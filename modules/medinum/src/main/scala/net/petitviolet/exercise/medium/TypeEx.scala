package net.petitviolet.exercise.medium

/**
 * 型に関するもの
 */
trait TypeEx {
  /**
   * Moneyクラスに加算を実装する
   * Money(a) + Money(b) => Money(a + b)
   */
  final case class Money(value: Int)

  /**
   * `Distance` traitに加算(`+`)をabstractメソッドとして定義する
   * `Kilometer`クラスと`Mile`クラスにそれぞれ実装する
   *
   * Kilometer(1) + Kilometer(2) => Kilometer(3)
   * Mile(1) + Mile(2) => Mile(3)
   */
  //  trait Distance
  //  case class Kilometer(value: Int) extends Distance
  //  case class Mile(value: Int) extends Distance

  /**
   * `Validator`を実装する
   * `Validator[T].validate(t)` => `Option[String]`でvalidならNone, invalidならSome(error message)
   * `Validator[T] && Validator[S]` => `Validator[S]`で合成できる
   *
   * [[Password]]を実装している[[Weak]], [[Strong]]に対して以下のような`Validator`を実装する
   * {{{
   *  // 空文字でないこと
   *  object EmptyValidator extends Validator[Password] { ??? }
   *  // 長さが`length`以上であること
   *  case class LengthValidator(length: Int) extends Validator[Password] { ??? }
   *  // アルファベットを含むこと
   *  object AlphabetValidator extends Validator[Weak] { ??? }
   *  // 記号を含むこと
   *  object SymbolValidator extends Validator[Strong] { ??? }
   *
   *  val weakV: Validator[Weak] = EmptyValidator && LengthValidator(4) && AlphabetValidator
   *  val strongV: Validator[Strong] = SymbolValidator && EmptyValidator && LengthValidator(8)
   * }}}
   *
   * [[combineOptionString]], [[hasSymbol]], [[hasAlphabet]]を使う
   */
  sealed trait Password {
    val value: String
  }
  final case class Weak(value: String) extends Password
  final case class Strong(value: String) extends Password

  final protected def combineOptionString(aOpt: Option[String], bOpt: Option[String]): Option[String] = {
    (aOpt, bOpt) match {
      case (None, None) => None
      case (a @ Some(_), None) => a
      case (None, b @ Some(_)) => b
      case (Some(a), Some(b)) => Some(s"$a & $b")
    }
  }
  final protected def hasAlphabet(str: String): Boolean = str.matches(".*[a-z].*")
  final protected def hasSymbol(str: String): Boolean = str.matches(".*[!*+~_].*")

}
