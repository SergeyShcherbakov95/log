package log.analysis

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by sergey on 12/07/16.
  */
object Main {
  var seconds = 2
  var maxTries = 2

  def main(args: Array[String]) {
    StreamingExamples.setStreamingLogLevels()

    val conf = new SparkConf().setAppName("ScanPorts").setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(1))

    val lines = ssc.socketTextStream("localhost", 9090, StorageLevel.MEMORY_AND_DISK)
    val logs = lines.window(Seconds(seconds), Seconds(seconds))
    val logsAfterFilter = logs.map(LogStatic.getFromLogLine(_)).filter(_ != null).filter(_.client != null)
    val warnPortsLogs = logsAfterFilter.map(log => (log.client, 1)).reduceByKey(_ + _).filter(_._2 >= 10)
    val warnEndPointLogs = logsAfterFilter.filter(log => log.endpoint.matches("(\\S+)\'(\\S+)") ||
                                                  log.endpoint.matches("(\\S+)<(\\S+)") ||
                                                  log.endpoint.matches("(\\S+)%3C(\\S+)") ||
                                                  log.endpoint.matches("(\\S+)>(\\S+)") ||
                                                  log.endpoint.matches("(\\S+)%(\\S+)") ||
                                                  log.endpoint.matches("(\\S+):(\\S+)"))

    warnPortsLogs.print()
    warnEndPointLogs.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
