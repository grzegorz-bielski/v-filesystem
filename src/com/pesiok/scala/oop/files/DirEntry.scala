package com.pesiok.scala.oop.files

abstract class DirEntry(val parentPath: String, val name: String) {
  def path: String = {
    val separator =
      if (Directory.ROOT_PATH.equals(parentPath)) ""
      else Directory.SEPARATOR

    parentPath + separator + name
  }

  def asDirectory: Directory

  def asFile: File

  def isDirectory: Boolean
  def isFile: Boolean

  def getType: String
}
