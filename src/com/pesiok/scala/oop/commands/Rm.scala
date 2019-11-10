package com.pesiok.scala.oop.commands
import com.pesiok.scala.oop.files.Directory
import com.pesiok.scala.oop.filesystem.State

class Rm(name: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.wd

    val absolutePath = wd.getAbsolutePath(name)

    if (Directory.ROOT_PATH == absolutePath)
        state.setMessage("Cannot remove root path")
    else doRm(state, absolutePath)

  }

  private def doRm(state: State, path: String): State = {
    def remove(curr: Directory, path: List[String]): Directory = {
      if (path.isEmpty) curr
      else if (path.tail.isEmpty) curr.removeEntry(path.head)
      else {
        val nextDir = curr.findEntry(path.head)
        if (!nextDir.isDirectory) curr
        else {
          val newNextDir = remove(nextDir.asDirectory, path.tail)

          if (newNextDir == nextDir) curr
          else curr.replaceEntry(path.head, newNextDir)
        }
      }
    }

    val tokens = path.substring(1).split(Directory.SEPARATOR).toList
    val newRoot: Directory = remove(state.root, tokens)

    if (newRoot == state.root)
        state.setMessage(path + ": no such file or directory")
    else
      State(newRoot, newRoot.findDescendant(state.wd.path.substring(1)))
  }
}

object Rm {
  val cmd = "rm"
}
