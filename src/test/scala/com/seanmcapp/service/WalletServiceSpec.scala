package com.seanmcapp.service

import com.seanmcapp.repository.WalletRepoMock
import com.seanmcapp.repository.seanmcwallet.Wallet
import org.mockito.Mockito
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec

class WalletServiceSpec extends AsyncWordSpec with Matchers {

  private val secretKey = "team-secret"

  val amarthaServiceMock = Mockito.mock(classOf[AmarthaService])
  val walletService = new WalletService(WalletRepoMock, WalletRepoMock) {
    override val SECRET_KEY = secretKey
  }

  "dashboard should return correct data" in {
    val dashboardView = walletService.dashboard(secretKey)
    dashboardView.savingAccount.get("SGD") shouldBe Some("1,300")
    dashboardView.chart.expense("SGD") shouldBe Map(
      "Fashion" -> List(0),
      "Zakat" -> List(0),
      "Misc" -> List(0),
      "Travel" -> List(850),
      "Rent" -> List(700),
      "Funding" -> List(0),
      "Daily" -> List(745),
      "IT Stuff" -> List(0),
      "Wellness" -> List(0)
    )
  }

  "data should return correct data" in {
    val dataView = walletService.data(secretKey, None)
    dataView.sgdBalance shouldBe Balance("-295", "-295", "-295")
    dataView.idrBalance shouldBe Balance("0","0","0")
  }

  "getMonth should return month name respectively" in {
    val i = List(1,2,3,4,5,6,7,8,9,10,11,12)
    val expected = List("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
    i.map(walletService.getMonth) shouldBe expected
  }

  "parseInput should return correct wallet (done)" in {
    val date = 202101
    val fields = Map(
      "id" -> "1",
      "name" -> "Condo rent",
      "category" -> "Rent",
      "currency" -> "SGD",
      "amount" -> "1000",
      "done" -> "on",
      "account" -> "DBS"
    )

    val expected = Wallet(1, 202101, "Condo rent", "Rent", "SGD", 1000, true, "DBS")
    walletService.parseInput(date, fields) shouldBe expected
  }

  "parseInput should return correct wallet" in {
    val date = 202101
    val fields = Map(
      "id" -> "1",
      "name" -> "Condo rent",
      "category" -> "Rent",
      "currency" -> "SGD",
      "amount" -> "1000",
      "account" -> "DBS"
    )

    val expected = Wallet(1, 202101, "Condo rent", "Rent", "SGD", 1000, false, "DBS")
    walletService.parseInput(date, fields) shouldBe expected
  }

}
