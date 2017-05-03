package com.example

object Hello {
  def main(args: Array[String]): Unit = {
    val trainCSVFile = getClass.getResource("/train.csv").getPath
    val testCSVFile = getClass.getResource("/test.csv").getPath
    val somNetToTrainFile = "SOM.nnet"
    val multilayerPerceptronNetToTrainFile = "MLP.nnet"
    val trainedSomFile = "SOM_trained.nnet"
    val trainedMultilayerPerceptronFile = "MLP_trained.nnet"

    val train = true
    val test = true
    val multilayerPerceptron = true
    val som = false

    if(multilayerPerceptron) {
      val multilayerPerceptronNetwork = new MultilayerPerceptronImageNeuralNetwork(multilayerPerceptronNetToTrainFile, 784, 10)


      if(train) {
        val trainResult = multilayerPerceptronNetwork.train(trainCSVFile, trainedMultilayerPerceptronFile)
        println(s"Total error for multilayer perceptron is: ${trainResult.totalError.get}")
      }

      if(test) {
        val testResult = multilayerPerceptronNetwork.test(trainCSVFile, testCSVFile, trainedMultilayerPerceptronFile)
        println(s"Multilayer Perceptron success rate: ${testResult.successRate}%")
      }
    }

    if(som) {
      val somNetwork = new SomNeuralNetwork(somNetToTrainFile, 784, 10)

      if(train) {
        somNetwork.train(trainCSVFile, trainedSomFile)
      }

      if(test) {
        val testResult = somNetwork.test(trainCSVFile, testCSVFile, trainedSomFile)
        println(s"SOM success rate: ${testResult.successRate}%")
      }
    }
  }
}