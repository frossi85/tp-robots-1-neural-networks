package com.example

object Hello {
  def main(args: Array[String]): Unit = {
    val trainCSVFile = getClass.getResource("/train.csv").getPath
    val testCSVFile = getClass.getResource("/test.csv").getPath
    val somNetToTrainFile = "./SOM.nnet"
    val multilayerPerceptronNetToTrainFile = "./MLP.nnet"
    val trainedSomFile = "./SOM_trained.nnet"
    val trainedMultilayerPerceptronFile = "./MPL_trained.nnet"

    val train = false
    val test = false
    val multilayerPerceptron = true
    val som = false

    if(multilayerPerceptron) {
      val multilayerPerceptronNetwork = new MultilayerPerceptronImageNeuralNetwork(multilayerPerceptronNetToTrainFile, 784, 10)


      if(train) {
        multilayerPerceptronNetwork.train(trainCSVFile, trainedMultilayerPerceptronFile)
      }

      /*

      if(test) {
        val testResult = multilayerPerceptronNetwork.test(trainCSVFile, testCSVFile, trainedMultilayerPerceptronFile)
        val successRate = (testResult.count(x => x.isMatch).toDouble / testResult.size.toDouble) * 100

        println(s"Multilayer Perceptron success rate: $successRate%")
      }
      */
    }

    /*
    if(som) {
      val somNetwork = new SomNeuralNetwork(somNetToTrainFile, 784, 10)

      if(train) {
        somNetwork.train(trainCSVFile, trainedSomFile)
      }

      if(test) {
        val testResult = somNetwork.test(trainCSVFile, testCSVFile, trainedSomFile)
        val successRate = (testResult.count(x => x.isMatch).toDouble / testResult.size.toDouble) * 100

        println(s"SOM success rate: $successRate%")
      }
    }
    */
  }
}