import org.scalatest.FunSuite

import additive.Additive._
import additive.AdditiveMethod.sum


class AdditiveTest extends FunSuite {

  test("IntAdditive zero") {
    assertResult(0) {
      sum(List[Int]())(IntAdditive)
    }
  }

  test("IntAdditive plus 1") {
    assertResult(0) {
      sum(List(0))(IntAdditive)
    }
  }

  test("IntAdditive plus 2") {
    assertResult(5) {
      sum(List(5))(IntAdditive)
    }
  }

  test("IntAdditive plus 3") {
    assertResult(-10) {
      sum(List(-3, -5, -2, 1, -1))(IntAdditive)
    }
  }

  test("StringAdditive zero") {
    assertResult("") {
      sum(List[String]())(StringAdditive)
    }
  }

  test("StringAdditive plus 1") {
    assertResult("") {
      sum(List[String](""))(StringAdditive)
    }
  }

  test("StringAdditive plus 2") {
    assertResult("foobar") {
      sum(List[String]("foo", "bar"))(StringAdditive)
    }
  }

  test("IntAdditive implicit test") {
    assertResult(-10) {
      sum(List(-3, -5, -2, 1, -1))
    }
  }

  test("StringAdditive implicit test") {
    assertResult("foobar") {
      sum(List[String]("foo", "bar"))
    }
  }

  test("Point zero") {
    assertResult(Point(0, 0)) {
      PointAdditive.zero
    }
  }

  test("Left Unit") {
    val point: Point = Point(10, 0)
    assertResult(point) {
      PointAdditive.plus(Point(0, 0), point)
    }
  }

  test("Right Unit") {
    val point: Point = Point(10, 0)
    assertResult(point) {
      PointAdditive.plus(point, Point(0, 0))
    }
  }

  test("Associativity") {
    val p1: Point = Point(10, 0)
    val p2: Point = Point(20, 0)
    val p3: Point = Point(30, 0)
    assertResult(PointAdditive.plus(p1, PointAdditive.plus(p2, p3))) {
      PointAdditive.plus(PointAdditive.plus(p1, p2), p3)
    }
  }

  test("Summation Test") {
    val pointList: List[Point] = List(Point(10, 0), Point(20, 0), Point(30, 0))
    assertResult(Point(60, 0)) {
      sum(pointList)
    }
  }

  test("Rational Test") {
    assertResult(Rational(4, 2)) {
      sum(List(Rational(1, 1), Rational(2, 2)))
    }
  }

  test("try") {
    assertResult(15) {
      List[Int](10, 20).average
    }
  }

  //test("try2") {
  //  assertResult(30) {
  //    List[Int](10, 20).sum2
  //  }
  //}

}
