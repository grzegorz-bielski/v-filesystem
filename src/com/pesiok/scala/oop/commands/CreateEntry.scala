package com.pesiok.scala.oop.commands

import com.pesiok.scala.oop.files.{DirEntry, Directory}
import com.pesiok.scala.oop.filesystem.State

abstract class CreateEntry(name: String) extends Command {
  override def apply(state: State): State = {
    if (state.wd.hasEntry(name)) {
      return state.setMessage(s"Entry $name already exists!")
    }

    if (name.contains(Directory.SEPARATOR)) {
      return state.setMessage(s"$name must not contain separators")
    }

    if (checkIllegal(name)) {
      return state.setMessage(s"$name: illegal entry name")
    }

    doCreateEntry(state, name)
  }

  def checkIllegal(name: String): Boolean = {
    name.contains('.')
  }

  def doCreateEntry(state: State, name: String): State = {
    val allDirsInPath = state.wd.getAllFoldersInPath
    val newEntry = createSpecificEntry(state)
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)
    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)
  }

  def createSpecificEntry(state: State): DirEntry

  def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
    if (path.isEmpty) {
      return currentDirectory.addEntry(newEntry)
    }

    val oldEntry = currentDirectory.findEntry(path.head).asDirectory

    currentDirectory
      .replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))


  }
}
