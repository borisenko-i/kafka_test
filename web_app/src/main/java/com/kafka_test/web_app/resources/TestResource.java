package com.kafka_test.web_app.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("test_resource")
public class TestResource {
    private static List<Resource> resources = new ArrayList<>();

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        Resource defaultResource = new Resource();
        defaultResource.resourceName = "Test Resource Default";
        if (resources.isEmpty()) resources.add(defaultResource);
        return resources.stream().map(x -> x.resourceName).collect(Collectors.joining("\n"));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Resource resource) {
        resources.add(resource);
    }
}