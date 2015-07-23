import play.api._
import play.api.ApplicationLoader.Context
import scala.concurrent.Future
import play.api.libs.ws.ning.NingWSComponents
import services._
import router.Routes

class SimpleApplicationLoader extends ApplicationLoader {
  def load(context: Context) = {
    new ApplicationComponents(context).application
  }
}

class ApplicationComponents(context: Context) extends BuiltInComponentsFromContext(context) with NingWSComponents {

  lazy val logService = new LogService
  lazy val linkService = new LinkService(logService, wsClient)  

  lazy val applicationController = new controllers.Application(linkService)
  applicationLifecycle.addStopHook(() => Future.successful(linkService.cleanup))
  lazy val assets = new controllers.Assets(httpErrorHandler)
  override lazy val router = new Routes(httpErrorHandler, applicationController, assets)
}