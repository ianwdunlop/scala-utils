package io.mdcatapult.util.models.result

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class DeletionResultSpec extends AnyFreeSpec with Matchers {

  "A DeletionResult" - {
    "that is ok" - {
      "should be acknowledged" in {
        DeletionResult.ok.acknowledged should be (true)
      }
      "should have a nominal deleted count" in {
        DeletionResult.ok.deletedCount should be (1L)
      }
    }
  }
}
