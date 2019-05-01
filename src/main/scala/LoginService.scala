package loginservice

object LoginService {
  sealed trait LoginError
  case object UserNotFound extends LoginError
  case object InvalidPassword extends LoginError
  case object PasswordLocked extends LoginError

  case class User(val id: Long,
                  val name: String,
                  val password: String,
                  val locked: Boolean)

  val userList: List[User] = List(User(1, "foo", "pass", false), User(2, "bar", "word", false))

  def login(name: String, password: String): Either[LoginError, User] = {
    // find内の処理でpasswordの一致やら何やらを見たいけど省略
    val matched: Option[User] = userList.find(u => u.name == name && u.password == password)
    matched match {
      case Some(u: User) => Right(u)
      case None => Left(UserNotFound)
    }
  }

}
