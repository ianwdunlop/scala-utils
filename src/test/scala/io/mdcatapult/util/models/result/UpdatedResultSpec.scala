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
