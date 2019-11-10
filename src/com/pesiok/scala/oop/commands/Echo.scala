package com.pesiok.scala.oop.commands
import com.pesiok.scala.oop.files.{Directory, File}
import com.pesiok.scala.oop.filesystem.State

import scala.annotation.tailrec

class Echo(args: Array[String]) extends Command {
  override def apply(state: State): State = {
      if (args.isEmpty) state
      else if (args.length == 1) state.setMessage(args(0))
      else {
        val operator = args(args.length - 2)
        val filename = args(args.length - 1)
        val contents = createContent(args, args.length - 2)

        operator match {
          case ">>" => doEcho(state, contents, filename, append = true)
          case ">" => doEcho(state, contents, filename, append = false)
          case _ => state.setMessage(createContent(args, args.length))
        }
      }
  }

  def doEcho(state: State, contents: String, filename: String, append: Boolean): State = {
    if (filename.contains(Directory.SEPARATOR))
      state.setMessage("Echo: filename must not contain separators")
    else {
      val newRoot: Directory = getRootAfterEcho(state.root, state.wd.getAllFoldersInPath :+ filename, contents, append)
      if (newRoot == state.root)
          state.setMessage(s"$filename: no such file")
      else
        State(newRoot, newRoot.findDescendant(state.wd.getAllFoldersInPath))
    }
  }

  private def getRootAfterEcho(
    dir: Directory,
    path: List[String],
    contents: String,
    append: Boolean): Directory = {
    if (path.isEmpty) dir
    else if (path.tail.isEmpty) {
      val dirEntry = dir.findEntry(path.head)

      if (dirEntry == null)
        dir.addEntry(new File(dir.path, path.head, contents))
        else if (dirEntry.isDirectory) dir
        else
          if (append) dir.replaceEntry(path.head, dirEntry.asFile.appendContents(contents))
          else dir.replaceEntry(path.head, dirEntry.asFile.setContents(contents))
    } else {
      val nextDir = dir.findEntry(path.head).asDirectory
      val newNextDir = getRootAfterEcho(nextDir, path.tail, contents, append)

      if (newNextDir == nextDir) dir
      else dir.replaceEntry(path.head, newNextDir)
    }
  }

  private def createContent(args: Array[String], topIndex: Int): String = {
    @tailrec
    def create(index: Int, acc: String): String = {
      if (index >= topIndex) acc
      else create(index + 1, acc + " " + args(index))
    }

    create(0, "")
  }

}

object Echo {
  val cmd = "echo"
}
