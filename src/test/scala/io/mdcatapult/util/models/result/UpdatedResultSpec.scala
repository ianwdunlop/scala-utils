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

package io.mdcatapult.util.models.result

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class UpdatedResultSpec extends AnyFreeSpec with Matchers {

  "A InsertionResult" - {
    "that is ok" - {
      val result = UpdatedResult.ok

      "should be acknowledged" in {
        result.acknowledged should be (true)
      }
      "should declare changes made" in {
        result.changesMade should be (true)
      }
      "should have a nominal modified count" in {
        result.modifiedCount should be (1L)
      }
      "should have a nominal matched count" in {
        result.matchedCount should be (1L)
      }
    }

    "that represents nothing being inserted" - {
      val result = UpdatedResult.nothing

      "should be acknowledged" in {
        result.acknowledged should be (true)
      }
      "should declare that no changes have been made" in {
        result.changesMade should be (false)
      }
      "should have no insertion count" in {
        result.modifiedCount should be (0L)
      }
      "should have a nominal matched count" in {
        result.matchedCount should be (0L)
      }
    }

    "that represents a failure to insert" - {
      val result = UpdatedResult.fail

      "should not be acknowledged" in {
        result.acknowledged should be (false)
      }
      "should declare that no changes have been made" in {
        result.changesMade should be (false)
      }
      "should have no insertion count" in {
        result.modifiedCount should be (0L)
      }
      "should have a nominal matched count" in {
        result.matchedCount should be (0L)
      }
    }
  }

}
