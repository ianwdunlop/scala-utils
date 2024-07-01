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

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{ExecutionContext, Future}

/** Run a function against some data when there is a need to limit the number of concurrent executions.
  * Typically the calls take some data of type C which is passed into a function that returns a value of type T.
  * All calls are curried to encourage a style of coding where the function is placed inside {} only
  * and not (), which should look more natural.
  */
trait LimitedExecution extends LazyLogging {

  /** Run function against data with the default level of concurrency.
    *
    * @param c input data
    * @param label message identifying what kind of functionality is being wrapped
    * @param f function that parses the data
    * @param ec context to run future under
    * @return result of applying function to the input data
    */
  def apply[C, T](c: C, label: String)(f: C => Future[T])(implicit ec: ExecutionContext): Future[T] =
    weighted(1)(c, label)(f)

  /** Run function against data with the a weighted level of concurrency.
    * A weight of 1 is equivalent to unweighted.
    * A weight of 0 is equivalent to unlimited.
    *
    * @param c input data
    * @param label message identifying what kind of functionality is being wrapped
    * @param f function that parses the data
    * @param ec context to run future under
    * @return result of applying function to the input data
    */
  def weighted[C, T](weight: Int)(c: C, label: String)(f: C => Future[T])(implicit ec: ExecutionContext): Future[T]

  /** Run function against data immediately.  There is no change to the amount of concurrency available.
    *
    * @param c input data
    * @param label message identifying what kind of functionality is being wrapped
    * @param f function that parses the data
    * @return result of applying function to the input data
    */
  def unlimited[C, T](c: C, label: String)(f: C => Future[T]): Future[T] = {
    logger.debug("Unlimited execution to run for {}", label)
    f(c)
  }
}
