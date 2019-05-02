import org.scalatest._
import org.scalatest.concurrent.TimeLimits
import org.scalatest.mockito.MockitoSugar
import org.scalatest.time.SpanSugar._
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._


class CalcSpec extends FlatSpec with DiagrammedAssertions with TimeLimits with MockitoSugar {

  val calc = new Calc

  "sum function" should "get Int sequence, and return summation of them" in {
    assert(calc.sum(Seq(1, 2, 3)) === 6)
    assert(calc.sum(Seq(0)) === 0)
    assert(calc.sum(Seq(-1, 1)) === 0)
    assert(calc.sum(Seq()) === 0)
  }

  // ref: http://www.scalatest.org/user_guide/writing_your_first_test
  // If you have multiple tests about the same subject,
  // you can use 'it' to refer to the previous subject:
  it should "overflow when over Int's max value" in {
    assert(calc.sum(Seq(Integer.MAX_VALUE, 1)) === Integer.MIN_VALUE)
  }

  "div function" should "take 2 arguments, and return floating value of numerator divided by denominator" in {
    assert(calc.div(6, 3) === 2.0)
    assert(calc.div(1, 3) === 0.33333333333333333333)
  }

  it should "raise ArithmeticException error when trying to divide numerator by 0" in {
    intercept[ArithmeticException] {
      calc.div(1, 0)
    }
  }

  "isPrime function" should "return Boolean value of argument is whether prime or not" in {
    assert(!calc.isPrime(0))
    assert(!calc.isPrime(-1))
    assert(calc.isPrime(2))
    assert(calc.isPrime(17))
  }

  it should "do processing prime checking less than a num of 10 million in 1 second" in {
    failAfter(1000 millis) {
      calc.isPrime(99999991)
    }
  }

  "Mock object of Calc" should "camouflage behavior of Calc object" in {
    val mockCalc = mock[Calc]
    when(mockCalc.sum(Seq(3, 4, 5))).thenReturn(12)
    when(mockCalc.sum(Seq(4, 4, 5))).thenReturn(13)
    // mockに登録してない引数が渡されると、mockCalcは0を返した。この挙動は。。？
    assert(mockCalc.sum(Seq(4, 4, 5)) === 13)
  }

}
