package io.mdcatapult.util.hash

import better.files.File
import io.mdcatapult.util.hash.Md5.md5
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
