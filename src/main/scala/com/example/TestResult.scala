package com.example

case class TestResult(stepResults: List[TestStepResult]) {
  val successRate = (stepResults.count(x => x.isMatch).toDouble / stepResults.size.toDouble) * 100
}
