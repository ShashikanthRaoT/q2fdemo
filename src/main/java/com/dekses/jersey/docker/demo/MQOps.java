package com.dekses.jersey.docker.demo;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.MQQueue;
import java.util.Hashtable;
import com.ibm.mq.constants.*;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQException;
import java.util.*;
import java.time.*;

public class MQOps {
    private static int ID_LENGTH = 24;
    private static String replyTextBegin = "<!DOCTYPE html> " +
                                 "<html>" +
                                 "<head>" +
                                 "<meta charset=\"ISO-8859-1\">" +
                                 "<title>IBM MQ Managed File Transfer Demo</title>" +
                                 "<style>" +
    "table, th, td {" +
      "border: 1px solid black; " +
    "} " +
    "</style> " +
    "</head> " +
    "<body> " +
    "<table style=\"width:50%\">";
    private static String replyTextEnd = "  </table>" +
    "</body>" +
    "</html>";

    private static List<String> customerNames = new ArrayList<String>();
    static {
      customerNames.add("Alec Baldwin");
      customerNames.add("Mark Johnson");
      customerNames.add("Julious King");
      customerNames.add("Nicolas Bird");
      customerNames.add("Bill Ferguson");
      customerNames.add("Susan Bernard");
      customerNames.add("Chandrashekar S");
      customerNames.add("Naveen Gupta");
      customerNames.add("Amit Mulani");
      customerNames.add("Anushka Gowda");
      customerNames.add("Nivedita Shetty");
      customerNames.add("Nirmala S");
      customerNames.add("Alain Frost");
      customerNames.add("Oscar Dmello");
    }

    private static List<String> bankNames = new ArrayList<String>();
    static {
      bankNames.add("Bank ANZ");
      bankNames.add("Wachovia Bank");
      bankNames.add("Baco Do Brasil");
      bankNames.add("NedBank SA");
      bankNames.add("State Bank of India");
      bankNames.add("Banco Santander");
      bankNames.add("First Commercial");
      bankNames.add("HDFC Bank");
      bankNames.add("Bank of New York Mellon");
      bankNames.add("Ansett Australia");
      bankNames.add("Bank of England");
      bankNames.add("Bank of Seychells");
      bankNames.add("MFUJ Bank");
      bankNames.add("UBS");
    }
    private static List<String> customerOrg = new ArrayList<String>();
    static {
      customerOrg.add("Child Care Trust");
      customerOrg.add("Point Securities LLP");
      customerOrg.add("Adonis ImportExports");
      customerOrg.add("Chapline Industries");
      customerOrg.add("Indic Cosmetology Labs");
      customerOrg.add("FiSer Homes Inc");
      customerOrg.add("Marvel Market Makers");
      customerOrg.add("Bionic Searoutes Inc");
      customerOrg.add("Future Trades Ltd");
      customerOrg.add("Cosmic Lights Inc");
      customerOrg.add("Sandy Sugars Ltd");
      customerOrg.add("Mayur Partners LLP");
      customerOrg.add("Pomodoro Pizza Delights");
      customerOrg.add("StreetFood Movers Inc");
    }

    @SuppressWarnings("unchecked")
	  public static String postMessage() throws MQException {
		String replyMessage = "";

		MQQueueManager queueManager = null;
		MQQueue queue = null;
        Hashtable props = new Hashtable();
        props.put(MQConstants.CHANNEL_PROPERTY, "QS_SVRCONN");
        props.put(MQConstants.PORT_PROPERTY, 1414);
        props.put(MQConstants.HOST_NAME_PROPERTY, "10.254.12.203");

		try {
      queueManager = new MQQueueManager("QUICKSTART", props);
			int openOptions = MQConstants.MQOO_OUTPUT + MQConstants.MQOO_INPUT_AS_Q_DEF;
			queue = queueManager.accessQueue("SWIFTQ", openOptions);
			MQPutMessageOptions mqPutMessageOptions = new MQPutMessageOptions();
			MQMessage message = new MQMessage();
			message.format = MQConstants.MQFMT_STRING;
      String requestMessage = getSwiftMessage();
			message.writeString(requestMessage);;
			queue.put(message, mqPutMessageOptions);
			replyMessage = "TransactionID: " + toHexString(message.messageId) + getSwiftMessage();
		} catch (Exception e) {
			replyMessage = e.getMessage();
		} finally {
			if (queue != null)
				queue.close();
			if (queueManager != null)
				queueManager.disconnect();;
		}
		return replyMessage;
	}

