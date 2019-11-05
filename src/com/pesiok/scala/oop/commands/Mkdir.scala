package com.pesiok.scala.oop.commands

import com.pesiok.scala.oop.files.{DirEntry, Directory}
import com.pesiok.scala.oop.filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry =
    Directory.empty(state.wd.path, name)
}

object Mkdir {
  val cmd = "mkdir"
}
