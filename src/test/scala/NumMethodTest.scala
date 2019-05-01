import org.scalatest.FunSuite

import num.NumMethod._


class NumMethodTest extends FunSuite {
  test("Int average test") {
    assertResult(10) {
      average(List[Int](10, 10, 10))
    }
  }

  test("Int average test 2") {
    assertResult(3) {
      average(List[Int](1, 3, 5))
    }
  }

  test("Double average test") {
    assertResult(2.5) {
      average(List[Double](1.5, 2.5, 3.5))
    }
  }

  test("Empty Int List Test") {
    assertThrows[IllegalArgumentException] {
      median(List[Int]())
    }
  }

  test("Empty Double List Test") {
    assertThrows[IllegalArgumentException] {
      median(List[Double]())
    }
  }

  test("Int median test") {
    assertResult(10) {
      median(List[Int](10, 10, 10))
    }
  }

  test("Int median test 2") {
    assertResult(3) {
      median(List[Int](1, 3, 5))
    }
  }

  test("Int median test 3") {
    assertResult(4) {
      median(List[Int](1, 3, 5, 1000))
    }
  }

  test("Int median test 4") {
    assertResult(3) {
      median(List[Int](5, 3, 1))
    }
  }

  test("Int median test 5") {
    assertResult(4) {
      median(List[Int](3, 1, 1000, 5))
    }
  }

  test("Int median test 6") {
    assertResult(3) {
      median(List[Int](1, 4, 5, 3))
    }
  }

  test("Double median test") {
    assertResult(2.5) {
      median(List[Double](1.5, 2.5, 3.5))
    }
  }

}
