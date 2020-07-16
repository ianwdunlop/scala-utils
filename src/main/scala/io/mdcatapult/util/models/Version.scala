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
