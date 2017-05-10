package net.petitviolet.exercise.medium

/**
 * コレクションに関するもの
 */
trait CollectionEx {
  /**
   * 引数の`Seq[T]`の
   * - 要素数が3以上なら3番目の要素を取得する
   * - 要素数が2なら2番目
   * - 要素数が1以下ならNone
   */
  def extractThirdOrSecond[T](target: Seq[T]): Option[T]

  /**
   * `Map[K, V]`の要素に追加した順序を保持する
   * `OrderingMapImpl`というクラス名で実装する
   */
  abstract class OrderingMap[K, V]() {
    // 新しい(K -> V)を末尾に追加
    def put(key: K, value: V): OrderingMap[K, V]
    // keyを用いてvalueを取得
    def get(key: K): Option[V]
    // 最も古い要素(先頭)を削除する
    def removeOldest(): OrderingMap[K, V]
    // 先頭の要素を取得
    def headOption: Option[(K, V)]
    // 末尾の要素を取得
    def lastOption: Option[(K, V)]
  }


  /**
   * 引数の`Seq[SortTarget]`を`int`の昇順、`str`の長さの降順でsortする
   *
   * Seq(SortTarget(1, "ab"), SortTarget(2, "a"), SortTarget(2, "ab"))
   * => Seq(SortTarget(1, "ab"), SortTarget(2, "ab"), SortTarget(2, "a"))
   */
  def orderByIntAscStrLenDesc(targets: Seq[SortTarget]): Seq[SortTarget]
  final case class SortTarget(int: Int, str: String)

  /**
   * implement [[Praises.put]] with name `PraisesImpl`
   * [[Praises]]クラスの`put`を実装する
   * `PraisesImpl`というクラス名にすること
   */
  type MemberId = Int
  final case class PraiseCount(value: Int) {
    def +(other: PraiseCount): PraiseCount = copy(value + other.value)
  }
  final case class Praise(memberId: MemberId, count: PraiseCount)
  abstract class Praises private[exercise] (val values: List[Praise]) {
    /**
     * (MemberId -> PraiseCount)の新しい要素を追加する
     * すでに同じMemberIdについて値を持っているなら足し合わせる
     * なければ新しく追加する
     *
     * val p = PraisesImpl(List(Praise(1L, PraiseCount(1)), Praise(2L, PraiseCount(2))))
     * p.put(2, PraiseCount(20)).put(3, PraiseCount(30))
     * => PraisesImpl(List(Praise(1L, PraiseCount(1)), Praise(2L, PraiseCount(22)), Praise(3L, PraiseCount(30))))
     */
    def put(memberId: MemberId, praiseCount: PraiseCount): Praises
  }

  /**
   * `Map[Int, Int]`をマージする
   *
   * mergeMap(Map(1 -> 1, 2 -> 2), Map(2 -> 20, 3 -> 30))
   * => Map(1 -> 1, 2 -> 22, 3 -> 30)
   */
  def mergeMap(left: Map[Int, Int], right: Map[Int, Int]): Map[Int, Int]
}

