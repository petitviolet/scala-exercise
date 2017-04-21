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

  /**
   * apply function to each elements
   */
  def mapTuple[A, B](tuple: (A, A, A), f: => A => B): (B, B, B)

  /**
   * sum size of collections
   */
  def sumSize[T](collections: Iterable[Iterable[T]]): Int

  /**
   * enable for-expression on MyFor[T]
   */
  //  trait MyFor[T]
}

