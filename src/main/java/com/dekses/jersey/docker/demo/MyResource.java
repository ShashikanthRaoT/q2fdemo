package com.dekses.jersey.docker.demo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class MyResource {
    
    private String welcomeText = "<!DOCTYPE html> " +
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
    "<table style=\"width:100%\">" +
    "<tr> " +
    "     <th><h1>IBM MQ Managed File Transfer</h1>" +
    "     </br> " +
    "      <h2>Message to File transfer Demo</h2>" +
    "     </br>" +
    "      <h3><a href=\"myapp/myresource\">Click Here to post a SWIFT message</a></h3>" +
    "   </th>" +
    "   </tr>" +
    "  </table>" +
    "</body>" +
    "</html>";
    
    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public String root() {
      return welcomeText;//getIndexPage();
    }


    private String getIndexPage() {
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append("<head>\n");
        sb.append("<meta charset=\"ISO-8859-1\">\n");
        sb.append("<title>IBM MQ Managed File Transfer Demo</title>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append("IBM MQ Managed File Transfer");
        sb.append("Message to File transfer Demo");
        sb.append("<a href=\"myapp/myresource\">Click me to post a SWIFT Message</a>\n");
        sb.append("</body>\n");
        sb.append("</html>\n");
        return sb.toString();
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("myresource")
    @Produces(MediaType.TEXT_HTML)
    public String getIt() {
        String replyMsg = "";
        System.out.println("Received request ");
        try {
            replyMsg = MQOps.postMessage();
            System.out.println("Generate request with ID: " + replyMsg);
        } catch(Exception ex) {
            System.out.println("Exeption caught");
            ex.printStackTrace();
            replyMsg = ex.getLocalizedMessage();
        }
        return replyMsg;
    }
}
