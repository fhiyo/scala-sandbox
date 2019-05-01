import org.scalatest.FunSuite
import binarytree.BinaryTree.{toString => treeToString, _}

// 一つのメソッドに対してテスト作ってくの面倒になってきたな。。。
// 全部一気に実行も今の所できないし。
// と思ったが、 (https://www.jetbrains.com/help/idea/run-debug-and-test-scala.html)
// でできた。configuration開いてTest KindをAll in PackageにすればOK.


class FromListTest extends FunSuite {
  test("Nil should generate Empty") {
    assertResult(Empty) {
      fromList(Nil)
    }
  }

  test("1") {
    assertResult(Branch(10, Empty, Branch(20, Empty, Empty))) {
      fromList(List(10, 20))
    }
  }
}


class TreeSortTest extends FunSuite {
  test("Empty Print") {
    //assert(treeToString(sort(Empty)) === "nil")
    // 下のようにも書ける (ref: http://www.scalatest.org/user_guide/using_assertions)
    assertResult("nil") {
      treeToString(sort(Empty))
    }
  }

  test("1") {
    //cancel("Not write the code yet.")
    assertResult("(1 nil nil)") {
      treeToString(sort(Branch(1, Empty, Empty)))
    }
  }

  test("2") {
    assertResult(List(-3, -2, -1, 1, 10)) {
      // そういえば木の構造まで保存してるわけではなかったからtreeToStringで比較できないわ
      //treeToString(sort(Branch(1, Branch(-1, Branch(10, Empty, Branch(-3, Empty, Branch(-2, Empty, Empty))), Empty), Empty)))
      toList(sort(Branch(1, Branch(-1, Branch(10, Empty, Branch(-3, Empty, Branch(-2, Empty, Empty))), Empty), Empty)))
    }
  }

}


class TreeTreeToStringTest extends FunSuite {
  test("nil print") {
    assert(treeToString(Empty) === "nil")
  }

  test("1") {
    assert(treeToString(Branch(1, Empty, Empty)) === "(1 nil nil)")
  }

  test("2") {
    assert(treeToString(Branch(2, Branch(1, Empty, Empty), Empty)) === "(2 (1 nil nil) nil)")
  }

  test("3") {
    assert(treeToString(Branch(2, Branch(1,
      Branch(100, Empty, Empty), Empty),
      Empty)) === "(2 (1 (100 nil nil) nil) nil)")
  }

  test("4") {
    assert(treeToString(Branch(2, Branch(1, Branch(10, Empty, Empty), Branch(20, Empty, Empty)), Empty)) === "(2 (1 (10 nil nil) (20 nil nil)) nil)")
  }

}


class TreeMaxSuiteTest extends FunSuite {
  test("Empty tree should be runtime exception") {
    assertThrows[RuntimeException] {
      max(Empty)
    }
  }

  test("Shallow Branch 1") {
    assert(max(Branch(1, Empty, Empty)) === 1)
  }

  test("Shallow Branch 2") {
    assert(max(Branch(1, Branch(2, Empty, Empty), Empty)) === 2)
  }

  test("Shallow Branch 3") {
    assert(max(Branch(1, Branch(3, Empty, Empty), Branch(2, Empty, Empty))) === 3)
  }

}


class TreeMinSuiteTest extends FunSuite {
  test("Empty tree should be runtime exception") {
    assertThrows[RuntimeException] {
      max(Empty)
    }
  }

  test("Shallow Branch 1") {
    assert(min(Branch(1, Empty, Empty)) === 1)
  }

  test("Shallow Branch 2") {
    assert(min(Branch(1, Branch(2, Empty, Empty), Empty)) === 1)
  }

  test("Shallow Branch 3") {
    assert(min(Branch(1, Branch(3, Empty, Empty), Branch(2, Empty, Empty))) === 1)
  }

}


class TreeDepthSuiteTest extends FunSuite {
  test("depth(Empty) should be 0") {
    assert(depth(Empty) === 0)
  }

  test("depth(Branch(10, Empty, Empty)) should be 1") {
    assert(depth(Branch(10, Empty, Empty)) === 1)
  }

  test("right empty") {
    assert(depth(Branch(10, Branch(20, Empty, Empty), Empty)) === 2)
  }

  test("left empty") {
    assert(depth(Branch(10, Empty, Branch(20, Empty, Empty))) === 2)
  }

  test("Both of side is not empty") {
    assert(depth(Branch(10, Branch(20, Empty, Empty), Branch(30, Empty, Empty))) === 2)
  }

  test("Deep Example 1") {
    assert(depth(Branch(10, Branch(20, Branch(40, Empty, Empty), Empty), Branch(30, Empty, Empty))) === 3)
  }

  test("Deep Example 2") {
    assert(depth(Branch(10, Branch(20, Empty, Empty), Branch(30, Empty, Branch(40, Empty, Branch(50, Empty, Empty))))) === 4)
  }
}

