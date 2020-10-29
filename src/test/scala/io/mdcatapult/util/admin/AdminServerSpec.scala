package io.mdcatapult.util.admin

import scala.jdk.CollectionConverters._
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.matchers.should.Matchers
import org.scalatest.freespec.AnyFreeSpec

class AdminServerSpec extends AnyFreeSpec with Matchers {
  private val config: Config = ConfigFactory.parseMap(Map("admin.port" -> 9090).asJava)
  private val adminServer: Server = Server(config)
  def get(url: String) = scala.io.Source.fromURL(url).mkString

  "An admin server" - {
    "when initialised without a healthcheck" - {
      "should return \"OK\"" in {
        adminServer.start()
        Thread.sleep(2500)
        val res = get(s"${adminServer.address}/health")
        res should be("\"OK\"")
        adminServer.stop()
      }
    }
  }
}
