/*
 * Copyright 2024 Medicines Discovery Catapult
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.doclib.util.admin

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
