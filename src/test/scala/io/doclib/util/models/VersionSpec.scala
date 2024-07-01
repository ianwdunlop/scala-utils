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

package io.doclib.util.models

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
