//package foo

// 同じpackage内ならimportしなくても使える？
// ↑mainを定義するobjectをどっかの名前空間に入れるのはOK?
//import foo.{Foo,Exp,Add,Sub,Mul,Div,Lit}
//import loginservice.LoginService
//import scala.util.Try
import taps.Taps._

object HelloWorld {

  def main(args: Array[String]): Unit = {

    //def loopFrom9To9(): Unit = {
    //  var i = 0
    //  do {
    //    println(i)
    //    i = i + 1
    //  } while(i < 10)
    //}
    //loopFrom9To9()

    //val a = for (e <- List("a", "b", "c", "d", "e")) yield {
    //  "Pre-" + e
    //}

    //val range = 1 to 1000;
    //for (a <- range; b <- range; c <- range) {
    //  if (a * a + b * b == c * c) {
    //    println(a, b, c);
    //  }
    //}

    //val lst = List("A", "B", "C");
    //lst match {
    //  case List("A", b, c) if b != "B" =>
    //    println("b = " + b)
    //    println("c = " + c)
    //  case _ =>
    //    println("nothing")
    //}

    //val lst = List(List("A"), List("B", "C"));
    //lst match {
    //  case List(a@List("A"), x) =>
    //    println(a)
    //    println(x)
    //  case _ =>
    //    println("nothing")
    //}

    //for (i <- 1 to 1000) {
    //  val s = new scala.util.Random(new java.security.SecureRandom()).alphanumeric.take(5).toList match {
    //    case List(a, b, c, d, _) => List(a, b, c, d, a).mkString
    //  }
    //  println(s)
    //}

    //val p = new Point(3, 2)
    //println(p)
    //println(p.toString())
    //println(p + new Point(10, 20))

    //val adder = new Adder()
    ////println(adder.add(2)(3))
    //val fun: Int => Int = adder.add(2, _)
    //println(fun(10))

    //new APrinter().print()
    //new BPrinter().print

    //val p = new Point3D(10, 20, 30)
    //println(p.x)
    //println(p.y)
    //println(p.z)

    //// objectによるfactory methodのおかげでnew keywordが無くてもオブジェクトを生成できる
    //val p = Point(3, 5)
    //println(p)
    //println(p.equals(Point(3, 5)))

    //val p = Person("Taro", 20, 70)
    //Person.printWeight()

    //val a = new ClassA
    //a.greet()

    //val cell = new Cell[Int](10)
    //println(cell.get())
    //cell.put(50)
    //println(cell.get())

    //val x: DayOfWeek = Sunday
    //println(x)
    //val y: DayOfWeek = Wednesday
    //println(y)

    //val func: AnyVal => Boolean
    //val extendedFunc1: Int => Boolean = i => i == 1
    ////print(extendedFunc1(3))
    //val func: AnyVal => Boolean = extendedFunc1
    //print(func(3))
    //val test = (i: Int) => i == 1
    //def test(i: Int): Boolean = { i == 1 }
    //def func(i: Int): Boolean extends test = { i == 2 }
    //val func: Int => AnyRef = test  // 今までの議論なら通るはず
    //print(test(3))

    //val exp: Exp = Add(Lit(1), Div(Mul(Lit(2), Lit(3)), Lit(2)))
    //println(Foo.eval(exp))

    //println(List(1, 2, 3, 4, 5).collect { case i if i % 2 == 1 => i * 2})

    //val o1: Option[String] = Option("hoge")
    //println(o1.isEmpty)
    //println(o1.isDefined)
    //println(o1.get)
    //val o2: Option[String] = Option(null)
    //println(o2.isEmpty)
    //println(o2.isDefined)
    //println(o2.getOrElse("ぬるぽ"))
    ////println(o2.getOrElse(throw new RuntimeException("ぬるぽ")))
    ////println(o2.get)
    //val o3: Option[String] = Some("hoge")
    //val result = o3 match {
    //  case Some(s: String) => s
    //  case None => "ぬる"
    //}
    //println(result)
    //println(Some(3).map(_ * 3))
    //val o: Option[Int] = Some(3)
    //println(o.fold(throw new RuntimeException)(_ * 3))
    //val n: Option[Int] = None
    ////println(n.map(_ * 3))
    //println(n.fold(throw new RuntimeException)(_ * 3))

    // 上手くできなかった。。。
    //def recursiveFlatten[B](implicit ev: Option[Int] <:< Option[B]): Option[Int] = o match {
    //  case None => throw new RuntimeException
    //  case Some(i: Int) => ev
    //  case _ => recursiveFlatten(ev.flatten)
    //}
    //println(recursiveFlatten(Some(2).map(i1 => Some(3).map(i2 => Some(5).map(i3 => Some(7).map(i4 => Some(11).map(i5 => i1 * i2 * i3 * i4 * i5)))))))
    //println(Some(2).flatMap(i1 => Some(3).flatMap(i2 => Some(5).flatMap(i3 => Some(7).flatMap(i4 => Some(11).map(i5 => i1 * i2 * i3 * i4 * i5))))))

    //for { i1 <- Some(2); i2 <- Some(3); i3 <- Some(5); i4 <- Some(7); i5 <- Some(11) }
    //  yield println(i1 * i2 * i3 * i4 * i5)

    //val v1: Either[String, Int] = Left("foo")
    ////val v2: Either[String, Int] = Right(123)
    //v1 match {
    //  // ref: https://dwango.github.io/scala_text/error-handling.html
    //  // 一般的にEitherを使う場合、Left値をエラー値、Right値を正常な値とみなすことが多いです。
    //  // 英語の"right"が正しいという意味なので、それにかけているという説があります      case Right(i) => println(i)
    //  case Right(i) => println(i)
    //  case Left(s) => println(s)
    //}

    //LoginService.login(name = "dwango", password = "password") match {
    //  case Right(user) => println(s"id: ${user.id}")
    //  case Left(LoginService.UserNotFound) => println(s"This user is not Found!")
    //  case Left(LoginService.InvalidPassword) => println(s"Invalid Passoword")
    //  case Left(LoginService.PasswordLocked) => println(s"This user is in state of PasswordLocked.")
    //}

    //val v1: Either[String, Int] = Right(123)
    //println(v1.map(_ * 2))
    //val v2: Either[String, Int] = Left("foo")
    //println(v2.map(_ * 2))

    //val v0: Try[Int] = Try(throw new RuntimeException("to be caught"))
    //println(v0)
    //val v1: Try[Int] = Try(3)
    //println(v1)
    //val v2: Try[Int] = Try(5)
    //println(v2)
    //val v3: Try[Int] = Try(7)
    //println(v3)
    //for { i0 <- v0; i1 <- v1; i2 <- v2; i3 <- v3 }
    //  yield println(i0 * i1 * i2 * i3)

    //val h: DayOfWeek = Sunday
    //h match {
    //  case Sunday => println("holiday!")
    //  case Saturday => println("holiday!")
    //  case Monday => println("not holiday...!")
    //}

    //println("Hi, ".smile)
    //if (2.twoIsFalse) {
    //  println("2 is clearly true!")
    //} else {
    //  println("fa!?")
    //}

    "Hello, World".tap{s => println(s)}.reverse.tap{s => println(s)}

  }


