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

import com.typesafe.config.Config
import play.api.libs.json.{Format, Json}

object Version  {
  implicit val prefetchOriginFormatter: Format[Version] = Json.format[Version]

  /**
    * Generates ConsumerVersion from configuration.
    * @param c config
    * @return version
    */
  def fromConfig(c: Config): Version =
    Version(
      number = c.getString("version.number"),
      major = c.getInt("version.major"),
      minor = c.getInt("version.minor"),
      patch = c.getInt("version.patch"),
      hash = c.getString("version.hash")
    )
}

case class Version(number: String, major: Int, minor: Int, patch: Int, hash: String)
