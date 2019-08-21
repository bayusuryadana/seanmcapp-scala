package com.seanmcapp.util.parser

import spray.json._

import scala.util.{Failure, Success, Try}

trait Decoder {
  def decode[T](jsonString: String)(implicit fmt: JsonReader[T]): T = {
    Try(jsonString.parseJson.convertTo[T]) match {
      case Success(res) => res
      case Failure(e) => throw new Exception(e)
    }
  }
}