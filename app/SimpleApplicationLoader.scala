import play.api._
import play.api.ApplicationLoader.Context
import services._
import router.Routes

class SimpleApplicationLoader extends ApplicationLoader {
  def load(context: Context) = {
    new ApplicationComponents(context).application
  }
}

class ApplicationComponents(context: Context) extends BuiltInComponentsFromContext(context) {
  lazy val logService = new LogService
  lazy val linkService = new LinkService(logService)
  lazy val applicationController = new controllers.Application(linkService)
  lazy val assets = new controllers.Assets(httpErrorHandler)
  lazy val router = new Routes(httpErrorHandler, applicationController, assets)
}