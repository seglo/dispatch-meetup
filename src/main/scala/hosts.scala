package meetup

import dispatch._

trait Hosts {
  def apiHost: Req
  def streamHost: Req
}

trait DefaultHosts extends Hosts {
  def apiHost = :/("api.meetup.com").secure
  def streamHost = :/("stream.meetup.com")
}
