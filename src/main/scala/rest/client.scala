package meetup.rest

import dispatch._
import com.ning.http.client.{ Response }
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Promise

object Client {
  type Handler[T] = (Response => T)
  trait Completion {
    def request[T](handler: Client.Handler[T]): Future[T]
  }
}

/** Meetup Rest API client */
case class Client(credentials: meetup.Credentials, http: Http = Http)
  extends meetup.DefaultHosts
     with Cities
     with Events
     with EventComments
     with Groups
     with Member
     with OpenEvents
     with Photos
     with Rsvps
     with Raw {
  def apply[T](req: Req)(handler: Client.Handler[T]): Future[T] =
    http(credentials.sign(req) > handler)
}
