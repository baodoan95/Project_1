package com.methods

class query {
  val sparkData = new sparkData
  def privilegeCheck(user:String): Unit ={
    if(sparkData.checkPrivilege(user) == 1) admin() else basic()
  }

  def admin(): Unit ={
    println("clearscreen")
    println("Hello Admin!")
    System.exit(0)
  }
  def basic(): Unit ={
    println("clearscreen")
    println("Hello Basic")
    System.exit(0)
  }
}
