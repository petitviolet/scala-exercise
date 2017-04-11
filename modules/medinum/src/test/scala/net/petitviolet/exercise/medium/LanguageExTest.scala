package net.petitviolet.exercise.medium

import org.scalatest.concurrent.TimeLimits
import org.scalatest.time.SpanSugar._
import org.scalatest.{ FlatSpec, GivenWhenThen, Matchers }

import scala.language.postfixOps

class LanguageExTest extends FlatSpec with Matchers with GivenWhenThen with TimeLimits {
  import LanguageExImpl._

  "WrapInt operator" should "work as normal operator" in {
    val left = new WrapIntImpl(10)
    val right = new WrapIntImpl(20)
    (left +: right).value shouldBe 30
    (left -: right).value shouldBe -10
  }

  "map" should "apply functions on each elements" in {
    map[Int](Nil, { i => i * 2 }) shouldBe empty
    TimeLimits.failAfter(10 millis)(map[Int](Nil, { Thread.sleep(50); i => { i * 2 } }))

    val seq = 1 to 3
    map[Int](seq, { i => i * 2 }) shouldBe Seq(2, 4, 6)
    TimeLimits.failAfter(60 millis) {
      map[Int](seq, { Thread.sleep(50); i => { i * 2 } }) shouldBe Seq(2, 4, 6)
    }
  }

}
