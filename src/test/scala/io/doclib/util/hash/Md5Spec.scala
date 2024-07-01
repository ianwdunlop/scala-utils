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

package io.doclib.util.hash

import better.files.File
import io.doclib.util.hash.Md5.md5
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.language.existentials

class Md5Spec extends AnyFreeSpec with Matchers {

  "A Md5" - {
    "should generate a consistent MD5 hash for" - {
      val text = "some text to hash"
      val md5Hash = "f97af9acb61dccbe5b660582bb2b0e39"

      "a file" in {
        val f =
          File("target/md5-test.txt")
            .write(text)
            .deleteOnExit()

        md5(f.toJava) should be (md5Hash)
      }
      "a string" in {
        md5(text) should be (md5Hash)
      }
      "an array of bytes" in {
        md5(text.getBytes) should be (md5Hash)
      }
    }
  }
}
