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

  // abstract type member
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

  // type parameter
  trait Distance2[T <: Distance2[_]] {
    def +(other: T): T
  }
  case class Kilometer2(value: Int) extends Distance2[Kilometer2] {
    override def +(other: Kilometer2): Kilometer2 = Kilometer2(value + other.value)
  }
  case class Mile2(value: Int) extends Distance2[Mile2] {
    override def +(other: Mile2): Mile2 = Mile2(value + other.value)
  }

  trait Validator[-A] { self =>
    def validate(target: A): Option[String]

    final def &&[B <: A](other: Validator[B]): Validator[B] = new Validator[B] {
      override def validate(target: B): Option[String] = {
        combineOptionString(self.validate(target), other.validate(target))
      }
    }
  }

  case object EmptyValidator extends Validator[Password] {
    override def validate(target: Password): Option[String] = {
      if (target.value.nonEmpty) None
      else Some("password must not empty")
    }
  }

  case class LengthValidator(length: Int) extends Validator[Password] {
    override def validate(target: Password): Option[String] = {
      if (target.value.length >= length) None
      else Some(s"password length must be >= $length")
    }
  }

  case object AlphabetValidator extends Validator[Weak] {
    private val p = ".*[a-z].*".r.pattern
    override def validate(target: TypeExImpl.Weak): Option[String] = {
      if (p.matcher(target.value).matches()) None
      else Some("password must contains alphabet")
    }
  }
  case object SymbolValidator extends Validator[Strong] {
    private val p = ".*[!*+~_].*".r.pattern
    override def validate(target: Strong): Option[String] = {
      if (p.matcher(target.value).matches()) None
      else Some("password must contains symbol")
    }
  }
}
