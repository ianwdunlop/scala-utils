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

package io.doclib.util.io

import java.io.InputStream

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class MagicNumberFilterInputStreamSpec extends AnyFreeSpec with Matchers {

  private def filteredInputStream(in: InputStream): InputStream =
    MagicNumberFilterInputStream
      .toTruncateAnyWith(
        List("RDX2", "RDA2").map(_.getBytes)
      )(in)

  "A MagicNumberFilterInputStream" - {
    "given first magic number matches" - {
      behave like readingIsTruncated("RDX2 is to be matched")
    }

    "given magic number matched is of than the first" - {
      behave like readingIsTruncated("RDA2 is to be matched")
    }

    "given no magic number is matched at start of stream" - {
      val text = "Here is some text that is expected to be read"
      val moreText = "RDX2 is magic, but isn't at the start"

      val in = filteredInputStream(stringToInputStream(text + moreText))

      "should read content" in {
        val bufferSize = text.getBytes.length
        val buffer = new Array[Byte](bufferSize)

        val bytesRead = in.read(buffer, 0, buffer.length)

        buffer should equal (text.getBytes)
        bytesRead should be (bufferSize)
      }

      "should read content even if magic number occurs on second read" in {
        val bufferSize = moreText.getBytes.length
        val buffer = new Array[Byte](bufferSize)

        val bytesRead = in.read(buffer, 0, buffer.length)

        buffer should equal (moreText.getBytes)
        bytesRead should be (bufferSize)
      }
    }
  }

  private def readingIsTruncated(text: String): Unit = {
    val bufferSize = text.getBytes.length

    val in =
      filteredInputStream(
        stringToInputStream(text + "extra text beyond buffer limit is not read")
      )

    "should give no content" - {
      val buffer = new Array[Byte](bufferSize)
      val bytesRead = in.read(buffer, 0, buffer.length)
      buffer should equal (new Array[Byte](bufferSize))
      bytesRead should be (-1)
    }

    "should give no more content after truncating" in {
      val buffer = new Array[Byte](bufferSize)
      val bytesRead = in.read(buffer, 0, buffer.length)

      buffer should equal (new Array[Byte](bufferSize))
      bytesRead should be (-1)
    }
  }
}
