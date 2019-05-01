import org.scalatest.FunSuite

import serializer.SerializerMethod._
import serializer.Serializer


class SerializerMethodTest extends FunSuite {

  test("IntSerializer Test") {
    assertResult("0") {
      string(0: Int)
    }
  }

  test("StringSerializer Test") {
    assertResult("foo") {
      string("foo")
    }
  }

  test("ListSerializer Test") {
    assertResult("[]") {
      string(List[Int]())
    }
  }

  test("ListSerializer Test 2") {
    assertResult("[1]") {
      string(List(1))
    }
  }

  test("ListSerializer Test 3") {
    assertResult("[10,1,5]") {
      string(List(10, 1, 5))
    }
  }

  test("NestedList's ListSerializer Test") {
    assertResult("[[10],[1],[5,32]]") {
      string(List(List(10), List(1), List(5, 32)))
    }
  }

  test("Try") {
    class MyClass(val x: Int)
    implicit object MyClassSerializer extends Serializer[MyClass] {
      override def serialize(src: MyClass): String = s"MyClass(${src.x})"
    }
    assertResult("MyClass(10)") {
      string(new MyClass(10))
    }
  }

}
