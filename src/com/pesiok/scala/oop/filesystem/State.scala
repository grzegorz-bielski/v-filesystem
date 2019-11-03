package com.pesiok.scala.oop.filesystem

import com.pesiok.scala.oop.files.Directory

class State(
   val root: Directory,
   val wd: Directory,
   val output: String
 ) {

  def show: Unit = {
    println(output)
    print(State.SHELL_TOKEN)
  }

  def setMessage(msg: String) =
    State(root, wd, msg)

}

object State {
  val SHELL_TOKEN = "$ "

  def apply(root: Directory, wd: Directory, output: String = "") =
    new State(root, wd, output)
}