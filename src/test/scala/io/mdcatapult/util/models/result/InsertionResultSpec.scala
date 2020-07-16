package io.mdcatapult.util.models.result

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class InsertionResultSpec extends AnyFreeSpec with Matchers {

  "A InsertionResult" - {
    "that is ok" - {
      "should be acknowledged" in {
        InsertionResult.ok.acknowledged should be (true)
      }
      "should have a nominal insertion count" in {
        InsertionResult.ok.insertedCount should be (1L)
      }
    }

    "that represents nothing being inserted" - {
      "should be acknowledged" in {
        InsertionResult.nothing.acknowledged should be (true)
      }
      "should have no insertion count" in {
        InsertionResult.nothing.insertedCount should be (0L)
      }
    }
  }

}
