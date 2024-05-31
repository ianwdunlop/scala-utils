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
import java.util.concurrent.atomic.AtomicInteger

object AdvancingNow {

  /**
    * AdvancingNow starting from the current time in UTC.  Useful for testing.
    * @return
    */
  def fromCurrentTime(): AdvancingNow =
    new AdvancingNow(nowUtc.now())
}

/**
  * Test implementation of Now that ensures that each generated timestamp differs from the the one before
  * by a millisecond.
  *
  * @param start the first timestamp to be generated
  */
class AdvancingNow(val start: LocalDateTime) extends Now {

  private val offset = new AtomicInteger()

  override def now(): LocalDateTime =
    start.plusNanos(offset.getAndIncrement() * 1000 * 1000)
}
