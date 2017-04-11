package net.petitviolet.exercise.medium

import org.scalatest.concurrent.TimeLimits
import org.scalatest.{ FlatSpec, GivenWhenThen, Matchers }

trait TestBase extends FlatSpec with Matchers with GivenWhenThen with TimeLimits {
  protected implicit val ec = scala.concurrent.ExecutionContext.Implicits.global
}
