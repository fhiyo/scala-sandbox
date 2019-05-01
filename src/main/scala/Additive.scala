package additive

// TODO
// average methodを実装する。averageはIntやDoubleなどの特定の型だけでなく、
// 汎用的に使えるようにしたい。
// average(List[Int](0, 1, 2, 3)) -> 1.5
// average(List[Double](0.0, 1.0, 2.0, 3.0)) -> 1.5
// (本当はListに独自メソッドを生やす形にしたいけどなぁ。。
// List[Int](0, 1).sum みたいな。
// ↑sumはあった。averageはないな。
// implicitのpimp my libraryの機能でできるんだろうけど。
// List[Int]に関数生やすのはできた。

// Additiveを継承してNumにするなら分かるけど、
// "Additiveをより一般化して、"というのは違うんじゃないか？
// 具体化してないか？


object Additive {

  // implicit classはpimp by library専用の構文
  implicit class RichIntList(val src: List[Int]) {
    def average: Int = src.sum / src.length
  }

  trait Additive[A] {
    def zero: A
    def plus(x: A, y: A): A
  }

  implicit object IntAdditive extends Additive[Int] {
    def zero = 0
    def plus(x: Int, y: Int) = x + y
  }

  implicit object StringAdditive extends Additive[String] {
    def zero = ""
    def plus(x: String, y: String) = x + y
  }

  case class Point(x: Double, y: Double)

  implicit object PointAdditive extends Additive[Point] {
    def zero = Point(0, 0)
    def plus(p1: Point, p2: Point) = Point(p1.x + p2.x, p1.y + p2.y)
  }

  case class Rational(num: Int, den: Int)

  // conpanion object
  object Rational {
    implicit object RationalAdditive extends Additive[Rational] {
      def zero = Rational(0, 0)
      def plus(a: Rational, b: Rational) = {
        if (a == zero) {
          b
        } else if (b == zero) {
          a
        } else {
          Rational(a.num * b.den + b.num * a.den, a.den * b.den)
        }
      }
    }
  }

}

object AdditiveMethod {

  def sum[A](l: List[A])(implicit m: Additive.Additive[A]): A = l.foldLeft(m.zero)((a, b) => m.plus(a, b))

  //implicit class RichList[A](val src: List[A]) {
  //  def sum2[B >: A](implicit m: Additive.Additive[B]): B = {
  //    src.foldLeft(m.zero)((a, b) => m.plus(a, b))
  //  }
  //}

}
