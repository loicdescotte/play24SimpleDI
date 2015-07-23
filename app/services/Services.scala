package services
import play.api.Play
import play.api.Logger
import play.api.libs.ws.WSClient
import scala.concurrent.ExecutionContext.Implicits.global

class LogService{

  def log(message: String) {
    Logger.info(message)
  }

}

class LinkService(logService: LogService, wsClient: WSClient){

  def findLinks(query: String) = {
    val duckDuckUrl = Play.current.configuration.getString("duckduck.url").getOrElse("http://api.duckduckgo.com/?format=json&q=") + query
    wsClient.url(duckDuckUrl).get().map{ response =>
      val results = response.json \\ "FirstURL"
      //take first result if exists
      val result =results.mkString(", ")
      logService.log("found links : " + result)
      result
    }
  }

  def cleanup = Logger.info("cleanup")
}