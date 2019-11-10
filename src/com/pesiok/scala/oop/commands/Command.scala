package com.pesiok.scala.oop.commands

import com.pesiok.scala.oop.filesystem.State

trait Command extends (State => State) {
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
      case Pwd.cmd => new Pwd
      case Ls.cmd => new Ls
      case Touch.cmd => new Touch(tokens(1))
      case Cd.cmd => new Cd(tokens(1))
      case Rm.cmd => new Rm(tokens(1))
      case Echo.cmd => new Echo(tokens.tail)
      case Cat.cmd => new Cat(tokens(1))

      case _ => new UnknownCommand
    }
  }

}
