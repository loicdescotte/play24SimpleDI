import play.api._
import play.api.ApplicationLoader.Context
import services.ServicesComponent
import router.Routes

object ApplicationServices extends ServicesComponent

class SimpleApplicationLoader extends ApplicationLoader {
  def load(context: Context) = {
    new ApplicationComponents(context).application
  }
}

class ApplicationComponents(context: Context) extends BuiltInComponentsFromContext(context) {
  lazy val applicationController = new controllers.Application(ApplicationServices)
  lazy val assets = new controllers.Assets(httpErrorHandler)
  lazy val router = new Routes(httpErrorHandler, applicationController, assets)
}