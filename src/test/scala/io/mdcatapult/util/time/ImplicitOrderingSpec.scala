package io.mdcatapult.util.time

import java.time.LocalDateTime

import io.mdcatapult.util.time.ImplicitOrdering.localDateOrdering
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class ImplicitOrderingSpec extends AnyFreeSpec with Matchers {

  "An ImplicitOrdering" - {
    "should place earliest times first" in {
      val times =
        Seq(
          LocalDateTime.parse("2019-10-01T12:00:00"),
          LocalDateTime.parse("2019-10-01T12:00:01"),
          LocalDateTime.parse("2019-10-01T11:59:59"),
          LocalDateTime.parse("2019-10-01T12:00:00"),
          LocalDateTime.parse("2019-10-01T12:00:03"),
        )

      times.sorted should be (Seq(times(2), times(3), times.head, times(1), times(4)))
    }

    "should place nulls before all other times (i.e. are earliest of all)" in {
      val times =
        Seq(
          LocalDateTime.parse("2019-10-01T12:00:00"),
          LocalDateTime.parse("2019-10-01T12:00:01"),
          LocalDateTime.parse("2019-10-01T11:59:59"),
          null,
          LocalDateTime.parse("2019-10-01T12:00:00"),
          LocalDateTime.parse("2019-10-01T12:00:03"),
          null,
        )

      times.sorted should be (Seq(null, null, times(2), times(4), times.head, times(1), times(5)))
    }
  }
}
