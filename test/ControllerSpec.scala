package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import org.specs2.mock.Mockito

import services._
import controllers._

class ControllerSpec extends Specification with Mockito {

  object MockServices extends ServicesComponent {
    override val logService = mock[LogService]
    override val linkService = {
      val mockLinkService = mock[LinkService]
      mockLinkService.findLinks("hello") returns Future("http://coucou.com")
      mockLinkService
    }
  }

  "Application" should {

    "display query results" in {

      val app = new Application(MockServices)
      val response = app.findLinks("hello")(FakeRequest())
      
      status(response) must equalTo(OK)
      contentType(response) must beSome("text/html")
      contentAsString(response) must contain("http://coucou.com")

    }
  }
}