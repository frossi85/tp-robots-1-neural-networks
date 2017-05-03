package com.example

case class TestStepResult(desiredOutput: Int, actualOutput: Int) {
  def isMatch: Boolean = desiredOutput == actualOutput
}



