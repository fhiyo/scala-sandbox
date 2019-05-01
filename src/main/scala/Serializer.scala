package serializer


trait Serializer[A] {
  def serialize(src: A): String
}

object Serializer {

  // NOTE: def string[A:Serializer] のように書くためにimplicit parameterを付ける
  implicit object IntSerializer extends Serializer[Int] {
    def serialize(src: Int): String = src.toString
  }

  implicit object StringSerializer extends Serializer[String] {
    def serialize(src: String): String = src
  }

  // ↓これだとダメなのはわかる。objectが型パラメータを取れないから
  // ref: https://stackoverflow.com/questions/2593487/scala-passing-type-parameters-to-object
  // > An object has to have a concrete type.
  // > The Scala object contruct is not a exception to this rule.
  //implicit object ListSerializer[A] extends Serializer[List[A]] {
  //  def serialize[A](src: List[A]): String = src.toString
  //}

  // defで定義したメソッドを{}で括って、中でメソッド定義するのにどんな意味があるかわからん。
  // あ、newしている。。。。！！！くそが。。。
  implicit def ListSerializer[A](implicit serializer: Serializer[A]): Serializer[List[A]] = new Serializer[List[A]] {
    override def serialize(src: List[A]): String = {
      val serializedList = src.map{o => serializer.serialize(o)}
      serializedList.mkString("[", ",", "]")
    }
  }
}

object SerializerMethod {
  def string[A:Serializer](a: A): String = implicitly[Serializer[A]].serialize(a)
}
