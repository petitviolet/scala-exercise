package net.petitviolet.exercise.medium

/**
 * Exercise about language feature
 */
object LanguageExImpl extends LanguageEx {
  class WrapIntImpl(value: Int) extends WrapInt(value) {
    override def +:(other: WrapInt): WrapInt = new WrapIntImpl(value + other.value)
    override def -:(other: WrapInt): WrapInt = new WrapIntImpl(other.value - value)
  }

  /**
   * apply function to each elements
   */
  override def mapTuple[A, B](tuple: (A, A, A), f: => (A) => B): (B, B, B) = {
    val _f: A => B = f
    val (x, y, z) = tuple
    (_f(x), _f(y), _f(z))
  }

  /**
   * sum size of collections
   */
  override def sumSize[T](collections: Iterable[Iterable[T]]): Int = {
    collections.foldLeft(0) { (acc, c) => acc + c.size }
  }

  sealed trait MyFor[+T] {
    def map[U](f: T => U): MyFor[U]
    def flatMap[U](f: T => MyFor[U]): MyFor[U]
    def withFilter(f: T => Boolean): MyFor[T]
  }

  case class MyForImpl[T](value: T) extends MyFor[T] {
    override def map[U](f: (T) => U): MyFor[U] = MyForImpl(f(value))

    override def flatMap[U](f: (T) => MyFor[U]): MyFor[U] = f(value)

    override def withFilter(f: (T) => Boolean): MyFor[T] = if (f(value)) this else MyForFail
  }
  case object MyForFail extends MyFor[Nothing] {
    override def map[U](f: (Nothing) => U): MyFor[U] = MyForFail
    override def flatMap[U](f: (Nothing) => MyFor[U]): MyFor[U] = MyForFail
    override def withFilter(f: (Nothing) => Boolean): MyFor[Nothing] = MyForFail
  }

}

