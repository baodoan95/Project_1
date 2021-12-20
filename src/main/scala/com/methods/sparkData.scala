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
  spark.sql("USE project1")

  //Delete this block later only for testing
  def showTable(): Unit ={
    spark.sql("SELECT * FROM users").show()
  }
  //Delete this block later only for testing





  def deleteUser(user:String): Unit ={
    spark.sql("CREATE TABLE holdertable (username String, password String, privilege string, dbquery_count int)")
    spark.sql(s"INSERT INTO holdertable SELECT * FROM users WHERE username <> '$user'")
    spark.sql("DROP TABLE users")
    spark.sql("ALTER TABLE holdertable RENAME TO users")
  }

  def createUser(username:String, pass:String): Unit ={
    spark.sql(s"INSERT INTO users VALUES('$username','$pass','Basic',0)")
  }

  def userExists(user:String, pass:String):Boolean={
    val check = spark.sql(s"SELECT COUNT(*) FROM users WHERE username = '$user' AND password = '$pass'")
      .collect()
    if(check(0)(0) == 1) true else false
  }

}