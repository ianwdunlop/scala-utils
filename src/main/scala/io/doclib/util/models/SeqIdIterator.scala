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

package io.doclib.util.models

import java.util.concurrent.atomic.AtomicInteger

object SeqIdIterator {

  /**
    * Generate a SeqIdIterator.
    * @param i iterator to get a sequence of IDs from
    * @param count number of IDs to generate
    * @tparam T ID type
    * @return initialised SeqIdIterator
    */
  def from[T](i: IdIterator[T], count: Int): SeqIdIterator[T] =
    new SeqIdIterator(1.to(count).map(_ => i.next()))
}

/**
  * IdIterator that "generate" IDs by stepping through a pre-generated sequence of IDs.
  * @param values sequence of IDs
  * @tparam T type of ID - typically UUID or ObjectID
  */
class SeqIdIterator[T](values: IndexedSeq[T]) extends IdIterator[T] {

  /** Index of the next ID. */
  private val index = new AtomicInteger()

  override def next(): T = values(index.getAndIncrement())

  /**
    * Get a new SeqIdIterator with the same values as this, but with the next value being back at the start.
    * @return refreshed copy
    */
  def refreshCopy(): IdIterator[T] = new SeqIdIterator(values)
}
