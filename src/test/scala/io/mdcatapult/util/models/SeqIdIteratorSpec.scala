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

package io.mdcatapult.util.models

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class SeqIdIteratorSpec extends AnyFreeSpec with Matchers {

  "A SeqIdIterator" - {
    "when initialised with a list of ids" - {
      "should iterate through those ids on successive calls to next()" in {
        val iter = new SeqIdIterator(IndexedSeq("first", "second", "third"))

        iter.next() should be ("first")
        iter.next() should be ("second")
        iter.next() should be ("third")
      }
    }

    "when a refreshed copy after some value have bee iterated through" - {
      val iter = new SeqIdIterator(IndexedSeq("first", "second", "third", "fourth", "fifth"))

      iter.next() should be ("first")
      iter.next() should be ("second")

      val copy = iter.refreshCopy()

      "original should continue to iterate" in {
        iter.next() should be ("third")
      }
      "copy should iterate through same values from the start" in {
        copy.next() should be ("first")
        copy.next() should be ("second")
        copy.next() should be ("third")
      }
    }

    "when initialised against a known iterator" - {
      "should generate at least the configured number of values" in {
        val iter = SeqIdIterator.from(UuidIdIterator, 4)

        1.to(4).map(_ => iter.next()).toSet should have size 4
      }
    }
  }
}
