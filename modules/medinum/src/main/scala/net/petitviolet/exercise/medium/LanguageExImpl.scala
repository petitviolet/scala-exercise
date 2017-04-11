package net.petitviolet.exercise.medium

/**
 * Exercise about language feature
 */
object LanguageExImpl extends LanguageEx {
  class WrapIntImpl(value: Int) extends WrapInt(value) {
    override def +:(other: WrapInt): WrapInt = new WrapIntImpl(value + other.value)
    override def -:(other: WrapInt): WrapInt = new WrapIntImpl(other.value - value)
  }
  override def map[T](seq: Seq[T], f: => (T) => T): Seq[T] = {
    if (seq.isEmpty) seq
    else {
      val _f: T => T = f
      seq map _f
    }
  }
}

