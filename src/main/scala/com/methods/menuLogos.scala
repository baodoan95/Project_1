package com.methods

class menuLogos {
  def welcomeMenu():Unit={
    println(s"${Console.RED}         ____  ____  ____      ____________________  " +
      s" ___\n        / __ \\/ __ \\/ __ \\  " +
      s"  / / ____/ ____/_  __/  <  /\n       / /_/ / /_/ / / / /_  / / __/ / /     / /   " +
      s"  / / \n      / ____/ _, _/ /_/ / /_/ / /___/ /___  / /   " +
      s"  / /  \n     /_/   /_/ |_|\\____/\\____/_____/\\____/ /_/     /_/   \n${Console.RESET}")
    println(s"${Console.GREEN}                     MOVIE ANALYTIC APP${Console.RESET}\n")
  }

  def mainMenu():Unit={
    println(s"             ${Console.RED}_____________")
    println(s"             | MAIN MENU |\n${Console.RESET}")
  }

  def loginMenu():Unit={
    println(s"             ${Console.RED}_________")
    println(s"             | LOGIN |\n${Console.RESET}")
  }

  def accCreateMenu():Unit={
    println(s"             ${Console.RED}__________________")
    println(s"             | CREATE ACCOUNT |\n${Console.RESET}")
  }

  def movieAnalyticApp():Unit={
    println(s"             ${Console.RED}_______________________")
    println(s"             | MOVIES ANALYTIC APP |\n${Console.RESET}")
  }

  def editAccount():Unit={
    println(s"             ${Console.RED}______________________")
    println(s"             | ACCOUNT MANAGEMENT |\n${Console.RESET}")
  }

  def querySection():Unit={
    println(s"             ${Console.RED}_________________")
    println(s"             | QUERY SECTION |\n${Console.RESET}")
  }
}