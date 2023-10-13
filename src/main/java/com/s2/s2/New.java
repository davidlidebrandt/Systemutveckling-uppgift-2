package com.s2.s2;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/new")
public class New {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello!";
    }
}
