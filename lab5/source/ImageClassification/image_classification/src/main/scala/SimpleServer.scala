
import java.io.{ByteArrayInputStream, File}
import javax.imageio.ImageIO

//import java.util.Base64
import sun.misc.BASE64Decoder
import unfiltered.filter.Plan
import unfiltered.jetty.SocketPortBinding
import _root_.unfiltered.request.{Body, Path, _}
import unfiltered.response.{Ok, ResponseString}


object SimplePlan extends Plan {
  def intent = {
    case req@GET(Path("/get")) => {
      Ok ~> ResponseString(IPApp.testImage("data/test/BlochSchool/BlochExecutivehall3.jpg"))
    }

      case req@POST(Path("/get_custom")) => {
        val imageByte = (new BASE64Decoder()).decodeBuffer(Body.string(req));
        val bytes = new ByteArrayInputStream(imageByte)
        val image = ImageIO.read(bytes)
        ImageIO.write(image, "png", new File("image.png"))
      }
        Ok ~> ResponseString(IPApp.testImage("image.png"))
  }
}
object SimpleServer extends App {
  val bindingIP = SocketPortBinding(host = "127.0.0.1", port = 8080)
  unfiltered.jetty.Server.portBinding(bindingIP).plan(SimplePlan).run()
}
