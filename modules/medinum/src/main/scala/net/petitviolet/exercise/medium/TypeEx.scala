package net.petitviolet.exercise.medium

trait TypeEx {
  /**
   * enable Money(a) + Money(b) returns Money(a + b)
   */
  final case class Money(value: Int)

  /**
   * enable Kilometer + Kilometer returns Kilometer
   * and
   * enable Mile + Mile returns Mile
   */
  //  trait Distance
  //  case class Kilometer(value: Int) extends Distance
  //  case class Mile(value: Int) extends Distance
}
