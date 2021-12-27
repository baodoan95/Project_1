package com.methods
import scalaj.http.Http

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
    if(sparkData.checkPrivilege(user) == 1) admin("Admin") else basic(user)
  }

  //admin functions
  def admin(user:String): Unit ={
    println("clearscreen")
    menuLogos.movieAnalyticApp()
    println(s"${Console.GREEN}\nWelcome To Movies Analytic App!")
    println(Calendar.getInstance().getTime() + s"${Console.RESET}\n")
    println(s" ${Console.RED}[1]${Console.RESET} Start query")
    println(s" ${Console.RED}[2]${Console.RESET} Edit Accounts")
    println(s" ${Console.RED}[3]${Console.RESET} Log Out\n")
    print("Enter input: ")
    var input = readLine()
    var isTrue = true
    while(isTrue) {
      input match {
        case "1" => startQuery(user)
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
    menuLogos.movieAnalyticApp()
    println(s"${Console.GREEN}\nWelcome To Movies Analytic App!")
    println(Calendar.getInstance().getTime() + s"${Console.RESET}\n")
    println(s" ${Console.RED}[1]${Console.RESET} Start Query")
    println(s" ${Console.RED}[2]${Console.RESET} Edit Account")
    println(s" ${Console.RED}[3]${Console.RESET} Log Out\n")
    print("Enter input: ")
    var input = readLine()
    var isTrue = true
    while (isTrue) {
      input match {
        case "1" => startQuery(user)
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

  //EDIT ACCOUNTS
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

  //QUERY FUNCTIONS
  def startQuery(user:String): Unit ={
    println("clearscreen")
    menuLogos.querySection()
    println(s" ${Console.RED}[1]${Console.RESET} 6 Preset Analytic Queries")
    println(s" ${Console.RED}[2]${Console.RESET} Personalize your own query")
    println(s" ${Console.RED}[3]${Console.RESET} Box Office Weekly Stats")
    println(s" ${Console.RED}[4]${Console.RESET} Go Back\n")
    print("Enter input: ")
    var input = readLine()
    var isTrue = true
    while(isTrue) {
      input match {
        case "1" => sixqueries(user)
        case "2" => personalizeQuery(user)
        case "3" => weekendBoxOffice(user)
        case "4" => println("clearscreen")
                    if(user == "Admin") admin(user) else basic(user)
        case _ => print("Invalid options.  Try again:  ")
          input = readLine()
      }
    }
  }

  //6 Preset Queries
  def sixqueries(user:String): Unit ={
    println("clearscreen")
    menuLogos.sixQueries()
    println(s" ${Console.RED}[1]${Console.RESET} Genre Average Rating Rankings")
    println(s" ${Console.RED}[2]${Console.RESET} Genre Being Produced The Most In The Last 10 Years")
    println(s" ${Console.RED}[3]${Console.RESET} Most Popularly Bad Movies")
    println(s" ${Console.RED}[4]${Console.RESET} Ranking Of Movies Rating Types")
    println(s" ${Console.RED}[5]${Console.RESET} Leonardo DiCaprio Top Movies")
    println(s" ${Console.RED}[6]${Console.RESET} Most Popular Movies In The Last 10 Years")
    println(s" ${Console.RED}[7]${Console.RESET} Go Back\n")
    print("Enter input: ")
    var input = readLine()
    var isTrue = true
    while(isTrue) {
      input match {
        case "1" => println("clearscreen")
          println("Genre Average Rating Rankings")
          sparkData.spark.sql("SELECT genre as Genres_Combination, ROUND(AVG(imdb_rating),1) as AVG_Ratings FROM movies GROUP BY genre order by AVG_Ratings DESC").show(false)
          println(s"Press ${Console.GREEN}Enter${Console.RESET} to go back")
          val wait = readLine()
          sixqueries(user)
        case "2" => println("clearscreen")
          println("Genre Being Produced The Most In The Last 10 Years")
          sparkData.spark.sql("SELECT genre as Genres_Combination, count(*) AS Total FROM movies GROUP BY genre order by total DESC").show(false)
          println(s"Press ${Console.GREEN}Enter${Console.RESET} to go back")
          val wait = readLine()
          sixqueries(user)
        case "3" => println("clearscreen")
          println("Most Popularly Bad Movies")
          sparkData.spark.sql("SELECT title, CAST(rating_votes as INT), imdb_rating FROM movies WHERE imdb_rating < 4.0 ORDER BY rating_votes DESC ").show(false)
          println(s"Press ${Console.GREEN}Enter${Console.RESET} to go back")
          val wait = readLine()
          sixqueries(user)
        case "4" => println("clearscreen")
          println("Ranking Of Movies Rating Types")
          sparkData.spark.sql("SELECT content_rating as Rating_Types, count(title) as total FROM movies GROUP BY Rating_Types ORDER BY total DESC").show()
          println(s"Press ${Console.GREEN}Enter${Console.RESET} to go back")
          val wait = readLine()
          sixqueries(user)
        case "5" => println("clearscreen")
          println("Leonardo DiCaprio Top Movies")
          sparkData.spark.sql("SELECT title, description, runtime, imdb_rating,rating_votes FROM movies WHERE stars REGEXP 'Leonardo' ORDER BY imdb_rating DESC").show(false)
          println(s"Press ${Console.GREEN}Enter${Console.RESET} to go back")
          val wait = readLine()
          sixqueries(user)
        case "6" => println("clearscreen")
          println("Most Popular Movies In The Last 10 Years")
          sparkData.spark.sql("SELECT title, CAST(rating_votes as INT), imdb_rating FROM movies ORDER BY rating_votes DESC").show(false)
          println(s"Press ${Console.GREEN}Enter${Console.RESET} to go back")
          val wait = readLine()
          sixqueries(user)
        case "7" => startQuery(user)
        case _ => print("Invalid options.  Try again:  ")
          input = readLine()
      }
    }
  }

  //Personalize Query
  def personalizeQuery(user:String): Unit ={
    println("clearscreen")
    menuLogos.personalizeQuery()
    print("Enter Begin Year: ")
    val beginyr = readLine()
    print("Enter End Year: ")
    val endyr = readLine()
    print("Specify Order (title, genres, year, ratings, or votes): ")
    val order = readLine()
    print("ASC OR DESC: ")
    val ordertype = readLine()
    print("How Many Results Do You Want To See: ")
    val resultcount = readLine()

    val json = ujson.read(Http(s"https://imdb-api.com/API/AdvancedSearch/k_mvnql4l2?release_date=$beginyr-01-01,$endyr-01-01&count=100")
      .asString.body)
    val jsoncontent = json("results").toString()
    import sparkData.spark.implicits._
    val tempTable = sparkData.spark.read.json(Seq(jsoncontent).toDS)
    tempTable.createOrReplaceTempView("moviesview")
    sparkData.spark.sql(s"SELECT id, title, genres, description as year,imDbRating as ratings, imDbRatingVotes as votes FROM moviesview ORDER BY $order $ordertype LIMIT $resultcount").show(resultcount.toInt,false)
    println(s"${Console.GREEN}Do You Want To Save This Query Result Into A Json File? (Y/N): ")
    var jsoninput = readLine().toLowerCase
    while(true) {
      jsoninput match {
        case "y" => print("Name Your Json File Folder: ")
          val jsonfoldername = readLine()
          sparkData.spark.sql(s"SELECT id, title, genres, description as year,imDbRating as ratings, imDbRatingVotes as votes FROM moviesview ORDER BY $order $ordertype LIMIT $resultcount").repartition(1).write.json(s"$jsonfoldername")
          println(s"${Console.GREEN}JSON FILE SUCCESSFULLY GENERATED FROM QUERY RESULT.")
          println(s"PLEASE CHECK $jsonfoldername ON THE LEFT TO OBTAIN YOUR FILE.")
          println("Redirecting back to query menu")
          for(i <- 0 to 20){
            print("█")
            Thread.sleep(120)
          }
          print(s"${Console.RESET}")
          startQuery(user)
        case "n" => startQuery(user)
        case _ => print("Invalid options.  Try again:  ")
          jsoninput = readLine()
      }
    }
  }

  //Box Office Weekend
  def weekendBoxOffice(user:String): Unit ={
    println("clearscreen")
    menuLogos.weekendBoxOffice()
    val json = ujson.read(Http("https://imdb-api.com/en/API/BoxOffice/k_mvnql4l2").asString.body)
    val jsoncontent = json("items").toString()
    import sparkData.spark.implicits._
    val tempboxoffice = sparkData.spark.read.json(Seq(jsoncontent).toDS)
    tempboxoffice.createOrReplaceTempView("WeekendBoxOffice")
    sparkData.spark.sql("SELECT id, rank, title, weekend, gross, weeks as weeks_in_theater FROM WeekendBoxOffice").show(false)
    println(s"${Console.GREEN}Do You Want To Save This Query Result Into A Json File? (Y/N): ")
    var jsoninput = readLine().toLowerCase
    while(true) {
      jsoninput match {
        case "y" => print("Name Your Json File Folder: ")
          val jsonfoldername = readLine()
          sparkData.spark.sql("SELECT id, rank, title, weekend, gross, weeks as weeks_in_theater FROM WeekendBoxOffice").repartition(1).write.json(s"$jsonfoldername")
          println(s"${Console.GREEN}JSON FILE SUCCESSFULLY GENERATED FROM QUERY RESULT.")
          println(s"PLEASE CHECK $jsonfoldername ON THE LEFT TO OBTAIN YOUR FILE.")
          println("Redirecting back to query menu")
          for(i <- 0 to 20){
            print("█")
            Thread.sleep(120)
          }
          print(s"${Console.RESET}")
          startQuery(user)
        case "n" => startQuery(user)
        case _ => print("Invalid options.  Try again:  ")
          jsoninput = readLine()
      }
    }
  }
}