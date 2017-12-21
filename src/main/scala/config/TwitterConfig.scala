package config

/**
  * Created by zarour on 13/04/2017.
  */

case class TwitterConfig(appName:String,
                         appKey:String,
                         accessToken:String,
                         tokenSecret:String,
                         consumerSecret:String)
object TwitterConfig {

  private val appName = "samsApplicationTwitterScala"
  private val appKey = "cqjQlYZKh7oosJnJfloXZXLhI"
  private val accessToken = "852618686208897029-n4tZMq6XtOHLA1slL3m3HLgWpdXIAMe"
  private val tokenSecret = "SjJUGILbiWDAbGRxeoSn1NR6Hko1qrAKavmgWjslTXHh8"
  private val consumerSecret = "EwlLoA4cGcyppP9yIhFnDIY6HLOh6Qd7cEbvnfaOu3REzhazrm"

  def getKeys = TwitterConfig(appName, appKey, accessToken, tokenSecret, consumerSecret)

}
