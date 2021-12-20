package com.methods

class menuLogos {
  def welcomeMenu():Unit={
    println(s"     ${Console.RED}_______________________")
    println(s"     | MOVIES ANALYTIC APP |\n${Console.RESET}")
  }

  def mainMenu():Unit={
    println(s"     ${Console.RED}_____________")
    println(s"     | MAIN MENU |\n${Console.RESET}")
  }

  def loginMenu():Unit={
    println(s"     ${Console.RED}_________")
    println(s"     | LOGIN |\n${Console.RESET}")
  }

  def accCreateMenu():Unit={
    println(s"     ${Console.RED}____________________")
    println(s"     | CREATE ACCOUNT |\n${Console.RESET}")
  }

  def querySection():Unit={
    println(s"     ${Console.RED}___________________")
    println(s"     | QUERY SECTION |\n${Console.RESET}")
  }

}