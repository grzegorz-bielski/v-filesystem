package com.pesiok.scala.oop.commands
import com.pesiok.scala.oop.files.{DirEntry, Directory}
import com.pesiok.scala.oop.filesystem.State

import scala.annotation.tailrec

class Cd(dir: String) extends Command {
  override def apply(state: State): State = {
    val root = state.root
    val wd = state.wd

    val absolutePath  = wd.getAbsolutePath(dir)
    val destDirectory = findDirectory(root, absolutePath)

    if (destDirectory == null || !destDirectory.isDirectory)
        state.setMessage(s"$dir: no such directory")
    else
        State(root, destDirectory.asDirectory)
  }

  private def findDirectory(root: Directory, path: String): DirEntry = {
    @tailrec
    def navigate(curr: Directory, path: List[String]): DirEntry = {
      if (path.isEmpty || path.head.isEmpty) curr
      else if (path.tail.isEmpty) curr.findEntry(path.head)
      else {
        val next = curr.findEntry(path.head)
        if (next == null || !next.isDirectory) null
        else navigate(next.asDirectory, path.tail)
      }
    }

    @tailrec
    def foldRelativeTokens(path: List[String], acc: List[String]): List[String] = {
      if (path.isEmpty) acc
      else path.head match {
        case "." => foldRelativeTokens(path.tail, acc)
        case ".." => if (acc.isEmpty) null
                     else foldRelativeTokens(path.tail, acc.tail)
        case _ => foldRelativeTokens(path.tail, acc :+ path.head)
      }
    }

    val tokens = path.substring(1).split(Directory.SEPARATOR).toList
    val foldedTokens = foldRelativeTokens(tokens, List())

    if (foldedTokens == null) null
    else  navigate(root, foldedTokens)
  }
}

object Cd {
  val cmd = "cd"
}
