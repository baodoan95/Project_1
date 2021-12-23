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
    println(s" ${Console.RED}[2]${Console.RESET} Edit Accounts")
    println(s" ${Console.RED}[3]${Console.RESET} Log Out\n")
    print("Enter input: ")
    var input = readLine()
    var isTrue = true
    while(isTrue) {
      input match {
        case "1" => println("Will add query here")
        System.exit(0)
        case "2" => editAccountAdmin(user)
        case "3" => println("clearscreen")
          println(s"${Console.GREEN}Logged out of user $user successfully!")
          println("Redirecting to main menu")
          for(i <- 0 to 20){
            print("█")
            Thread.sleep(120)
          }
          print(s"${Console.RESET}")
          mainMenu()
        case _ => print("Invalid options.  Try again:  ")
        input = readLine()
      }
    }
  }

  //basic user functions
  def basic(user:String): Unit = {
    println("clearscreen")
    menuLogos.editAccount()
    println(s"${Console.GREEN}\nWelcome ${user.toUpperCase()}!")
    println(Calendar.getInstance().getTime() + s"${Console.RESET}\n")
    println(s" ${Console.RED}[1]${Console.RESET} Start Query")
    println(s" ${Console.RED}[2]${Console.RESET} Edit Account")
    println(s" ${Console.RED}[3]${Console.RESET} Log Out\n")
    print("Enter input: ")
    var input = readLine()
    var isTrue = true
    while (isTrue) {
      input match {
        case "1" => println("Will add query here") //Add query
        System.exit(0)
        case "2" => editAccountBasic(user)
        case "3" => println("clearscreen")
          println(s"${Console.GREEN}Logged out of user $user successfully!")
          println("Redirecting to main menu")
          for(i <- 0 to 20){
            print("█")
            Thread.sleep(120)
          }
          print(s"${Console.RESET}")
          mainMenu()
        case _ => print("Invalid options.  Try again:  ")
          input = readLine()
      }
    }
  }

  def editAccountAdmin(user:String): Unit ={
    println("clearscreen")
    menuLogos.editAccount()
    println(s" ${Console.RED}[1]${Console.RESET} View All Users Credentials")
    println(s" ${Console.RED}[2]${Console.RESET} Modify User's Username")
    println(s" ${Console.RED}[3]${Console.RESET} Modify User's Password")
    println(s" ${Console.RED}[4]${Console.RESET} Update User's Privilege")
    println(s" ${Console.RED}[5]${Console.RESET} Delete User")
    println(s" ${Console.RED}[6]${Console.RESET} Go Back\n")
    print("Enter input: ")
    var input = readLine()
    var isTrue = true
    while (isTrue) {
      input match {
        case "1" => println("clearscreen")
          sparkData.showUsers()
          println(s"Press ${Console.GREEN}Enter${Console.RESET} to go back")
          val wait = readLine()
          editAccountAdmin(user)

        case "2" =>println("clearscreen")
          print("Enter User's username: ")
          val curuser = readLine()
          print("Enter user's new Username: ")
          val newuser = readLine()
          sparkData.updateUsername(curuser,newuser)
          println(s"${Console.GREEN}Successfully changed user: '$curuser' to '$newuser'!")
          println("Redirecting back to menu")
          for(i <- 0 to 20){
            print("█")
            Thread.sleep(120)
          }
          print(s"${Console.RESET}")
          editAccountAdmin(user)

        case "3" =>println("clearscreen")
          print("Enter user's Username: ")
          val nowuser = readLine()
          print("Enter new password for user: ")
          val newpass = readLine()
          sparkData.updatePasswordAdminWay(nowuser,sha256.hash(newpass))
          println("clearscreen")
          println(s"${Console.GREEN}Password successfully changed for user '$nowuser'!")
          println("Redirecting to menu")
          for(i <- 0 to 20){
            print("█")
            Thread.sleep(120)
          }
          print(s"${Console.RESET}")
          editAccountAdmin(user)


        case "4" => println("clearscreen")
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
          editAccountAdmin(user)
        case "5" => println("clearscreen")
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
          editAccountAdmin(user)
        case "6" => println("clearscreen")
          admin(user)
        case _ => print("Invalid options.  Try again:  ")
          input = readLine()
      }
    }
  }

  def editAccountBasic(user:String): Unit ={
    println("clearscreen")
    menuLogos.editAccount()
    println(s" ${Console.RED}[1]${Console.RESET} Change Username")
    println(s" ${Console.RED}[2]${Console.RESET} Change Password")
    println(s" ${Console.RED}[3]${Console.RESET} Go Back\n")
    print("Enter input: ")
    var input = readLine()
    var isTrue = true
    while (isTrue) {
      input match {
        case "1" => println("clearscreen")
          print("Enter current username: ")
          var olduser = readLine()
          while(olduser != user){
            print("Username does not match current username!")
            print("Enter current username: ")
            olduser = readLine()
          }
          print("Enter new username: ")
          val newuser = readLine()
          sparkData.updateUsername(olduser,newuser)
          println("clearscreen")
          println(s"${Console.GREEN}Username changed from '$olduser' to '$newuser' successfully!")
          println("Redirecting to main menu")
          for(i <- 0 to 20){
            print("█")
            Thread.sleep(120)
          }
          print(s"${Console.RESET}")
          mainMenu()
        case "2" =>  println("clearscreen")
          print("Enter current password: ")
          var oldpass = readLine()
          print("Enter new password: ")
          val newpass = readLine()
          sparkData.updatePassword(user,sha256.hash(oldpass),sha256.hash(newpass))
          println("clearscreen")
          println(s"${Console.GREEN}Password changed successfully!")
          println("Redirecting to main menu")
          for(i <- 0 to 20){
            print("█")
            Thread.sleep(120)
          }
          print(s"${Console.RESET}")
          mainMenu()
        case "3" => println("clearscreen")
          basic(user)
        case _ => print("Invalid options.  Try again:  ")
          input = readLine()
      }
    }
  }

}