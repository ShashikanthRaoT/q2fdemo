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
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String root() {
      return getIndexPage();
    }

    private String getIndexPage() {
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append("<head>\n");
        sb.append("<meta charset=\"ISO-8859-1\">\n");
        sb.append("<title>Insert title here</title>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append("<a href=\"rest/hello\">Click Here</a>\n");
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
    @Produces(MediaType.TEXT_PLAIN)
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
