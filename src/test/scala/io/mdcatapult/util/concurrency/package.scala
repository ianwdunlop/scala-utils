package io.mdcatapult.util

import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger

import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.time.{Seconds, Span}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

package object concurrency {

  val concurrentTestTimeout = Timeout(Span(10, Seconds))

  /** Run functions concurrently get return the maximum number of concurrently running functions.
    * It lines up a set of Future by holding them all on a countdown latch until all are ready.  Once there they are all
    * released to be then throttled by the LimitedExecution.  The caller supplies a method of SemaphoreLimitedExecution
    * to run, such as SemaphoreLimitedExecution.weighted(3).  A function is run against this that increments a count of
    * all functions currently running which is returned before decrementing the currently running count.
    *
    * @param f a function of SemaphoreLimitedExecution that is to run a function, such as apply() or weighted()
    * @return a future holding the maximum number of functions that were concurrently running
    */
  def runFunctionsConcurrently(f: (Int, String) => (Int => Future[Int]) => Future[Int]): Future[Int] = {
    val latch = new CountDownLatch(1000)

    val running = new AtomicInteger(0)

    val concurrentRunningCounts: Seq[Future[Int]] =
      0.to(latch.getCount.toInt).map( _ => {
        latch.countDown()
        f(0, "run functions concurrently")((_: Int) =>
          Future {
            val currentlyRunningCount = running.incrementAndGet()

            Thread.sleep(1, 0)
            running.decrementAndGet()

            currentlyRunningCount
          }
        )
      })

    Future.sequence(concurrentRunningCounts).map(_.max)
  }
}
