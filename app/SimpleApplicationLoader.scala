import play.api._
import play.api.ApplicationLoader.Context
import services._
import router.Routes

class SimpleApplicationLoader extends ApplicationLoader {
  def load(context: Context) = {
    new ApplicationComponents(context).application
  }
}

object ControllerDependencies {
  lazy val logService = new LogService
  lazy val linkService = new LinkService(logService)
}

class ApplicationComponents(context: Context) extends BuiltInComponentsFromContext(context) {  
  lazy val applicationController = new controllers.Application(ControllerDependencies.linkService)
  lazy val assets = new controllers.Assets(httpErrorHandler)
  override lazy val router = new Routes(httpErrorHandler, applicationController, assets)
}