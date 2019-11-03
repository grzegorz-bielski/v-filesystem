package com.pesiok.scala.oop.commands

import com.pesiok.scala.oop.filesystem.State

trait Command {
  def apply(state: State): State
}

object Command {

  def emptyCommand: Command = new Command {
    override def apply(state: State) = state
  }

  def incompleteCommand(cmd: String): Command = new Command {
    override def apply(state: State) = state.setMessage("incomplete command!")
  }

  def from(input: String): Command = {
    if (input.isEmpty) {
      return emptyCommand
    }

    val tokens = input.split(" ")
    val userCmd = tokens(0)

    userCmd match {
      case Mkdir.cmd => {
        if (tokens.length < 2) incompleteCommand(Mkdir.cmd)
        else new Mkdir(tokens(1))
      }

      case Ls.cmd => new Ls

      case _ => new UnknownCommand
    }
  }
}
