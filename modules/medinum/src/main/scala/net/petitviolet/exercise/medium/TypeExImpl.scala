package net.petitviolet.exercise.medium

object TypeExImpl extends TypeEx {
  trait Addable[T] {
    def zero: T
    def add(t1: T, t2: T): T
  }
  implicit val moneyAddable = new Addable[Money] {
    override def zero: Money = Money(0)
    override def add(t1: Money, t2: Money): Money = Money(t1.value + t2.value)
  }
  implicit class AddableOps[T](val t: T) extends AnyVal {
    def +(other: T)(implicit A: Addable[T]): T = A.add(t, other)
  }

  trait Distance {
    type Self <: Distance
    def +(other: Self): Self
  }
  case class Kilometer(value: Int) extends Distance {
    type Self = Kilometer
    override def +(other: Kilometer): Kilometer = Kilometer(value + other.value)
  }
  case class Mile(value: Int) extends Distance {
    override type Self = Mile
    override def +(other: Mile): Mile = Mile(value + other.value)
  }
}
