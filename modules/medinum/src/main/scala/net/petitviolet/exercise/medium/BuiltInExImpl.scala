package net.petitviolet.exercise.medium

import scala.concurrent.duration.Duration
import scala.concurrent._
import scala.util._

object BuiltInExImpl extends BuiltInEx {
  override def firstFinishFuture(iF: Future[Int], jF: Future[Int])(implicit ec: ExecutionContext): Int = {
    val p: Promise[Int] = Promise()
    iF.onComplete { p.tryComplete }
    jF.onComplete { p.tryComplete }
    Await.result(p.future, Duration.Inf)
  }

  override def composeMonads(t: Try[Int], o: Option[Int], e: Either[Throwable, Int], s: Seq[Int]): Int = {
    (for {
      _t <- t.toOption
      _o <- o
      _e <- e.toOption
      _s <- s.reduceOption { _ * _ }
    } yield _t * _o * _e * _s) getOrElse 0
  }

  override def composeDeepMonads(
    to: Try[Option[Int]],
    sf: Seq[Future[Int]],
    fo: Future[Option[Int]]
  ): Future[Int] = {
    def getOr0(iO: Option[Int]): Int = iO getOrElse 0

    val aF: Future[Int] = Future.fromTry(to map getOr0)
    val bF: Future[Int] = Future.sequence(sf).map { _.sum }.flatMap { x =>
      fo.map { yO =>
        x + getOr0(yO)
      }
    }
    for {
      a <- aF
      b <- bF
    } yield a + b
  }
}
