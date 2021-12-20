package com.methods
import scala.io.StdIn._
class menus {


  //Instantiate
  val menuLogos = new menuLogos()
  val sha256 = new sha256()
  val sparkData = new sparkData()
  val query = new query()


  //Main Menu print and scan input
  def mainMenu(): Unit ={
    println("clearscreen")
    menuLogos.welcomeMenu()
    println(s" ${Console.RED}[1]${Console.RESET} Login")
    println(s" ${Console.RED}[2]${Console.RESET} Create Account")
    println(s" ${Console.RED}[3]${Console.RESET} Exit\n")
    print("Enter input: ")
    var input = readLine()
    var isTrue = true
    while(isTrue) {
      input match {
        case "1" => println("clearscreen")
          login()
          isTrue = false
        case "2" => println("clearscreen")
          createAccount()
          isTrue = false
        case "3" => println("clearscreen")
          println(s"${Console.RED}Thank You For Using Movies Analytic App${Console.RESET}")
          sparkData.spark.close()
          System.exit(0)
        case  _  => print("Invalid input.  Enter input: ")
          input = readLine()
      }
    }
  }



  //Login Menu
  def login():Unit={
    menuLogos.loginMenu()
    var count = 3
    print(s"${Console.RED}Username: ${Console.RESET}")
    var user = readLine()
    print(s"${Console.RED}Password: ${Console.RESET}")
    var pass = readLine()
    var status = sparkData.userExists(user,sha256.hash(pass))
    if (status == true){
      query.privilegeCheck(user)
    }else{
      while(status == false){
        println("clearscreen")
        menuLogos.loginMenu()
        println(s"Incorrect credentials try again! Attempts left: $count")
        print(s"${Console.RED}Username: ${Console.RESET}")
        user = readLine()
        print(s"${Console.RED}Password: ${Console.RESET}")
        pass = readLine()
        status = sparkData.userExists(user,sha256.hash(pass))
        if(status == true){
          status = true
          query.privilegeCheck(user)
          System.exit(0)
        }
        count -= 1
        if(count == 0){
          status = true
          println("clearscreen")
          println("Maximum failed attempts reached.")
          println("Redirecting to main menu")
          for(i <- 0 to 18){
            print("█")
            Thread.sleep(100)
          }
          mainMenu()
        }
      }
      count = 3
      mainMenu()
    }
  }


  //Create Account Menu
  def createAccount():Unit= {
    println("clearscreen")
    menuLogos.accCreateMenu()
    print(s"${Console.RED}Enter Username: ${Console.RESET}")
    val user = readLine()
    var isMatch = true
    print(s"${Console.RED}Enter Password: ${Console.RESET}")
    val pass = readLine()
    print(s"${Console.RED}Re-enter Password: ${Console.RESET}")
    var repass = readLine()
    while (isMatch) {
      if(pass == repass) isMatch = false
      else{
        print("Password not match.  Re-enter:  ")
        repass = readLine()
      }
    }
    sparkData.createUser(user,sha256.hash(pass))
    println("clearscreen")
    println("Account successfully created!")
    println("Redirecting to main menu")
    for(i <- 0 to 18){
      print("█")
      Thread.sleep(100)
    }
    mainMenu()
  }



}