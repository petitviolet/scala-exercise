package net.petitviolet.exercise.medium

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.Try

/**
 * exercise about built-in classes
 */
trait BuiltInEx {
  final def getFirstFinished(i: Int, j: Int)(implicit ec: ExecutionContext): Int = {
    def sleepInt(n: Int)(implicit ec: ExecutionContext) = Future { Thread.sleep(n * 100); n }

    val iSleep = sleepInt(i)
    val jSleep = sleepInt(j)
    firstFinishFuture(iSleep, jSleep)
  }

  /**
   * return only the first completed value
   * @param iF
   * @param jF
   * @param ec
   * @return
   */
  protected def firstFinishFuture(iF: Future[Int], jF: Future[Int])(implicit ec: ExecutionContext): Int

  /**
   * compose Int of Try, Option, Either and Seq
   * if any of monad fails, return 0
   * else return multiple all values
   * @param t
   * @param o
   * @param e
   * @param s
   * @return
   */
  def composeMonads(t: Try[Int], o: Option[Int], e: Either[Throwable, Int], s: Seq[Int]): Int
}
