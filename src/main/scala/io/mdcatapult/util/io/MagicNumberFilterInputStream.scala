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

package io.mdcatapult.util.io

import java.io.{FilterInputStream, InputStream}

/** Filter content based upon the presence of a "magic number" at the start of the content.  Many defined file formats,
  * mainly binary in nature, place a typically unlikely set of bytes at the start of the content to provide a means of
  * quickly determining if the file matches its format.  For example, gzip always begins with 1f 8b.
  */
object MagicNumberFilterInputStream {

  /** Create a MagicNumberFilterInputStream that will truncate if the start of the read content matches any
    * of the byte arrays.  The stream will then behave as if the end of the file has been reached.
    */
  def toTruncateAnyWith(magicNumbers: List[Array[Byte]])(in: InputStream): MagicNumberFilterInputStream =
    new MagicNumberFilterInputStream(in, magicNumbers)
}

class MagicNumberFilterInputStream private (in: InputStream, magicNumbers: List[Array[Byte]])
  extends FilterInputStream(in) {

  private var magicHeaderTested = false
  private var truncate = false

  override def read(b: Array[Byte], off: Int, len: Int): Int = {

    def zeroOutBuffer(): Unit =
      for (
        i <- off.until(Math.min(off + len, b.length))
      ) {
        b(i) = 0
      }

    val endOfStream = -1

    if (truncate) return endOfStream

    val readLength = super.read(b, off, len)

    if (!magicHeaderTested) {
      magicHeaderTested = true

      val magicNumberMatch = magicNumbers.exists(_.zip(b).forall(p => p._1 == p._2))

      if (magicNumberMatch) {
        truncate = true
        zeroOutBuffer()
        return endOfStream
      }
    }

    readLength
  }
}
