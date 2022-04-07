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
    public Response root() {
      return Response.seeOther(URI.create("/WebContent/index")).build();
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
