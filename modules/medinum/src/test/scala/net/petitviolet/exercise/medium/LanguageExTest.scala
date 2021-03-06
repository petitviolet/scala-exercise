package net.petitviolet.exercise.medium

import org.scalatest.concurrent.TimeLimits
import org.scalatest.time.SpanSugar._

import scala.language.postfixOps

class LanguageExTest extends TestBase {
  import LanguageExImpl._

  "WrapInt operator" should "work as normal operator" in {
    val left = new WrapIntImpl(10)
    val right = new WrapIntImpl(20)
    (left +: right).value shouldBe 30
    (left -: right).value shouldBe -10
  }

  "mapTuple" should "apply functions on each elements" in {
    mapTuple[String, Int](("hoge", "foo", "bo"), { _.length }) shouldBe (4, 3, 2)
    TimeLimits.failAfter(80 millis) {
      mapTuple[String, Int](("hoge", "foo", "bo"), { Thread.sleep(50); _.length }) shouldBe (4, 3, 2)
    }

  }

  "sumSize" should "return sum size of provided collections" in {
    sumSize(List(Set(1, 2), List(3, 4))) shouldBe 4
    sumSize(Set(List(1, 2), Set(3, 4))) shouldBe 4
  }

  "MyFor" should "can use for-expression" in {
    val r: MyFor[Int] = for {
      a <- MyForImpl(2)
      b <- MyForImpl(Seq(1, 2, 3)) if b.size >= 3
      c <- MyForImpl("4444")
    } yield a * b.sum * c.length
    r shouldBe a[MyForImpl[_]]
    r.value shouldBe 2 * 6 * 4

    val r2: MyFor[Int] = for {
      a <- MyForImpl(2) if a != 2
      b <- MyForImpl(Seq(1, 2, 3)) if b.size >= 3
      c <- MyForImpl("4444")
    } yield a * b.sum * c.length
    r2 shouldBe MyForFail
  }
}
