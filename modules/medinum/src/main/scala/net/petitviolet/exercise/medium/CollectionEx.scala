package net.petitviolet.exercise.medium

trait CollectionEx {
  /**
   * if `target` is longer than 3 return the third element,
   * else if length is 2 return the second,
   * else return None
   */
  def extractThirdOrSecond[T](target: Seq[T]): Option[T]

  /**
   * `Map[K, V]` with elements ordering
   * implement each methods with name `OrderingMapImpl`
   */
  abstract class OrderingMap[K, V]() {
    def put(key: K, value: V): OrderingMap[K, V]
    def get(key: K): Option[V]
    def removeOldest(): OrderingMap[K, V]
    def headOption: Option[(K, V)]
    def lastOption: Option[(K, V)]
  }

  /**
   * sorting `SortTarget` with its value of `int` asc and length of `str` desc.
   * @param int
   * @param str
   */
  final case class SortTarget(int: Int, str: String)
  def orderByIntAscStrLenDesc(targets: Seq[SortTarget]): Seq[SortTarget]

  /**
   * implement [[Praises.put]] with name `PraisesImpl`
   */
  type MemberId = Int
  final case class PraiseCount(value: Int) {
    def +(other: PraiseCount): PraiseCount = copy(value + other.value)
  }
  final case class Praise(memberId: MemberId, count: PraiseCount)
  abstract class Praises private[exercise] (val values: List[Praise]) {
    /**
     * put a pair of (`memberId` -> `praiseCount`)
     * if `values` contains `memberId`, compose `praiseCount`s
     * else add them as a new pair.
     */
    def put(memberId: MemberId, praiseCount: PraiseCount): Praises
  }

  /**
   * merge `Map[Int, Int]` sum up values
   * @param left
   * @param right
   * @return
   */
  def mergeMap(left: Map[Int, Int], right: Map[Int, Int]): Map[Int, Int]
}

