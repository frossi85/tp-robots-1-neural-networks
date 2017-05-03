package com.example

import better.files._
import org.neuroph.nnet.MultiLayerPerceptron

class MultilayerPerceptronImageNeuralNetwork(
                                              networkFile: String,
                                              pixelsInImage: Int,
                                              numberOfImagesToIdentify: Int
                                            ) extends GreyscaleImageNeuralNetwork[MultiLayerPerceptron](networkFile, pixelsInImage, numberOfImagesToIdentify) {

  private val neuronsInHiddenLayers = (inputNeurons / (1.4 * outputNeurons)).round.toInt

  if(File(networkFile).isEmpty) {
    logger.info("MultiLayer Perceptron will be created")
    val net = new MultiLayerPerceptron(
      inputNeurons,
      neuronsInHiddenLayers,
      neuronsInHiddenLayers,
      neuronsInHiddenLayers,
      outputNeurons
    )

    net.save(networkFile)
    logger.info("MultiLayer Perceptron network was created")
  } else {
    logger.info("MultiLayer Perceptron already exists and will be loaded")
  }
}