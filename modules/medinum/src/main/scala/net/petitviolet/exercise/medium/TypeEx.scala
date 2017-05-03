package net.petitviolet.exercise.medium

trait TypeEx {
  /**
   * enable Money(a) + Money(b) returns Money(a + b)
   */
  final case class Money(value: Int)

  /**
   * - add abstract method to Distance trait
   * - enable Kilometer + Kilometer returns Kilometer
   * - enable Mile + Mile returns Mile
   */
  //  trait Distance
  //  case class Kilometer(value: Int) extends Distance
  //  case class Mile(value: Int) extends Distance

  sealed trait Password {
    val value: String
  }
  final case class Weak(value: String) extends Password
  final case class Strong(value: String) extends Password

  /**
   * trait `Validator`
   * `validate`: run validation on argument, returns Some[String] when invalid, None when valid
   * `&&`: combine other `Validator` instance with combining `validate` result using [[combineOptionString]]
   * {{{
   *  object EmptyValidator extends Validator[Password] { ??? }
   *  case class LengthValidator(length: Int) extends Validator[Password] { ??? }
   *  object AlphabetValidator extends Validator[Weak] { ??? }
   *  object SymbolValidator extends Validator[Strong] { ??? }
   *
   *  val weakValidator: Validator[Weak] = EmptyValidator && LengthValidator(4) && AlphabetValidator
   *  val strongValidator: Validator[Strong] = EmptyValidator && LengthValidator(8) && SymbolValidator
   * }}}
   */
  // just sample
  //  trait Validator[-A] { self =>
  //    def validate(target: A): Option[String]
  //
  //    final def &&[B <: A](other: Validator[B]): Validator[B] = new Validator[B] {
  //      override def validate(target: B): Option[String] = {
  //        self.validate(target) orElse other.validate(target)
  //      }
  //    }
  //  }
  final protected def combineOptionString(aOpt: Option[String], bOpt: Option[String]): Option[String] = {
    (aOpt, bOpt) match {
      case (None, None) => None
      case (a @ Some(_), None) => a
      case (None, b @ Some(_)) => b
      case (Some(a), Some(b)) => Some(s"$a & $b")
    }
  }

}
