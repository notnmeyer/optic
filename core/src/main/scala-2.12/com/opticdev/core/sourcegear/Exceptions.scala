package com.opticdev.core.sourcegear

case class InvalidProjectFileException(error: String) extends Exception {
  override def getMessage: String = error
}
