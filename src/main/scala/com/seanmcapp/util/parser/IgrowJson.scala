package com.seanmcapp.util.parser

import spray.json.DefaultJsonProtocol

case class IgrowResponse(data: Seq[IgrowData])

case class IgrowData(name: String, price: Long, stock: Int, returnAmount: String, contractPeriod: Int)

object IgrowJson extends DefaultJsonProtocol {

  implicit val IgrowDataFormat = jsonFormat(IgrowData, "name", "price", "stock",
    "return", "contract_period")

  implicit val IgrowResponseFormat = jsonFormat(IgrowResponse, "data")

}