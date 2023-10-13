package com.s2.s2;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Person hello() {
        return new Person(7, "Tim");
    }
}

