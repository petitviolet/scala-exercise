package net.petitviolet.exercise.medium

class TypeExTest extends TestBase {
  import TypeExImpl._

  "Money" should "addable" in {
    Money(10) + Money(20) shouldBe Money(30)
  }

  "Distance" should "can + operator" in {
    Kilometer(10) + Kilometer(20) shouldBe Kilometer(30)
    Mile(20) + Mile(30) shouldBe Mile(50)
  }
}
