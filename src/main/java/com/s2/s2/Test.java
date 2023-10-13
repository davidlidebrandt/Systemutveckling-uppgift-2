package com.s2.s2;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/")
public class Test {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> hello() {
        return List.of("1", "2");
    }
}
