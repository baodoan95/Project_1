package com.methods
import org.apache.spark.sql.SparkSession
class sparkData{
  //Hadoop pathing and set up
  System.setProperty("hadoop.home.dir", "C:\\hadoop")
  val spark = SparkSession
    .builder
    .appName("hello hive")
    .config("spark.master", "local")
    .enableHiveSupport()
    .getOrCreate()
  //Turn off all log except errors logs
  spark.sparkContext.setLogLevel("ERROR")

  //Use project1 database
  spark.sql("USE project1")





  def showUsers(): Unit ={
    spark.sql("SELECT * FROM users").show()
  }

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



  def checkPrivilege(user:String):Int={
    val check = spark.sql(s"SELECT privilege FROM users WHERE username = '$user'").collect()
    if(check(0)(0) == "Admin") 1 else 0
  }

  def deleteUser(user:String): Unit ={
    spark.sql("CREATE TABLE holdertable (username STRING, password STRING, privilege STRING)")
    spark.sql(s"INSERT INTO holdertable SELECT * FROM users WHERE username <> '$user'")
    spark.sql("DROP TABLE users")
    spark.sql("ALTER TABLE holdertable RENAME TO users")
  }

  def updatePrivilege(user:String, privil:String): Unit ={
    val temp = spark.sql(s"SELECT * FROM users WHERE username = '$user'").collect()
    val usert = temp(0)(0)
    val passt = temp(0)(1)
    deleteUser(user)
    spark.sql(s"INSERT INTO users VALUES('$usert','$passt','$privil')")
  }

}