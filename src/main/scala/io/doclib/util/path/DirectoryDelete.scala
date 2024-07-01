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

trait DirectoryDelete {

  /**
    * Delete a sequence of directories from the file system
    * @param directories sequence of directories to be deleted
    */
  def deleteDirectories(directories: Seq[File]): Unit
}

/**
  * Convenience method to delete a list of directories
  */
object DirectoryDeleter extends DirectoryDelete {

  override def deleteDirectories(directories: Seq[File]): Unit = {
    directories.map(_.delete(swallowIOExceptions = true))
  }

}
