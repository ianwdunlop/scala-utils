package io.mdcatapult.util.concurrency

import org.scalatest.concurrent.ScalaFutures.whenReady
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UnlimitedLimitedExecutionSpec extends AnyFreeSpec with Matchers {

  private val runFunctionsConcurrently = runConcurrently(waitForAll = true) _

  "A UnlimitedLimitedExecution" - {
    "when wrapping a function by default" - {
      "should be able to run more than 1 function run concurrently when concurrency limit is 1" in {
        val executor = UnlimitedLimitedExecution.create(1)

        whenReady(runFunctionsConcurrently(executor.apply), concurrentTestTimeout) { maxConcurrency =>
          maxConcurrency should be > 1
        }
      }

      "should run once concurrency is available" in {
        val executor = UnlimitedLimitedExecution.create(1)

        whenReady(executor("run", "label 1") { x => Future.successful("has " + x) }, concurrentTestTimeout) {
          _ should be("has run")
        }
      }

      "should allow additional call once an earlier one has finished" in {
        val executor = UnlimitedLimitedExecution.create(1)

        executor("run", "label 2a") { x => Future.successful("has " + x) }

        whenReady(executor("run later", "label 2b") { x => Future.successful("has " + x) }, concurrentTestTimeout) {
          _ should be("has run later")
        }
      }

      "should allow additional call once an earlier one has finished when first one fails" in {
        val executor = UnlimitedLimitedExecution.create(1)

        executor("run", "label 2a") { _ => Future.failed(new RuntimeException("error")) }

        whenReady(executor("run later", "label 2b") { x => Future.successful("has " + x) }, concurrentTestTimeout) {
          _ should be("has run later")
        }
      }
    }

    "when wrapping a function with a weighting" - {

      "should be able to run more than 1 function of weight 3 run concurrently when concurrency limit is 3" in {
        val executor = UnlimitedLimitedExecution.create(3)

        whenReady(runFunctionsConcurrently(executor.weighted(3)), concurrentTestTimeout) { maxConcurrency =>
          maxConcurrency should be > 1
        }
      }

      "should execute with a weight a function when sufficient concurrency is available" in {
        val executor = UnlimitedLimitedExecution.create(5)

        whenReady(executor.weighted(5)("run", "label 3") { x => Future.successful("has " + x) }, concurrentTestTimeout) {
          _ should be("has run")
        }
      }

      "should allow additional weighted call once an earlier one has finished" in {
        val executor = UnlimitedLimitedExecution.create(5)

        executor.weighted(5)("run", "label 4a") { x => Future.successful("has " + x) }

        whenReady(executor.weighted(5)("run later", "label 4b") { x => Future.successful("has " + x) }, concurrentTestTimeout) {
          _ should be("has run later")
        }
      }
    }

    "when wrapping a function with unlimited weighting" - {
      "should not affect concurrency" in {
        val executor = UnlimitedLimitedExecution.create(1)

        whenReady(
          executor.unlimited("unlimited", "label 5") { x => {
            executor("run after", "label 5") { y => Future.successful(s"has $y $x") }
          }}, concurrentTestTimeout
        ) {
          _ should be("has run after unlimited")
        }
      }

      "should allow unlimited call run even if concurrency is exhausted" in {
        val executor = UnlimitedLimitedExecution.create(1)

        whenReady(
          executor("run", "label") { x => {
            executor.unlimited("no concurrency left", "label") { y => Future.successful(s"has $x with $y") }
          }}, concurrentTestTimeout
        ) {
          _ should be("has run with no concurrency left")
        }
      }
    }
  }

}
