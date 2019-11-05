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

  def getType: String = "File"

}

object File {
  def empty(parentPath: String, name: String): File =
    new File(parentPath, name, "")
}