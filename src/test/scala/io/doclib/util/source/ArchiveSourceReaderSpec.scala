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

package io.doclib.util.source

import java.io.File
import io.doclib.util.io.stringToInputStream
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class ArchiveSourceReaderSpec extends AnyFreeSpec with Matchers {

  "An ArchiveSourceReader" - {
    "given the source is for a zip archive" - {
      "should give contents of both files in a list" in {
        val source = Source.fromFile(new File("sources/two-files.zip"))
        val reader = SourceReader()

        val content = reader.read(source)

//        val standardizedContent = content.map(x => x.replaceAll("\r\n", "\n"))

//        content should contain allElementsOf Seq(
//          "Here is some text...".stripMargin,
//          """Here is some more text...
//            |And even more!""".stripMargin.replace("\n", System.lineSeparator)
//        )
//val expectedContent = Seq(
//  """Here is some text...""".stripMargin,
//  """Here is some more text...
//    |And even more!""".stripMargin.replace(System.lineSeparator, "\n")
//)

//        assert(expectedContent.head.equals(content.head))
//        println(s"CONTENT......${content(1)}")
//        assert(expectedContent(1).equals(content(1)))
//
//        assert(content.equals(expectedContent))
val expectedContent = Seq(
  "Here is some text...".stripMargin,
  """Here is some more text...
     |And even more!""".stripMargin.replace(System.lineSeparator, "\n")
)
        content(1) diff expectedContent(1)

        content should contain allElementsOf expectedContent


      }

      "should throw InputStreamTooLongException when maxBytes is smaller that full size" in {
        val source = Source.fromFile(new File("sources/two-files.zip"))
        val reader = SourceReader().withMaxBytes(maxBytes = 20)

        val e = the [InputStreamTooLongException] thrownBy reader.read(source)

        e.maxLength should be (20)
      }
    }

    "given the source is for a single file" - {

      "should give content of that file" in {
        val source = Source(stringToInputStream(" some sample text "), "name")
        val reader = SourceReader()

        reader.read(source) should contain only "some sample text"
      }

      "should throw InputStreamTooLongException when maxBytes is smaller that full size" in {
        val source = Source(stringToInputStream("some sample text"), "name")
        val reader = SourceReader().withMaxBytes(maxBytes = 10)

        val e = the [InputStreamTooLongException] thrownBy reader.read(source)

        e.maxLength should be (10)
      }
    }
  }
}
