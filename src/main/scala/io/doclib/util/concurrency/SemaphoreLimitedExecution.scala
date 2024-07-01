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

package io.doclib.util.concurrency

import monix.execution.AsyncSemaphore

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{ExecutionContext, Future}

object SemaphoreLimitedExecution extends LimitedExecutionFactory {

  /** Create a SemaphoreLimitedExecution that controls concurrency using an encapsulated Semaphore that is fair.
    * Fair means that this semaphore will guarantee first-in first-out granting of permits under contention.
    *
    * @param concurrency number of semaphore permits
    * @return LimitedExecution configured with a semaphore
    */
  override def create(concurrency: Int): SemaphoreLimitedExecution = create(AsyncSemaphore(concurrency))

  /** Create a SemaphoreLimitedExecution that controls concurrency with an exposed Semaphore.  Because the semaphore is
    * passed in it is possible that it is manipulated outside of this LimitedExecution, which could then leak permits.
    * As such it is generally safer to pass semaphore limits rather than an actual semaphore.
    *
    * @param s semaphore to control concurrency
    * @return LimitedExecution configured with a semaphore
    */
  def create(s: AsyncSemaphore): SemaphoreLimitedExecution = new SemaphoreLimitedExecution(s)
}

/** Control function execution concurrency using a JDK semaphore.  It is responsible for ensuring that acquired permits
  * are released once function execution has finished.
  *
  * @param s semaphore
  */
class SemaphoreLimitedExecution private (s: AsyncSemaphore) extends LimitedExecution with LazyLogging {

  /** @inheritdoc */
  override def weighted[C, T](weight: Int)(c: C, label: String)(f: C => Future[T])(implicit ec: ExecutionContext): Future[T] = {
    logger.debug("Acquire lock of weight {} for {}", weight, label)
    s.acquireN(weight).flatMap { _ =>
      try {
        logger.debug("Lock acquired for {}", label)
        val result = f(c)
        result.onComplete(_ => {
          logger.debug("Release lock of weight {} for {}", weight, label)
          s.releaseN(weight)
        })
        result
      } catch {
        case e: Exception =>
          logger.debug("Release lock of weight {} for {} on error: {}", weight, label, e)
          s.releaseN(weight)
          throw e
      }
    }
  }
}
