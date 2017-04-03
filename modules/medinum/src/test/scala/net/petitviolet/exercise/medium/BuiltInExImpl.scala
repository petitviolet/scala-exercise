package net.petitviolet.exercise.medium

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.util.Success

object BuiltInExImpl extends BuiltInEx {
  override def firstFinishFuture(iF: Future[Int], jF: Future[Int])(implicit ec: ExecutionContext): Int = {
    val p: Promise[Int] = Promise()
    iF.onComplete { p.tryComplete }
    jF.onComplete { p.tryComplete }
    Await.result(p.future, Duration.Inf)
  }
}
