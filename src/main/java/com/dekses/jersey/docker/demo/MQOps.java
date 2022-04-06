package com.dekses.jersey.docker.demo;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.MQQueue;
import java.util.Hashtable;
import com.ibm.mq.constants.*;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQException;

public class MQOps {
    private static int ID_LENGTH = 24;

    @SuppressWarnings("unchecked")
	public static String postMessage() throws MQException {
		String replyMessage = "";

		MQQueueManager queueManager = null;
		MQQueue queue = null;
        Hashtable props = new Hashtable();
        props.put(MQConstants.CHANNEL_PROPERTY, "DEV.APP.SVRCONN");
        props.put(MQConstants.PORT_PROPERTY, 1414);
        props.put(MQConstants.HOST_NAME_PROPERTY, "10.41.4.16");
        props.put(MQConstants.USER_ID_PROPERTY, "app");
        props.put(MQConstants.PASSWORD_PROPERTY, "passw0rd");

		try {
            queueManager = new MQQueueManager("MQMFT", props);
			int openOptions = MQConstants.MQOO_OUTPUT + MQConstants.MQOO_INPUT_AS_Q_DEF;
			queue = queueManager.accessQueue("SWIFTQ", openOptions);
			MQPutMessageOptions mqPutMessageOptions = new MQPutMessageOptions();
			MQMessage message = new MQMessage();
			message.format = MQConstants.MQFMT_STRING;
			message.writeString("This is a test message");;
			queue.put(message, mqPutMessageOptions);
			replyMessage = toHexString(message.messageId);
		} catch (Exception e) {
			e.printStackTrace();
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
}
