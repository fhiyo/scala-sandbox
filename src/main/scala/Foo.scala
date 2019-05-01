package foo

//case class Foo(val x: Int, val y: Int) {
//  override def toString: String = "(" + x.toString + ", " + y.toString + ")"
//}


// XXX: objectかclassにくるまないとメソッドの定義はできない
object Foo {

  sealed abstract class DayOfWeek
  case object Sunday extends DayOfWeek
  case object Monday extends DayOfWeek
  case object Tuesday extends DayOfWeek
  case object Wednesday extends DayOfWeek
  case object Thursday extends DayOfWeek
  case object Friday extends DayOfWeek
  case object Saturday extends DayOfWeek

  sealed abstract class Exp
  case class Add(lhs: Exp, rhs: Exp) extends Exp
  case class Sub(lhs: Exp, rhs: Exp) extends Exp
  case class Mul(lhs: Exp, rhs: Exp) extends Exp
  case class Div(lhs: Exp, rhs: Exp) extends Exp
  case class Lit(value: Int) extends Exp

  def eval(exp: Exp): Int = {
    exp match {
      case Add(l, r) => eval(l) + eval(r)
      case Sub(l, r) => eval(l) - eval(r)
      case Mul(l, r) => eval(l) * eval(r)
      case Div(l, r) => eval(l) / eval(r)
      case Lit(v) => v
    }
  }

}

