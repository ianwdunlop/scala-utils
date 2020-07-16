package io.mdcatapult.util.path

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
