package net.petitviolet.exercise.medium

class TypeExTest extends TestBase {
  import TypeExImpl._

  "Money" should "addable" in {
    Money(10) + Money(20) shouldBe Money(30)
  }

  "Distance" should "can + operator" in {
    Kilometer(10) + Kilometer(20) shouldBe Kilometer(30)
    Mile(20) + Mile(30) shouldBe Mile(50)

    Kilometer2(10) + Kilometer2(20) shouldBe Kilometer2(30)
    Mile2(20) + Mile2(30) shouldBe Mile2(50)
  }

  "Validator" should "" in {
    val weakValidator: Validator[Weak] = EmptyValidator && LengthValidator(4) && AlphabetValidator

    weakValidator.validate(Weak("hoge")) shouldBe empty
    weakValidator.validate(Weak("1234")) should not be empty

    val strongValidator: Validator[Strong] = SymbolValidator && EmptyValidator && LengthValidator(8)
    strongValidator.validate(Strong("hoge!!!!")) shouldBe empty
    strongValidator.validate(Strong("hoge")) should not be empty
    strongValidator.validate(Strong("hogefuga")) should not be empty
  }
}
