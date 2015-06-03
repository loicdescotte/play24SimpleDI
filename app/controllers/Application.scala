package controllers

import play.api.mvc._
import services.LinkService
import scala.concurrent.ExecutionContext.Implicits.global

class Application(linkService: LinkService) extends Controller {

  def findLinks(query: String) = Action.async {
    val links = linkService.findLinks(query)
    links.map(response => Ok(views.html.index(response)))
  }

}