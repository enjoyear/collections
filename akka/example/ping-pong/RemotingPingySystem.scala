package example

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorIdentity, ActorRef, Identify, Props}
import akka.event.Logging
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Future

object RemotingPingySystem extends App {
  println("creating runner system")
  val system = Setup.remotingSystem("PingyDimension", 24567)
  val runner = system.actorOf(Props[Runner], "runner")
  println("finished creating runner system")

  runner ! "start"
  Thread.sleep(5000)
  system.terminate()
}


class Runner extends Actor {
  val log = Logging(context.system, this)
  val pingy = context.actorOf(Props[Pingy], "pingy")

  override def receive: Receive = {
    case "start" =>
      val pongySys = "akka.tcp://PongyDimension@127.0.0.1:24321"
      val pongyPath = "/user/pongy"
      val url = pongySys + pongyPath
      val selection = context.actorSelection(url)
      selection ! Identify(0)
    case ActorIdentity(0, Some(pongyRef)) =>
      pingy ! pongyRef
    case ActorIdentity(0, None) =>
      log.info("Wrong! Cannot find pongy.")
      context.stop(self)
    case "pong" =>
      log.info("got a pong from another dimension.")
      context.stop(self)
  }
}


class Pingy extends Actor {
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case pongyRef: ActorRef => {
      implicit val timeOut = Timeout(10, TimeUnit.SECONDS)
      val future: Future[Any] = pongyRef ? "ping"
      import akka.pattern.pipe
      import scala.concurrent.ExecutionContext.Implicits.global
      pipe(future).pipeTo(sender)
    }
  }
}
