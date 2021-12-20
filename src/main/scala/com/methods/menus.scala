package com.methods
import java.util.Calendar
import scala.io.StdIn._
class menus {


  //Instantiate
  val menuLogos = new menuLogos()
  val sha256 = new sha256()
  val sparkData = new sparkData()



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
      privilegeCheck(user)
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
          privilegeCheck(user)
          System.exit(0)
        }
        count -= 1
        if(count == 0){
          status = true
          println("clearscreen")
          println(s"${Console.GREEN}Maximum failed attempts reached.")
          println("Redirecting to main menu")
          for(i <- 0 to 20){
            print("█")
            Thread.sleep(120)
          }
          print(s"${Console.RESET}")
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
    var userExistsCheck = true
    var user = ""
    while (userExistsCheck) {
      print(s"${Console.RED}Enter Username: ${Console.RESET}")
      user = readLine()
      if(sparkData.userExistsDup(user) == false) userExistsCheck = false
      else{
        println("Username already exist. Please pick different username!")
      }
    }

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
    println(s"${Console.GREEN}Account created successfully!")
    println("Redirecting to main menu")
    for(i <- 0 to 20){
      print("█")
      Thread.sleep(120)
    }
    print(s"${Console.RESET}")
    mainMenu()
  }

  //Privilege check to assign proper options
  def privilegeCheck(user:String): Unit ={
    if(sparkData.checkPrivilege(user) == 1) admin(user) else basic(user)
  }

  //admin functions
  def admin(user:String): Unit ={
    println("clearscreen")
    menuLogos.querySection()
    println(s"${Console.GREEN}\nWelcome ${user.toUpperCase()}!")
    println(Calendar.getInstance().getTime() + s"${Console.RESET}\n")
    println(s" ${Console.RED}[1]${Console.RESET} Start query")
    println(s" ${Console.RED}[2]${Console.RESET} View accounts")
    println(s" ${Console.RED}[3]${Console.RESET} Update Privilege")
    println(s" ${Console.RED}[4]${Console.RESET} Delete account")
    println(s" ${Console.RED}[5]${Console.RESET} Log Out\n")
    print("Enter input: ")
    var input = readLine()
    var isTrue = true
    while(isTrue) {
      input match {
        case "1" => println("Will add query here")
        System.exit(0)
        case "2" => println("clearscreen")
          sparkData.showUsers()
          println(s"Press ${Console.GREEN}Enter${Console.RESET} to go back")
          val wait = readLine()
          admin(user)
        case "3" => println("clearscreen")
          print("Enter update privilege username: ")
          val userp = readLine()
          print("Enter privilege type: ")
          val privilp = readLine()
          sparkData.updatePrivilege(userp,privilp)
          println(s"${Console.GREEN}Updated user '$userp' privilege to $privilp successfully!")
          println("Redirect back menu")
          for(i <- 0 to 20){
            print("█")
            Thread.sleep(120)
          }
          print(s"${Console.RESET}")
          admin(user)
        case "4" => println("clearscreen")
          print("Enter username to be deleted: ")
          val delete = readLine()
          sparkData.deleteUser(delete)
          println(s"${Console.GREEN}Deleted user '$delete' successfully!")
          println("Redirect back menu")
          for(i <- 0 to 20){
            print("█")
            Thread.sleep(120)
          }
          print(s"${Console.RESET}")
          admin(user)
        case "5" => println("clearscreen")
          println(s"${Console.GREEN}Logged out of user $user successfully!")
          println("Redirecting to main menu")
          for(i <- 0 to 20){
            print("█")
            Thread.sleep(120)
          }
          print(s"${Console.RESET}")
          mainMenu()
      }
    }
  }

  //basic user functions
  def basic(user:String): Unit = {
    println("clearscreen")
    menuLogos.querySection()
    println(s"${Console.GREEN}\nWelcome ${user.toUpperCase()}!")
    println(Calendar.getInstance().getTime() + s"${Console.RESET}\n")
    println(s" ${Console.RED}[1]${Console.RESET} Start Query")
    println(s" ${Console.RED}[2]${Console.RESET} Log Out\n")
    print("Enter input: ")
    var input = readLine()
    var isTrue = true
    while (isTrue) {
      input match {
        case "1" => println("Will add query here")
        System.exit(0)
        case "2" => println("clearscreen")
          println(s"${Console.GREEN}Logged out of user $user successfully!")
          println("Redirecting to main menu")
          for(i <- 0 to 20){
            print("█")
            Thread.sleep(120)
          }
          print(s"${Console.RESET}")
          mainMenu()
      }
    }
  }

}