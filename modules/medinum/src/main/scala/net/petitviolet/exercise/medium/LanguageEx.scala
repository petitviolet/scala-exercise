package net.petitviolet.exercise.medium

/**
 * Exercise about language feature
 */
trait LanguageEx {
  abstract class WrapInt(val value: Int) {
    /**
     * sum values
     */
    def +:(other: WrapInt): WrapInt

    /**
     * subtract other from this
     */
    def -:(other: WrapInt): WrapInt
  }

  def map[T](seq: Seq[T], f: => T => T): Seq[T]
}

