package io.openliberty.sample.hello;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

@Path("hello")
public class HelloResource {

    @GET
    public String hello() {
        return "Hello!";
    }

    @GET
    @Path("fail")
    public String fail() {
        throw new WebApplicationException(500);
    }

}
