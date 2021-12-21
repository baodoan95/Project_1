package com.methods
import scalaj.http._
import ujson._
class apidata {

    //Only used to get api movies main data and insert into hdfs
    val sparkData = new sparkData
    def putMoviesData(start:String,end:String) {
      val json = ujson.read(Http(s"https://imdb-api.com/API/Advanced" +
        s"Search/k_mvnql4l2?release_date=$start-01-01,$end-01-01&count=100")
        .asString.body)
      val tuples = json("results").arr.map { item =>
        (item("id")
          , item("title"), item("description")
          , item("runtimeStr"), item("contentRating")
          , item("imDbRating"), item("imDbRatingVotes")
          , item("stars"), item("genres"))
      }
      for (i <- 0 until tuples.length) {
        sparkData.insertAPI(tuples(i)._1.toString()
          , tuples(i)._2.toString
          , tuples(i)._3.toString
          , tuples(i)._4.toString
          , tuples(i)._5.toString
          , tuples(i)._6.toString
          , tuples(i)._7.toString
          , tuples(i)._8.toString
          , tuples(i)._9.toString)
      }
    }

}
