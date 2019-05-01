import org.scalatest.FunSuite
import foo.Foo._


class ExpSuiteTest extends FunSuite {
  test("eval Lit(0) should be 0") {
    assert(eval(Lit(0)) === 0)
  }

  test("eval Add(Lit(1), Lit(2)) should be 3") {
    assert(eval(Add(Lit(1), Lit(2))) === 3)
  }

  test("eval Sub(Lit(1), Lit(2)) should be -1") {
    assert(eval(Sub(Lit(1), Lit(2))) === -1)
  }

  test("eval Mul(Lit(3), Lit(2)) should be 6") {
    assert(eval(Mul(Lit(3), Lit(2))) === 6)
  }

  test("eval Div(Lit(5), Lit(2)) should be 2") {
    // 5 = 2 * 2 + 1
    assert(eval(Div(Lit(5), Lit(2))) === 2)
  }
}
