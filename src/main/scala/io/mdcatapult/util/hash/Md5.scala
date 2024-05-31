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

package io.mdcatapult.util.hash

import java.io.{File, FileInputStream}
import java.security.{DigestInputStream, MessageDigest}

object Md5 {

  /**
    * Generates and MD5 hash of the file contents
    * @param source file whose contents is to be hashed
    * @return
    */
  def md5(source: File): String = {
    val buffer = new Array[Byte](8192)
    val md5 = MessageDigest.getInstance("MD5")

    val dis = new DigestInputStream(new FileInputStream(source), md5)
    try {
      while (dis.read(buffer) != -1) {}
    } finally {
      dis.close()
    }

    md5.digest.map("%02x".format(_)).mkString
  }

  /**
    * Generates an md5 hash of a string
    * @param value raw text to be hashed
    * @return
    */
  def md5(value: String): String =
    md5(value.getBytes)

  /**
    * Generates an md5 hash of an array of bytes.
    * @param value raw text to be hashed
    * @return
    */
  def md5(value: Array[Byte]): String =
    MessageDigest.getInstance("MD5").digest(value).map("%02x".format(_)).mkString

}
