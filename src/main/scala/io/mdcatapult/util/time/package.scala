package io.mdcatapult.util

package object time {

  implicit val nowUtc: FixedTimezoneNow = FixedTimezoneNow.utc()
}
