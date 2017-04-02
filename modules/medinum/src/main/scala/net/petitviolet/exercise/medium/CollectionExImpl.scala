package net.petitviolet.exercise.medium

object CollectionExImpl extends CollectionEx {
  // パターンマッチ
  def extractThirdOrSecond[T](target: Seq[T]): Option[T] = {
    target match {
      case _ +: _ +: third +: _ => Some(third)
      case _ +: second +: _ => Some(second)
      case _ => None
    }
  }

  import scala.collection.{ mutable => m }
  class OrderingMapImpl[K, V](map: m.LinkedHashMap[K, V] = m.LinkedHashMap.empty[K, V]) extends OrderingMap[K, V] {
    def put(key: K, value: V): OrderingMap[K, V] = new OrderingMapImpl(map -= key += (key -> value))
    def get(key: K): Option[V] = map.get(key)
    def removeOldest(): OrderingMap[K, V] = if (map.isEmpty) this else new OrderingMapImpl(map.tail)
    def headOption: Option[(K, V)] = map.headOption
    def lastOption: Option[(K, V)] = map.lastOption
  }

  def orderByIntAscStrLenDesc(targets: Seq[SortTarget]): Seq[SortTarget] =
    targets sortBy { t => (t.int, -t.str.length) }

  class PraisesImpl private[exercise] (values: List[Praise]) extends Praises(values) {
    def put(memberId: MemberId, praiseCount: PraiseCount): Praises = {
      val newValues = values span { _.memberId != memberId } match {
        case (others, exists +: rest) =>
          exists.copy(count = exists.count + praiseCount) +:
            (others ++ rest)
        case (others, Nil) =>
          Praise(memberId, praiseCount) +: others
      }
      new PraisesImpl(newValues)
    }
  }

  override def mergeMap(left: Map[Int, Int], right: Map[Int, Int]): Map[Int, Int] = {
    (left ++ right) map {
      case (key, value) if (left contains key) && (right contains key) =>
        key -> (value + left(key))
      case keyValue => keyValue
    }
  }
}
