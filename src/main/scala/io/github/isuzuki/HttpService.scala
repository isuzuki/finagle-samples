package io.github.isuzuki

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.util.{Await, Future}

object HttpService {
  def main(args: Array[String]): Unit = {
    val service = new Service[Request, Response] {
      def apply(req: Request): Future[Response] = {
        val res = Response(req.version, Status.Ok)
        res.setContentString("Hello World.")
        Future.value(res)
      }
    }

    val server = Http.serve(":8080", service)
    Await.ready(server)
  }
}
