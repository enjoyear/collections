package example

import akka.actor.{Actor, Props}
import akka.event.Logging


object RemotingPongySystem extends App {
  println("creating pongy system")
  val system = Setup.remotingSystem("PongyDimension", 24321)
  val pongy = system.actorOf(Props[Pongy], "pongy")
  println("finished creating pongy system")
  Thread.sleep(15000)
  system.terminate()
}


class Pongy extends Actor {
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case "ping" =>
      log.info("Got a ping -- ponging back!")
      sender ! "pong"
      context.stop(self)
  }

  override def postStop() = log.info("pongy going down")
}

