object MainBefore {

  case class Address(id: Int, name: String, postalCode: Option[String])
  case class User(id: Int, name: String, addressId: Option[Int])

  val userDataBase: Map[Int, User] = Map (
    1 -> User(1, "太郎", Some(1)),
    2 -> User(2, "次郎", Some(2)),
    3 -> User(3, "プー太郎", None)
  )

  val addressDataBase: Map[Int, Address] = Map (
    1 -> Address(1, "渋谷", Some("150-0002")),
    2 -> Address(2, "国際宇宙ステーション", None)
  )


  sealed trait PostalCodeResult
  case class Success(postalCode: String) extends PostalCodeResult
  sealed trait Failure extends PostalCodeResult
  case object UserNotFound extends Failure
  case object UserNotHasAddress extends Failure
  case object AddressNotFound extends Failure
  case object AddressNotHasPostalCode extends Failure


  def getPostalCodeResult(userId: Int): PostalCodeResult = {
    // ref: http://kazzna.jp/slide/either_in_scala/#/step-33
    // > 例えばPlayで、処理が失敗するごとに404や503などを右側に詰めていけば、
    // > すべて成功した場合Left(Ok)で、
    // > その他はRight(失敗した時点のエラー)とできます。
    // > 最後にmergeを呼び出せば、Result型が返せます。
    // ↑これか。。MergableEither?
    (for {
      user <- findUser(userId)
      addressId <- findUserAddressId(user)
      address <- findAddress(addressId)
      postalCode <- findPostalCode(address)
    } yield Success(postalCode)).merge
  }

  def findUser(userId: Int): Either[Failure, User] = {
    userDataBase.get(userId).toRight(UserNotFound)
  }

  def findUserAddressId(user: User): Either[Failure, Int] = {
    user.addressId.toRight(UserNotHasAddress)
  }

  def findAddress(addressId: Int): Either[Failure, Address] = {
    addressDataBase.get(addressId).toRight(AddressNotFound)
  }

  def findPostalCode(address: Address): Either[Failure, String] = {
    address.postalCode.toRight(AddressNotHasPostalCode)
  }

}
