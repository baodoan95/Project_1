package com.methods
import java.security.MessageDigest

class sha256 {
  def hash(str: String):String= {
    MessageDigest.getInstance("SHA-256")
      .digest(str.getBytes("UTF-8"))
      .map("%02x".format(_)).mkString
  }
}