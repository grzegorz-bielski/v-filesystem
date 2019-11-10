package com.pesiok.scala.oop.files

import com.pesiok.scala.oop.filesystem.{FileSystemException, State}

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

  def getAbsolutePath(dir: String): String = {
    if (dir.startsWith(Directory.SEPARATOR)) dir
    else if (isRoot) path + dir
    else path + Directory.SEPARATOR + dir
  }

  def findDescendant(path: List[String]): Directory = {
    if (path.isEmpty) this
    else findEntry(path.head)
            .asDirectory
            .findDescendant(path.tail)
  }

  def findDescendant(path: String): Directory = {
    if (path.isEmpty) this
    else findDescendant(path.split(Directory.SEPARATOR).toList)
  }

  def addEntry(newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents :+ newEntry)

  def removeEntry(entry: String): Directory = {
    if (!hasEntry(entry)) this
    else new Directory(parentPath, name, contents.filter((x => !x.name.equals((entry)))))
  }

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

  def isRoot: Boolean = parentPath.isEmpty
  def asDirectory: Directory = this
  override def isDirectory: Boolean = true
  override def isFile: Boolean = false
  def asFile: File = throw new FileSystemException("Directory cannot be converted to the file")
  def getType: String = "Directory"
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "")
  def empty(parentPath: String, name: String) =
      new Directory(parentPath, name, List())
}