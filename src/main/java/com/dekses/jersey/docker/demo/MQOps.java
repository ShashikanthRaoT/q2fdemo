package com.dekses.jersey.docker.demo;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.MQQueue;
import java.util.Hashtable;
import com.ibm.mq.constants.*;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQException;
import java.util.*;

public class MQOps {
    private static int ID_LENGTH = 24;

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
			replyMessage = "Request ID: " +toHexString(message.messageId) + "\n" + requestMessage;
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
      sb.append("Customer:\n");
      sb.append("\n\tName: " + customerNames.get(x));
      sb.append("\n\tAccount Number: " + accountNumberBase + Integer.toString(accountPreffix));
      sb.append("\n\tBank Name: " + bankNames.get(x));
      sb.append("\n\tAmount in $: " + randomGenerator.nextInt(225000));

      return sb.toString();
    }
}
