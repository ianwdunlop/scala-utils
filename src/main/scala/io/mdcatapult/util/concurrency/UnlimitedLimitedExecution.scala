package io.mdcatapult.util.concurrency
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{ExecutionContext, Future}

/** A stub implementation of LimitedExecution that makes no attempt to actually limit execution. */
object UnlimitedLimitedExecution extends LimitedExecution with LazyLogging with LimitedExecutionFactory {

  override def create(concurrency: Int): LimitedExecution = this

  override def weighted[C, T](weight: Int)(c: C, label: String)(f: C => Future[T])(implicit ec: ExecutionContext): Future[T] =
    unlimited(c, label)(f)

}
