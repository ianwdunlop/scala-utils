package io.mdcatapult.util.time

import java.time.{LocalDateTime, ZoneId, ZoneOffset}

object FixedTimezoneNow {

  def utc(): FixedTimezoneNow =
    new FixedTimezoneNow(ZoneOffset.UTC)
}

class FixedTimezoneNow private (zone: ZoneId) extends Now {

  override def now(): LocalDateTime = LocalDateTime.now(zone)
}
