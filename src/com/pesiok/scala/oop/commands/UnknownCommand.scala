package com.pesiok.scala.oop.commands
import com.pesiok.scala.oop.filesystem.State

class UnknownCommand extends Command {
  override def apply(state: State): State =
    state.setMessage("Command not found!")
}