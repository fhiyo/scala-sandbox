package num

// ↓Additiveな属性を継承するべきかと思ったけど、違うかも。
// StringはAdditiveだけど、Numではない。
// 一方Numである型は全てAdditive。確かに
// https://dwango.github.io/scala_text/introduction-to-typeclass.html
// の言ってること正しいのかも。でもなんでだ？
// 一般化ってなんだ？？
//trait Num[A] extends Additive[A] {

trait Num[A] {
  def plus(a: A, b: A): A
  def minus(a: A, b: A): A
  def multiply(a: A, b: A): A
  def divide(a: A, b: A): A
  def zero: A
}

object Num {

  implicit object IntNum extends Num[Int] {
    def plus(a: Int, b: Int): Int = a + b
    def minus(a: Int, b: Int): Int = a - b
    def multiply(a: Int, b: Int): Int = a * b
    def divide(a: Int, b: Int): Int = a / b
    def zero: Int = 0
  }

  implicit object DoubleNum extends Num[Double] {
    def plus(a: Double, b: Double): Double = a + b
    def minus(a: Double, b: Double): Double = a - b
    def multiply(a: Double, b: Double): Double = a * b
    def divide(a: Double, b: Double): Double = a / b
    def zero: Double = 0.0
  }

}


trait FromInt[A] {
  def to(from: Int): A
}

object FromInt {
  implicit object FromIntToInt extends FromInt[Int] {
    def to(from: Int): Int = from
  }

  implicit object FromDoubleToInt extends FromInt[Double] {
    // NOTE: fromのままでも推論してくれるっぽいけど一応
    def to(from: Int): Double = from.toDouble
  }
}


object NumMethod {

  def average[A](l: List[A])(implicit num: Num[A], fromInt: FromInt[A]): A = {
    val sum: A = l.foldLeft(num.zero)((a, b) => num.plus(a, b))
    num.divide(sum, fromInt.to(l.length))
  }

  // implicitly version
  def averageByImplicitlyImpl[A:Num:FromInt](l: List[A]): A = {
    val a = implicitly[Num[A]]
    val b = implicitly[FromInt[A]]
    val sum: A = l.foldLeft(a.zero)((x, y) => a.plus(x, y))
    a.divide(sum, b.to(l.length))
  }

  // median methodを作る。
  // 要素数が奇数ならListの中央値を取ってくる、偶数なら間の平均を取る
  // 要素数が偶数のとき、どうやって割ったらいいかわからないからそれを先に定義した
  // 型を用意しておく。その世界の中にあるものだけを扱うようにする
  def median[A:Num:FromInt:Ordering](l: List[A]): A = {
    require(l.length > 0)

    val sorted = l.sorted
    val medIndex = sorted.length / 2
    medIndex % 2 match {
      case 1 => sorted(sorted.length / 2)
      case 0 => average(List[A](sorted(medIndex - 1), sorted(medIndex)))
    }
  }

}
