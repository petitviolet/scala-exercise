package net.petitviolet.exercise.medium

import org.scalatest.concurrent.{ Futures, ScalaFutures, TimeLimits }
import org.scalatest.{ FlatSpec, GivenWhenThen, Matchers }
import org.scalatest.time._

trait TestBase extends FlatSpec with Matchers with GivenWhenThen with TimeLimits with ScalaFutures {
  protected implicit val ec = scala.concurrent.ExecutionContext.Implicits.global
  implicit override val patienceConfig = PatienceConfig(timeout = Span(2, Seconds), interval = Span(20, Millis))
}
