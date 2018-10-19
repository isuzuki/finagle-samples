package io.github.isuzuki

import com.twitter.finagle.http._
import com.twitter.finagle.http.path._
import com.twitter.finagle.http.service.RoutingService
import com.twitter.finagle.{Http, Service}
import com.twitter.util.{Await, Future}

object Router {
  val service = RoutingService.byMethodAndPathObject[Request] {
    case Method.Get  -> Root / "item" / itemId => getItem(itemId)
    case Method.Post -> Root / "item" => postItem
    case Method.Get  -> Root / "gif" => gif1px
    case Method.Get  -> Root => index
    case _ => NotFoundService
  }

  val index: Service[Request, Response] = (req: Request) => {
    val res = Response(req.version, Status.Ok)
    res.contentString_=("Hello World.")
    res.contentType_=(MediaType.PlainText)
    Future.value(res)
  }

  val gif1px: Service[Request, Response] = (req: Request) => {
    val res = Response(Status.Ok)
    res.content_=(HttpContent.gif1px)
    res.contentType_=(MediaType.Gif)

    Future.value(res)
  }

  def getItem(itemId: String): Service[Request, Response] = (req: Request) => {
    val res = Response(req.version, Status.Ok)
    res.setContentString(s"$itemId item.")
    res.setContentType("text/plain")
    Future.value(res)
  }

  val postItem: Service[Request, Response] = (req: Request) => {
    val content = req.contentString
    val res = Response(req.version, Status.NoContent)
    Future.value(res)
  }

  object NotFoundService extends Service[Request, Response] {
    def apply(req: Request): Future[Response] = {
      val res = Response(req.version, Status.NotFound)
      res.contentString_=("Hello World.")
      res.contentType_=(MediaType.PlainText)
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