  @SuppressWarnings("unchecked")
  public static String postMessage(Customer cust) throws MQException {
  String replyMessage = "";

  MQQueueManager queueManager = null;
  MQQueue queue = null;
      Hashtable props = new Hashtable();
      props.put(MQConstants.CHANNEL_PROPERTY, "QS_SVRCONN");
      props.put(MQConstants.PORT_PROPERTY, 1414);
      props.put(MQConstants.HOST_NAME_PROPERTY, "10.254.12.203");

  try {
    queueManager = new MQQueueManager("QUICKSTART", props);
    int openOptions = MQConstants.MQOO_OUTPUT + MQConstants.MQOO_INPUT_AS_Q_DEF;
    queue = queueManager.accessQueue("SWIFTQ", openOptions);
    MQPutMessageOptions mqPutMessageOptions = new MQPutMessageOptions();
    MQMessage message = new MQMessage();
    message.format = MQConstants.MQFMT_STRING;
    String curTime = Instant.now().toString();
    String requestMessage = cust.getJson(curTime);
    message.writeString(requestMessage);;
    queue.put(message, mqPutMessageOptions);
    replyMessage = getSwiftMessageHtml(toHexString(message.messageId), cust, curTime);
  } catch (Exception e) {
    replyMessage = e.getMessage();
  } finally {
    if (queue != null)
      queue.close();
    if (queueManager != null)
      queueManager.disconnect();;
  }
  return replyMessage;
}

    public static String toHexString(byte[] id)
    {
      StringBuffer sb = new StringBuffer ();
  
      String resultId = null;
      int size = id.length;
      if ( size == ID_LENGTH )
      {
        for (int i = 0; i < size; i++) {
          int v = (int) id[i] & 0xFF;
          sb.append ((v < 16) ? "0" : "");
          sb.append (Integer.toHexString(v));
        }
        resultId = sb.toString();
      }
    
      return resultId;
    }

    public static String getSwiftMessage() {
      StringBuffer sb = new StringBuffer();
      Random randomGenerator = new Random();
      int x = randomGenerator.nextInt(14); 
      String accountNumberBase = "10080092012";
      int accountPreffix = randomGenerator.nextInt(1000);
      sb.append("\nCustomer Detail:\n");
      sb.append("\n\tName: " + customerNames.get(x));
      sb.append("\n\tAccount Number: " + accountNumberBase + Integer.toString(accountPreffix));
      sb.append("\n\tBank Name: " + bankNames.get(x));
      sb.append("\n\tAmount in $: " + randomGenerator.nextInt(225000));
      sb.append("\n");

      return sb.toString();
    }

    public static String getSwiftMessageHtml(final String messageId, Customer cust, String curTime) {
      StringBuffer sb = new StringBuffer();
      sb.append(replyTextBegin);
      sb.append( "<tr><th>Transaction ID:</th><td>"+messageId + "</td></tr>");
      sb.append( "<tr><th>Customer Name:</th><td>"+ cust.getName() + "</td></tr>");
      sb.append( "<tr><th>Organization:</th><td>"+ cust.getOrganization() + "</td></tr>");
      sb.append( "<tr><th>Account Number:</th><td>"+ cust.getAccountNumber() + "</td></tr>");
      sb.append( "<tr><th>Bank:</th><td>"+ cust.getBankName() + "</td></tr>");
      sb.append( "<tr><th>Amount in $:</th><td>"+ cust.getAmount() + "</td></tr>");
      sb.append( "<tr><th>Time:</th><td>"+ curTime + "</td></tr>");
      sb.append(replyTextEnd);
      return sb.toString();
    }
  }

