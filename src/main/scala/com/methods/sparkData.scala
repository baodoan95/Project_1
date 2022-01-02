package com.methods
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
class sparkData{
  //Hadoop pathing and set up
  System.setProperty("hadoop.home.dir", "C:\\hadoop")
  val spark = SparkSession
    .builder
    .appName("hello hive")
    .config("spark.master", "local[*]")
    .enableHiveSupport()
    .getOrCreate()
  //Turn off all log except errors logs
  spark.sparkContext.setLogLevel("ERROR")

  //Use project1 database
  spark.sql("USE project1")

  spark.sql("SET hive.exec.dynamic.partition = true")
  spark.sql("SET hive.exec.dynamic.partition.mode = nonstrict")


//-----------MAIN MOVIES DATA FUNCTIONS-------------
  def createMoviesTable() {
    spark.sql("CREATE TABLE movies (id STRING, title STRING, description STRING, " +
      "runtime STRING, genre STRING, imdb_rating STRING," +
      " rating_votes STRING, stars STRING) PARTITIONED BY (content_rating STRING)")
  }


  def insertAPI(id:String,title:String,description:String
             ,runtime:String,content_rating:String,imdb_rating:String
             ,rating_votes:String,stars:String,genre:String): Unit ={
    spark.sql(s"INSERT INTO movies VALUES($id,$title,$description" +
      s",$runtime,$content_rating," +
      s"$imdb_rating,$rating_votes,$stars,$genre)")
  }

  def showMoviesTable(): Unit ={
    spark.sql("SELECT * FROM movies").show(1100)
  }

  def describeMovies(): Unit ={
    spark.sql("SELECT content_rating,COUNT(content_rating) FROM movies GROUP BY content_rating").show(1100)
  }
  //-----------MAIN MOVIES DATA FUNCTIONS--------------


//test


//test





  //Show users credentials table
  def showUsers(): Unit ={
    spark.sql("SELECT * FROM users").show(false)
  }

  //Create users
  def createUser(username:String, pass:String): Unit ={
    spark.sql(s"INSERT INTO users VALUES('$username','$pass','Basic')")
  }

  //Check for login
  def userExists(user:String, pass:String):Boolean={
    val check = spark.sql(s"SELECT COUNT(*) FROM users WHERE username = '$user' AND password = '$pass'")
      .collect()
    if(check(0)(0) == 1) true else false
  }

  //check for duplicate username
  def userExistsDup(user:String): Boolean ={
    val check = spark.sql(s"SELECT COUNT(*) FROM users WHERE username = '$user'")
      .collect()
    if(check(0)(0) == 1) true else false
  }

  //pull privilege info 1 is Admin 0 is Basic
  def checkPrivilege(user:String):Int={
    val check = spark.sql(s"SELECT privilege FROM users WHERE username = '$user'").collect()
    if(check(0)(0) == "Admin") 1 else 0
  }

  //delete user
  def deleteUser(user:String): Unit ={
    spark.sql("CREATE TABLE holdertable (username STRING, password STRING, privilege STRING)")
    spark.sql(s"INSERT INTO holdertable SELECT * FROM users WHERE username <> '$user'")
    spark.sql("DROP TABLE users")
    spark.sql("ALTER TABLE holdertable RENAME TO users")
  }

  //update privilege from admin to basic or vice versa
  def updatePrivilege(user:String, privil:String): Unit ={
    val temp = spark.sql(s"SELECT * FROM users WHERE username = '$user'").collect()
    val usert = temp(0)(0)
    val passt = temp(0)(1)
    deleteUser(user)
    spark.sql(s"INSERT INTO users VALUES('$usert','$passt','$privil')")
  }

  def updateUsername(olduser:String,newuser:String): Unit ={
    val temp = spark.sql(s"SELECT * FROM users WHERE username = '$olduser'").collect()
    val oldpass = temp(0)(1)
    val oldprivil = temp(0)(2)
    deleteUser(olduser)
    spark.sql(s"INSERT INTO users VALUES('$newuser','$oldpass','$oldprivil')")
  }
  def updatePassword(user:String, oldpass:String,newpass:String): Unit ={
    val temp = spark.sql(s"SELECT * FROM users WHERE username = '$user' AND password = '$oldpass'").collect()
    val olduser = temp(0)(0)
    val oldprivil = temp(0)(2)
    deleteUser(user)
    spark.sql(s"INSERT INTO users VALUES('$olduser','$newpass','$oldprivil')")
  }

  def updatePasswordAdminWay(user:String,newpass:String): Unit ={
    val temp = spark.sql(s"SELECT * FROM users WHERE username = '$user'").collect()
    val olduser = temp(0)(0)
    val oldprivil = temp(0)(2)
    deleteUser(user)
    spark.sql(s"INSERT INTO users VALUES('$olduser','$newpass','$oldprivil')")
  }
}