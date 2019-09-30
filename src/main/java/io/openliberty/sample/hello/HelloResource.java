package io.openliberty.sample.hello;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import java.util.Random;

@Path("hello")
public class HelloResource {

    @GET
    public String hello() {
        if (new Random().nextDouble() < 0.9)
            throw new WebApplicationException();
        return "Hello!";
    }

}
