package com.pesiok.scala.oop.commands
import com.pesiok.scala.oop.files.DirEntry
import com.pesiok.scala.oop.filesystem.State

class Ls extends Command {
  override def apply(state: State): State = {
    val contents = state.wd.contents
    val output = createOutput(contents)

    state.setMessage(output)
  }

  def createOutput(contents: List[DirEntry]): String = {
    if (contents.isEmpty) ""
    else {
      val entry = contents.head

      s"${entry.name}[${entry.getType}]\n${createOutput(contents.tail)}"
    }
  }
}

object Ls {
  val cmd = "ls"
}
