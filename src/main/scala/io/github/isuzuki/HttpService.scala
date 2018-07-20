package io.github.isuzuki

import com.twitter.finagle.http.path._
import com.twitter.finagle.http.service.RoutingService
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.util.{Await, Future}

object Router {
  val service = RoutingService.byPathObject[Request] {
    case Root / "item" / itemId => item(itemId)
    case Root => index
    case _ => NotFoundService
  }

  val index: Service[Request, Response] = (req: Request) => {
    val res = Response(req.version, Status.Ok)
    res.setContentString("Hello World.")
    res.setContentType("text/plain")
    Future.value(res)
  }

  def item(itemId: String): Service[Request, Response] = (req: Request) => {
    val res = Response(req.version, Status.Ok)
    res.setContentString(s"$itemId item.")
    res.setContentType("text/plain")
    Future.value(res)
  }

  object NotFoundService extends Service[Request, Response] {
    def apply(req: Request): Future[Response] = {
      val res = Response(req.version, Status.NotFound)
      res.setContentString("Not Found.")
      res.setContentType("text/plain")
      Future.value(res)
    }
  }
}

object HttpService {
  def main(args: Array[String]): Unit = {
    val server = Http.serve(":8080", Router.service)
    Await.ready(server)
  }
}
