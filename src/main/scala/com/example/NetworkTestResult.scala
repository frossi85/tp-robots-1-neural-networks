package com.example

case class NetworkTestResult(desiredOutput: Int, actualOutput: Int) {
  def isMatch: Boolean = desiredOutput == actualOutput
}
