package com.pesiok.scala.oop.filesystem

import com.pesiok.scala.oop.commands.Command
import com.pesiok.scala.oop.files.Directory

object Filesystem extends App {
  val root = Directory.ROOT
  val init = State(root, root)

  init.show

  io.Source.stdin
      .getLines()
      .foldLeft(init)((curr, line) => {
        curr.show
        Command.from(line)(curr)
      })
}
