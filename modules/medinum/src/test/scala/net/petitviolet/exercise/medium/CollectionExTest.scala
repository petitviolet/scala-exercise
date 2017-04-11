package net.petitviolet.exercise.medium

import scala.collection.immutable

class CollectionExTest extends TestBase {
  import CollectionExImpl._
  "extractThirdOrSecond" should "return the third element" in {
    extractThirdOrSecond(1 :: 2 :: 3 :: 4 :: Nil) shouldBe Some(3)
    extractThirdOrSecond(1 :: 2 :: 3 :: Nil) shouldBe Some(3)
    extractThirdOrSecond(1 :: 2 :: Nil) shouldBe Some(2)
    extractThirdOrSecond(1 :: Nil) shouldBe empty

    extractThirdOrSecond(Vector(1, 2, 3, 4, 5)) shouldBe Some(3)
    extractThirdOrSecond(Vector("hoge", "foo")) shouldBe Some("foo")

    extractThirdOrSecond(immutable.LinearSeq(1, 2)) shouldBe Some(2)
    extractThirdOrSecond(Nil) shouldBe empty
    extractThirdOrSecond(Vector.empty) shouldBe empty
    extractThirdOrSecond(Seq.empty) shouldBe empty
  }

  "OrderingMap" should "hold element with its order" in {
    val map = new OrderingMapImpl[Int, String]()
      .put(1, "hoge")
      .put(2, "foo")
      .put(3, "bar")
      .put(1, "baz")

    map.get(2) shouldBe Some("foo")
    map.get(999) shouldBe empty
    map.headOption shouldBe Some((2, "foo"))
    map.lastOption shouldBe Some((1, "baz"))
    map.removeOldest().headOption shouldBe Some((3, "bar"))
  }

  "orderByIntAscStrLenDesc" should "sort with 'int' asc and length of 'str' desc" in {
    val targets = orderByIntAscStrLenDesc {
      SortTarget(4, "abc") ::
        SortTarget(4, "abc") ::
        SortTarget(2, "ab") ::
        SortTarget(3, "ab") ::
        SortTarget(1, "a") ::
        SortTarget(1, "abcd") ::
        SortTarget(3, "a") ::
        SortTarget(3, "abc") ::
        Nil
    }

    targets shouldBe {
      SortTarget(1, "abcd") ::
        SortTarget(1, "a") ::
        SortTarget(2, "ab") ::
        SortTarget(3, "abc") ::
        SortTarget(3, "ab") ::
        SortTarget(3, "a") ::
        SortTarget(4, "abc") ::
        SortTarget(4, "abc") ::
        Nil
    }
  }

  "putting pair into Praises" should "compose PraiseCount" in {
    val praises = new PraisesImpl(Nil)
      .put(1, PraiseCount(1))
      .put(2, PraiseCount(2))
      .put(3, PraiseCount(3))
      .put(2, PraiseCount(20))
      .put(1, PraiseCount(10))
      .put(4, PraiseCount(4))

    praises.values.toSet shouldBe {
      (Praise(1, PraiseCount(11)) ::
        Praise(2, PraiseCount(22)) ::
        Praise(3, PraiseCount(3)) ::
        Praise(4, PraiseCount(4)) ::
        Nil).toSet
    }
  }

  "mergeMap" should "sum up values" in {
    val map1: Map[Int, Int] = Map(1 -> 1, 2 -> 2, 3 -> 3)
    val map2: Map[Int, Int] = Map(2 -> 20, 3 -> 30, 4 -> 40)
    mergeMap(map1, map2) shouldBe Map(1 -> 1, 2 -> 22, 3 -> 33, 4 -> 40)
  }
}
