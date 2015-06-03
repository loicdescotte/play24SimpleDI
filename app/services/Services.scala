package services
import play.api.Play
import scala.concurrent.ExecutionContext.Implicits.global

class LogService{
  import play.api.Logger

  def log(message: String) {
    Logger.info(message)
  }
}

class LinkService(logService: LogService){
  import play.api.libs.ws.WS
  import play.api.libs.ws.ning._

  def findLinks(query: String) = {
    val duckDuckUrl = Play.current.configuration.getString("duckduck.url").getOrElse("http://api.duckduckgo.com/?format=json&q=") + query
    implicit val sslClient = NingWSClient()
    WS.clientUrl(duckDuckUrl).get().map{ response =>
      val results = response.json \\ "FirstURL"
      //take first result if exists
      val result =results.mkString(", ")
      logService.log("found links : " + result)
      result
    }
  }
}