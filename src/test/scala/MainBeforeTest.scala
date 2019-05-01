import org.scalatest.FunSuite

import MainBefore._


class MainBeforeTest extends FunSuite {
  test("Success") {
    assertResult(Success("150-0002")) {
      getPostalCodeResult(1)
    }
  }

  test("AddressNotHasPostalCode") {
    assertResult(AddressNotHasPostalCode) {
      getPostalCodeResult(2)
    }
  }

  test("UserNotHasAddress") {
    assertResult(UserNotHasAddress) {
      getPostalCodeResult(3)
    }
  }

  test("UserNotFound") {
    assertResult(UserNotFound) {
      getPostalCodeResult(4)
    }
  }

}
