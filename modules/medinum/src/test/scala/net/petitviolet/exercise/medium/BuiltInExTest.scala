package net.petitviolet.exercise.medium

import org.scalatest.{ FlatSpec, GivenWhenThen, Matchers }

import scala.util.Try

class BuiltInExTest extends FlatSpec with Matchers with GivenWhenThen {
  import BuiltInExImpl._
  import scala.concurrent.ExecutionContext.Implicits.global

  "getFirstFinished" should "return the first finished Value" in {
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
}
