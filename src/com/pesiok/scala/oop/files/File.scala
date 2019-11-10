package com.pesiok.scala.oop.files

import com.pesiok.scala.oop.filesystem.FileSystemException

class File(
  override val parentPath: String,
  override val name: String,
  val contents: String)
  extends DirEntry(parentPath, name) {

  def asDirectory: Directory =
    throw new FileSystemException("File cannot be converted to a directory")

  def asFile = this

  override def isDirectory: Boolean = false

  override def isFile: Boolean = true

  def getType: String = "File"

  def setContents(newConents: String): File =
    new File(parentPath, name, newConents)

  def appendContents(newContents: String): File =
    setContents(contents + "\n" + newContents)

}

object File {
  def empty(parentPath: String, name: String): File =
    new File(parentPath, name, "")
}