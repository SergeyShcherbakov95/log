package log.analysis


import org.apache.spark.Logging

import org.apache.log4j.{Level, Logger}

/**
  * Created by sergey on 18/07/16.
  */
object StreamingExamples extends Logging {
  def setStreamingLogLevels() {
    val log4jInitialized = Logger.getRootLogger.getAllAppenders.hasMoreElements
    if (!log4jInitialized) {
      logInfo("Setting log level to [WARN] for streaming example." +
        " To override add a custom log4j.properties to the classpath.")
      Logger.getRootLogger.setLevel(Level.WARN)
    }
  }
}
