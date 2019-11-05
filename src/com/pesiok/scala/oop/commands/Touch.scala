package com.pesiok.scala.oop.commands

import com.pesiok.scala.oop.files.{DirEntry, File}
import com.pesiok.scala.oop.filesystem.State

class Touch(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.wd.path, name)
}

object Touch {
  val cmd = "touch"
}
