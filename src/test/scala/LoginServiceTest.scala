import org.scalatest.FunSuite

import loginservice.LoginService._


class LoginServiceTest extends FunSuite {
  test("Normal Scenarios Check") {
    assertResult(Right(userList(0))) {
      login("foo", "pass")
    }
  }

  test("UserNotFound Check") {
    assertResult(Left(UserNotFound)) {
      login("baz", "pass")
    }
  }
  // InvalidPasswordの場合など他にもチェックするものはあるが省略
}
