package io.mdcatapult.util.admin

import java.util.concurrent.Executors

import scala.jdk.CollectionConverters._
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.matchers.should.Matchers
import org.scalatest.freespec.AnyFreeSpec

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

class AdminServerSpec extends AnyFreeSpec with Matchers {
  implicit val context: ExecutionContextExecutor = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())
  private val config: Config = ConfigFactory.parseMap(Map("admin.port" -> 9090).asJava)
  private val adminServer: Server = Server(config)
  private val f = Future {
    adminServer.start()
  }

  def get(url: String) = scala.io.Source.fromURL(url).mkString

  f.onComplete( _ => {
    "An admin server" - {
      "when initialised without a healthcheck" - {
        "should return \"OK\"" in {
          val res = get(s"${adminServer.address}/health")
          res should be("\"OK\"")
        }
      }
    }

    adminServer.stop()
  })
}
