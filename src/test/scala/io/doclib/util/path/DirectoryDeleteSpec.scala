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

package io.doclib.util.path

import better.files.File
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class DirectoryDeleteSpec extends AnyFreeSpec with Matchers {

  "A DirectoryDeleter" - {
    "when deleting a sequence of directories" - {
      val testDirectory =
        File(s"target/dir-delete-test")

      if (testDirectory.exists) testDirectory.delete()
      testDirectory.createDirectory()

      "should delete successfully" - {
        "given an empty sequence" in {
          DirectoryDeleter.deleteDirectories(Seq.empty)
        }
        "given a directory with file" in {
          val dir = testDirectory.createChild("single-file", asDirectory = true)
          val file = dir.createChild("file").write("some text")

          dir.exists should be (true)
          file.exists should be (true)

          DirectoryDeleter.deleteDirectories(Seq(dir))

          file.exists should be (false)
          dir.exists should be (false)
        }
        "given a directory that has a nested structure" in {
          val dir = testDirectory.createChild("dir", asDirectory = true)
          val file = dir.createChild("file").write("some text")
          val subDir = dir.createChild("sub-dir", asDirectory = true)
          val subDirFile = dir.createChild("sub-dir-file").write("some other text")

          dir.exists should be (true)
          file.exists should be (true)
          subDir.exists should be (true)
          subDirFile.exists should be (true)

          DirectoryDeleter.deleteDirectories(Seq(dir))

          subDirFile.exists should be (false)
          subDir.exists should be (false)
          file.exists should be (false)
          dir.exists should be (false)
        }
        "given a sequence of multiple directories" in {
          val dir = testDirectory.createChild("dir", asDirectory = true)
          val file = dir.createChild("file").write("some text")

          val otherDir = testDirectory.createChild("other-dir", asDirectory = true)
          val otherDirFile = dir.createChild("other-dir-file").write("some other text")

          dir.exists should be (true)
          file.exists should be (true)
          otherDir.exists should be (true)
          otherDirFile.exists should be (true)

          DirectoryDeleter.deleteDirectories(Seq(dir, otherDir))

          otherDirFile.exists should be (false)
          otherDir.exists should be (false)
          file.exists should be (false)
          dir.exists should be (false)
        }
        "given a sequence of directories with one unknown" in {
          val dir = testDirectory.createChild("dir", asDirectory = true)
          val file = dir.createChild("file").write("some text")

          val otherDir = testDirectory.createChild("other-dir", asDirectory = true)
          val otherDirFile = dir.createChild("other-dir-file").write("some other text")

          val unknownDir = testDirectory.createChild("unknown-dir", asDirectory = true)
          unknownDir.delete()

          unknownDir.exists should be (false)

          dir.exists should be (true)
          file.exists should be (true)
          otherDir.exists should be (true)
          otherDirFile.exists should be (true)

          DirectoryDeleter.deleteDirectories(Seq(dir, unknownDir, otherDir))

          otherDirFile.exists should be (false)
          otherDir.exists should be (false)
          file.exists should be (false)
          dir.exists should be (false)
        }
        "given a directory actually points to a file" in {
          val dir = testDirectory.createChild("dir", asDirectory = true)
          val file = dir.createChild("file").write("some text")

          file.exists should be (true)

          DirectoryDeleter.deleteDirectories(Seq(file))

          file.exists should be (false)
          dir.exists should be (true)
        }
      }
    }
  }
}
