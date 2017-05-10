package net.petitviolet.exercise.medium

import scala.concurrent.{ ExecutionContext, Future }
import scala.util._

/**
 * 組み込み型に関する問題
 */
trait BuiltInEx {
  /**
   * ExecutionContextはこれを使う
   */
  final protected implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  /**
   * 引数に与えたFutureから最初に完了したものの値を返却する
   */
  protected def firstFinishFuture(iF: Future[Int], jF: Future[Int])(implicit ec: ExecutionContext): Int

  /**
   * Try, Option, Either, SeqにくるまれたIntの値を全てかけあわせる
   * Success(1), Some(2), Right(3), Seq(4, 5) => 1 * 2 * 3 * 4 * 5
   * 一つでも失敗が含まれる場合は0を返却する
   * Success(1), Some(2), Left(Exception()), Seq(4, 5) => 0
   */
  def composeMonads(t: Try[Int], o: Option[Int], e: Either[Throwable, Int], s: Seq[Int]): Int

  /**
   * 入れ子になったMonadが保持する値を足し合わせてFutureにくるんで返却する
   * Success(Some(1)), Seq(Success(2), Success(3)), Success(Some(4)) => Future(1 + 2 + 3 + 4)
   * 一つでも失敗が含まれる場合はFailureを返却する
   * Success(None), Seq(Success(2), Success(3)), Success(Some(4)) => Failure
   */
  def composeDeepMonads(to: Try[Option[Int]], sf: Seq[Future[Int]], fo: Future[Option[Int]]): Future[Int]
}
