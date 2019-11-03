package com.pesiok.scala.oop.filesystem

import java.util.Scanner

import com.pesiok.scala.oop.commands.Command
import com.pesiok.scala.oop.files.Directory

object Filesystem extends App {
  val scanner = new Scanner(System.in)
  val root = Directory.ROOT
  var state = State(root, root)

  while(true) {
    state.show
    state = Command.from(scanner.nextLine)(state)
  }
}
