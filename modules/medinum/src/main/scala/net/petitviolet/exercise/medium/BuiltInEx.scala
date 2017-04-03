package net.petitviolet.exercise.medium

import scala.concurrent.{ExecutionContext, Future}

/**
 * exercise about built-in classes
 */
trait BuiltInEx {
  final private def sleepInt(n: Int)(implicit ec: ExecutionContext): Future[Int] =
    Future { Thread.sleep(n * 100); n }

  final protected def getFirstFinished(i: Int, j: Int)(implicit ec: ExecutionContext): Int = {
    val iSleep = sleepInt(i)
    val jSleep = sleepInt(j)
    firstFinishFuture(iSleep, jSleep)
  }

  def firstFinishFuture(iF: Future[Int], jF: Future[Int])(implicit ec: ExecutionContext): Int
}
