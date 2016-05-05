package example

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

object Setup {
  def remotingConfig(port: Int) = ConfigFactory.parseString(
    s"""
       |akka {
       |  actor.provider = "akka.remote.RemoteActorRefProvider"
       |  remote {
       |    enable-transports = ["akka.remote.netty.tcp"]
       |    netty.tcp {
       |      hostname = "127.0.0.1"
       |      port = $port
       |    }
       |  }
       |}
     """.stripMargin)

  def remotingSystem(name: String, port: Int) = ActorSystem(name, remotingConfig(port))
}
