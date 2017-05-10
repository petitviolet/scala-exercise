package net.petitviolet.exercise.medium

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success, Try }

class BuiltInExTest extends TestBase {
  import BuiltInExImpl._

  "getFirstFinished" should "return the first finished Value" in {
    def getFirstFinished(i: Int, j: Int)(implicit ec: ExecutionContext): Int = {
      def sleepInt(n: Int)(implicit ec: ExecutionContext) = Future { Thread.sleep(n * 100); n }

      val iSleep = sleepInt(i)
      val jSleep = sleepInt(j)
      firstFinishFuture(iSleep, jSleep)
    }

    getFirstFinished(10, 100) shouldBe 10
    getFirstFinished(300, 10) shouldBe 10
  }

  "composeMonads" should "return multiplied values" in {
    val t = new RuntimeException("fail")
    composeMonads(Try(1), Option(2), Right(3), Seq(4)) shouldBe 24
    composeMonads(Try(throw t), Option(2), Right(3), Seq(4)) shouldBe 0
    composeMonads(Try(1), None, Right(3), Seq(4)) shouldBe 0
    composeMonads(Try(1), Option(2), Left(t), Seq(4)) shouldBe 0
    composeMonads(Try(1), Option(2), Right(3), Nil) shouldBe 0
  }

  "composeDeepMonads" should "return summed values" in {
    val t = new RuntimeException("fail")
    def toF[A](a: => A): Future[A] = Future(a)(ec)
    val f = composeDeepMonads _

    whenReady(f(Success(Some(1)), toF(2) :: toF(3) :: toF(4) :: Nil, toF(Some(5)))) { r =>
      r shouldBe 15
    }
    whenReady(f(Success(None), toF(2) :: toF(3) :: toF(4) :: Nil, toF(Some(5)))) { r =>
      r shouldBe 14
    }
    whenReady(f(Success(Some(1)), toF(2) :: toF(3) :: toF(4) :: Nil, toF(None))) { r =>
      r shouldBe 10
    }

    whenReady(f(Failure(t), toF(2) :: toF(3) :: toF(4) :: Nil, toF(Some(5))).failed) { r =>
      r shouldBe t
    }
    whenReady(f(Success(Some(1)), toF(2) :: toF(throw t) :: toF(4) :: Nil, toF(Some(5))).failed) { r =>
      r shouldBe t
    }
    whenReady(f(Success(Some(1)), toF(2) :: toF(3) :: toF(4) :: Nil, toF(throw t)).failed) { r =>
      r shouldBe t
    }
    whenReady(f(Success(Some(1)), toF(2) :: toF(3) :: toF(4) :: Nil, toF(throw t)).failed) { r =>
      r shouldBe t
    }
  }
}
