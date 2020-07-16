package io.mdcatapult.util.time

import java.time.LocalDateTime

trait Now {

  /** Get a timestamp of the current instant in time.
    */
  def now(): LocalDateTime
}
