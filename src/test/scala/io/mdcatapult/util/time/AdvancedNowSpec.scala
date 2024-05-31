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

package io.mdcatapult.util.time

import java.time.LocalDateTime
import java.time.ZoneOffset.UTC

import org.scalatest.Assertion
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class AdvancedNowSpec extends AnyFreeSpec with Matchers {

  "An AdvancedNow" - {

    "when configured from the current time" - {
      "should increment in milliseconds on subsequent calls to show time passing" in {
        incrementedByOneMillisecond(AdvancingNow.fromCurrentTime())
      }
    }

    "when configured with a specified time" - {
      val startTime = LocalDateTime.parse("2019-10-01T12:00:00")
      val now = new AdvancingNow(startTime)

      "should give the configured time the first time now() is called" in {
        now.now() should be (startTime)
      }

      "should increment in milliseconds on subsequent calls to show time passing" in {
        incrementedByOneMillisecond(now)
      }
    }
  }

  private def incrementedByOneMillisecond(n: AdvancingNow): Assertion = {
    val a = n.now()
    val b = n.now()

    b.toInstant(UTC).toEpochMilli - a.toInstant(UTC).toEpochMilli should be (1)
  }
}
