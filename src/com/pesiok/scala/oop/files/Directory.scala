package com.pesiok.scala.oop.files

import scala.annotation.tailrec

class Directory(
   override val parentPath: String,
   override val name: String,
   val contents: List[DirEntry]
 ) extends DirEntry(parentPath, name) {

  def hasEntry(name: String) = findEntry(name) != null

  def getAllFoldersInPath: List[String] =
    path
      .substring(1)
      .split(Directory.SEPARATOR)
      .toList
      .filter(e => !e.isEmpty)

  def findDescendant(path: List[String]): Directory = {
    if (path.isEmpty) this
    else findEntry(path.head)
            .asDirectory
            .findDescendant(path.tail)
  }

  def addEntry(newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents :+ newEntry)

  def findEntry(entryName: String): DirEntry = {
    @tailrec
    def findEntryPrim(name: String, contentList: List[DirEntry]): DirEntry =
      if (contentList.isEmpty) null
      else if (contentList.head.name.equals(name)) contentList.head
      else findEntryPrim(name, contentList.tail)

    findEntryPrim(entryName, contents)
  }

  def replaceEntry(entryName: String, newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents.filter(e => !e.name.equals((entryName))) :+ newEntry)

  def asDirectory: Directory = this
  def getType: String = "Directory"
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "")
  def empty(parentPath: String, name: String) =
      new Directory(parentPath, name, List())
}