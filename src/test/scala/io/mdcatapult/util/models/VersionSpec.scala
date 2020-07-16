package io.mdcatapult.util.models

import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class VersionSpec extends AnyFreeSpec with Matchers {

  "A Version" - {
    "should be initialised from config" in {
      val config: Config = ConfigFactory.parseString(
        """version {
          |  number = "2.1.6-SNAPSHOT",
          |  major = 2,
          |  minor =  1,
          |  patch = 6,
          |  hash =  "20837d29"
          |}
          |""".stripMargin)

      val v = Version.fromConfig(config)

      v.number should be ("2.1.6-SNAPSHOT")
      v.major should be (2)
      v.minor should be (1)
      v.patch should be (6)
      v.hash should be ("20837d29")
    }
  }
}
