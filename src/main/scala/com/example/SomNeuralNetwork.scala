package com.example

import better.files._
import org.neuroph.nnet.Kohonen

class SomNeuralNetwork(
                        networkFile: String,
                        pixelsInImage: Int,
                        numberOfImagesToIdentify: Int
                      ) extends GreyscaleImageNeuralNetwork[Kohonen](networkFile, pixelsInImage, numberOfImagesToIdentify) {

  if(File(networkFile).isEmpty) {
    logger.info("SOM will be created")
    new Kohonen(inputNeurons, outputNeurons).save(networkFile)
    logger.info("SOM was created")
  }
}