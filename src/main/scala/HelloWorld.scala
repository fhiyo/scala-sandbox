import java.util
import java.util.{List => JList, ArrayList => JArrayList}
import java.util.{Comparator => JComparator}

import taps.Taps._
import java.util.HashSet
import java.util.ArrayList
import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer


object HelloWorld {

  def main(args: Array[String]): Unit = {
    "Hello, World".tap{s => println(s)}.reverse.tap{s => println(s)}

    // これは何をやっている？
    val hashSet = new HashSet[String]() { add("foo"); add("bar"); }
    println(hashSet)

    System.out.println("Hello, World!")

    println(System.currentTimeMillis())

    //println(System.exit(0))

    val map = new util.HashMap[String, Int]()
    map.put("A", 1)
    println(Option(map.get("A")))
    println(Option(map.get("B")))

    val list = new ArrayList[String]()
    list.add("A")
    val scalaList = list.asScala
    println(scalaList)

    val scalaArray = new ArrayBuffer[String]()
    scalaArray.insert(0, "foo")
    val javaArray = scalaArray.asJava
    println(javaArray)

    val objects: JList[_ <: Number] = new JArrayList[Integer]()
    println(objects)
    val cmp: JComparator[_ >: String] = new JComparator[Any] {
      override def compare(o1: Any, o2: Any): Int = {
        o1.hashCode() - o2.hashCode()
      }
    }

    println(cmp.compare("aa", "bb"))

    println(Seq(Some(1), Some(2), None, Some(4)) flatMap { x => x })
    println(List(1, 2, 4) mkString(","))

    println(Seq(1, 2, 3, 4) flatMap {x => if (x % 2 == 0) Some(x) else None})

    println(Seq(Seq(1, 2, 3), Seq(), Seq(5, 6)) flatMap {x => Some(x.size)})

    println(Seq(Seq(1, 2), Seq(3)) flatMap(identity))

  }

}