  //implicit class RichString(val src: String) {
  //  def smile: String = src + ":-)"
  //}
  ////implicit def enrichString(arg: String): RichString = new RichString(arg)
  //implicit class intToBoolean(val src: Int) {
  //  def twoIsFalse(): Boolean = if (src == 2) false else true
  //}

  //sealed trait DayOfWeek
  //sealed trait Holiday extends DayOfWeek
  //sealed trait Weekday extends DayOfWeek
  //case object Sunday extends Holiday
  //case object Monday extends Weekday
  //// ...
  //case object Saturday extends Holiday


  //sealed abstract class DayOfWeek {
  //  override def toString: String = this.getClass.getName.split("\\$").last
  //}
  //object Sunday extends DayOfWeek
  //object Monday extends DayOfWeek
  //object Tuesday extends DayOfWeek
  //object Wednesday extends DayOfWeek
  //object Thursday extends DayOfWeek
  //object Friday extends DayOfWeek
  //object Saturday extends DayOfWeek

  //class Cell[A](var value: A) {
  //  def put(newValue: A): Unit = {
  //    value = newValue
  //  }
  //  def get(): A = value
  //}

  //trait TraitA {
  //  def greet(): Unit
  //}
  //trait TraitB extends TraitA {
  //  override def greet(): Unit = println("Good Morning!")
  //}
  //trait TraitC extends TraitA {
  //  override def greet(): Unit = println("Good Evening!")
  //}
  ////class ClassA extends TraitB with TraitC {
  //class ClassA extends TraitC with TraitB {
  //  //override def greet(): Unit = super[TraitB].greet()
  //}

  //trait TraitA {
  //  val name: String
  //  def printName(): Unit = println(name)
  //}
  //class ClassA(val name: String) extends TraitA
  //object ObjectA {
  //  val a = new ClassA("dwango")
  //  val b = new TraitA {
  //    override val name: String = "kadokawa"
  //  }
  //}

  //class Person(name: String, age: Int, private[this] val weight: Int)  // [this]つけるとダメ
  //class Person(name: String, age: Int, private val weight: Int)
  //object Person {
  //  def printWeight(): Unit = {
  //    val taro = new Person("Taro", 20, 70)
  //    println(taro.weight)
  //  }
  //}

  //case class Point(val x: Int, val y: Int)

  //class Point(val x: Int, val y: Int)
  //object Point {
  //  def apply(x: Int, y: Int): Point = new Point(x, y)
  //}

  //class Point3D(val x: Int, val y: Int, val z: Int)

  //class APrinter() {
  //  def print(): Unit = {
  //    println("A")
  //  }
  //}
  //
  //class BPrinter() extends APrinter {
  //  override def print(): Unit = {
  //    println("B")
  //  }
  //}

  //abstract class XY {
  //  def x: Int
  //  def y: Int
  //}

  //class Adder() {
  //  def add(x: Int, y: Int): Int = x + y
  //}

  //class Point(val x: Int, val y: Int) {
  //  def +(p: Point): Point = {
  //    new Point(x + p.x, y + p.y)
  //  }
  //  override def toString(): String = "(" + x + ", " + y + ")"
  //}

}
