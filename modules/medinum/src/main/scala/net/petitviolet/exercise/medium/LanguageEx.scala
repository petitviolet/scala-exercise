package net.petitviolet.exercise.medium

/**
 * 言語機能に関するもの
 */
trait LanguageEx {
  /**
   * Intをラップしたクラスで加減算を実装する
   * `WrapIntImpl`というクラス名で実装すること
   * (new WrapIntImpl(1)) +: (new WrapIntImpl(2)) => WrapIntImpl(3)
   * (new WrapIntImpl(2)) -: (new WrapIntImpl(1)) => WrapIntImpl(1)
   */
  abstract class WrapInt(val value: Int) {
    /**
     * 加算
     */
    def +:(other: WrapInt): WrapInt

    /**
     * 減算
     */
    def -:(other: WrapInt): WrapInt
  }

  /**
   * タプルの各要素に関数を適用する
   * mapTuple[String, Int](("a", "ab", "abc"), { s => s.size }) => (1, 2, 3)
   */
  def mapTuple[A, B](tuple: (A, A, A), f: => A => B): (B, B, B)

  /**
   * 引数のIterable内のIterableのsizeを足し合わせる
   * sumSize(List(List(1), List(1, 2)), List(List(1, 2, 3))) => 6
   */
  def sumSize[T](collections: Iterable[Iterable[T]]): Int

  /**
   * `MyFor`型にfor式を実装する
   *
   * val success: MyFor[Int] = {
   *  a <- MyForImpl(1)
   *  b <- MyForImpl(2)
   * } yield a + b  => MyForImpl(3)
   *
   * val fail: MyFor[Int] = {
   *  a <- MyForImpl(1)
   *  b <- MyForImpl(2) if b != 2
   * } yield a + b  => MyForFail
   */
  //  trait MyFor[T]
}

