package com.pesiok.scala.oop.commands
import com.pesiok.scala.oop.filesystem.State

class Pwd extends Command {
  override def apply(state: State): State =
      state.setMessage(state.wd.path)
}

object Pwd {
  val cmd = "pwd"
}
