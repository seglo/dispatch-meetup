package meetup.stream

import dispatch._
import net.liftweb.json.JValue
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Promise

object Client {
  import net.liftweb.json.JValue
  type Handler[T] = (JValue => T)
  trait Completion {
    def foreach[T](handler: Client.Handler[T]): Future[Unit]
  }
}

case class Client(http: Http = Http)
     extends meetup.DefaultHosts
        with Rsvps
        with OpenEvents
        with EventComments
        with Photos {
  def apply[T](req: Req)(handler: Client.Handler[T]): Future[Unit] =
    http(req > as.lift.stream.Json(handler))
}
