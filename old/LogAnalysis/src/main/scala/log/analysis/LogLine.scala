package log.analysis

/**
  * Created by sergey on 12/07/16.
  */
@SerialVersionUID(114L)
class LogLine(ipX: String, clientX: String, userIdX: String,
              dateX: String, methodX: String, endpointX: String,
              protocolX: String, responseCodeX: Int, contentSizeX: Long) extends Serializable{
  var ip = ipX
  var client = clientX
  var userId = userIdX
  var date = dateX
  var method = methodX
  var endpoint = endpointX
  var protocol = protocolX
  var responseCode = responseCodeX
  var contentSize = contentSizeX

  override def toString = s"LogLine($ip, $client, $userId, $date, $method, $endpoint, $protocol, $responseCode, $contentSize)"
}

object LogStatic {
 // var pattern = "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+) \"[\\\\W\\s]+\" \"[\\\\W\\s]+\""

  def getFromLogLine(line: String): LogLine = {
   // if(line.matches(pattern)) {
      val array = line.split(" ")
      return new LogLine(array(0), array(1), array(2),
        array(3).substring(1) + array(4).substring(0, array(4).length-1),
        array(5).substring(1), array(6), array(7).substring(0, array(7).length-1), array(8).toInt, array(9).toLong)
    //}
    return null
  }
}
