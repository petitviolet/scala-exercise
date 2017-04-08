package net.petitviolet.exercise.medium

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, ExecutionContext, Future, Promise }
import scala.util.Try

object BuiltInExImpl extends BuiltInEx {
  override def firstFinishFuture(iF: Future[Int], jF: Future[Int])(implicit ec: ExecutionContext): Int = {
    val p: Promise[Int] = Promise()
    iF.onComplete { p.tryComplete }
    jF.onComplete { p.tryComplete }
    Await.result(p.future, Duration.Inf)
  }

  /**
   * compose Int of Try, Option, Either and Seq
   * if any of monad fails, return 0
   * else return multiple all values
   *
   * @param t
   * @param o
   * @param e
   * @param s
   * @return
   */
  override def composeMonads(t: Try[Int], o: Option[Int], e: Either[Throwable, Int], s: Seq[Int]): Int = {
    (for {
      _t <- t.toOption
      _o <- o
      _e <- e.right.toOption
      _s <- s.reduceOption { _ * _ }
    } yield _t * _o * _e * _s) getOrElse 0
  }
}
