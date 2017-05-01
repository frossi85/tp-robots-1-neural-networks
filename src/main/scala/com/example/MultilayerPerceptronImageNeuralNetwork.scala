package com.example

import java.io.{File => JFile}
import better.files._
import com.github.tototoshi.csv.CSVReader
import com.typesafe.scalalogging.LazyLogging
import org.neuroph.core.NeuralNetwork
import org.neuroph.core.data.{DataSet, DataSetRow}
import org.neuroph.core.events.{LearningEvent, LearningEventListener}
import org.neuroph.nnet.MultiLayerPerceptron
import org.neuroph.nnet.learning.BackPropagation
import scala.collection.JavaConverters._

class MultilayerPerceptronImageNeuralNetwork(
                                              networkFile: String,
                                              pixelsInImage: Int,
                                              numberOfImagesToIdentify: Int
                                            ) extends LearningEventListener with LazyLogging {//extends GreyscaleImageNeuralNetwork[MultiLayerPerceptron](networkFile, pixelsInImage, numberOfImagesToIdentify) {

  val outputNeurons = numberOfImagesToIdentify
  val inputNeurons = pixelsInImage
  val maximumPixelValue = 255.0
  protected var neuralNetwork: Option[MultiLayerPerceptron] = None

  private val neuronsInHiddenLayers = inputNeurons / 2 * outputNeurons

  if(File(networkFile).isEmpty) {
    logger.info("MultiLayer Perceptron will be created")
    val net = new MultiLayerPerceptron(inputNeurons, neuronsInHiddenLayers, outputNeurons)
    net.save(networkFile)
    logger.info("MultiLayer Perceptron network was created")
  } else {
    logger.info("MultiLayer Perceptron already exists and will be loaded")
  }


  def pixelNormalization(pixelValue: Double): Double =
    pixelValue / maximumPixelValue

  def train(trainCSVFile: String, trainedNetworkFile: String): Unit = {
    logger.info("Training started")

    val trainingDataSet = getDataSet(trainCSVFile)

    neuralNetwork = Some(NeuralNetwork.createFromFile(networkFile).asInstanceOf[MultiLayerPerceptron])
    neuralNetwork.get.learn(trainingDataSet)
    neuralNetwork.get.save(trainedNetworkFile)

    logger.info("Training finished")
  }

  def test(trainCSVFile: String, testCSVFile: String, networkFile: String): List[NetworkTestResult] = {
    logger.info("Test started")

    val testDataSet = getDataSet(testCSVFile)
    val rows = testDataSet.getRows.asScala

    neuralNetwork = Some(NeuralNetwork.createFromFile(networkFile).asInstanceOf[MultiLayerPerceptron])

    val result = rows map { row =>
      neuralNetwork.get.setInput(row.getInput: _*)
      neuralNetwork.get.calculate()

      NetworkTestResult(
        desiredOutput = getOutputNumber(row.getDesiredOutput().toList),
        actualOutput = getOutputNumber(neuralNetwork.get.getOutput().toList)
      )
    }

    logger.info("Test finished")

    result.toList
  }

  def generateOutputVector(number: Int): List[Double] = {
    (0 to 9).map {
      case n if n == number => 1.0
      case _ => 0.0
    }.toList
  }

  def getDataSet(inputCSVFile: String): DataSet = {
    val reader = CSVReader.open(new JFile(inputCSVFile))
    val dataSet =  new DataSet(inputNeurons, outputNeurons)

    val dataSetRows = reader.toStream.filter(row => row(0) != "label") map { row =>
      val number = row.head.toInt
      val pixels = row.tail
      val output = generateOutputVector(number)

      new DataSetRow(
        pixels.map(value => pixelNormalization(value.toDouble)).toArray,
        output.toArray
      )
    }

    dataSetRows.foreach(row => dataSet.addRow(row))

    reader.close()
    dataSet
  }

  def getOutputNumber(values: List[Double]): Int = {
    values.indexOf(values.max)
  }

  override def handleLearningEvent(event: LearningEvent): Unit = {
    val bp = event.getSource().asInstanceOf[BackPropagation]

    if (event.getEventType() != LearningEvent.Type.LEARNING_STOPPED){
      logger.info(bp.getCurrentIteration() + ". iteration : "+ bp.getTotalNetworkError())
    }
    if(bp.getCurrentIteration()>= 100){
      logger.info("#### Stopping learning stage")
      neuralNetwork.map(n => n.stopLearning())
    }
  }
}