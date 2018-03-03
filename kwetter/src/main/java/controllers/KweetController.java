package controllers;

import dao.IKweetDao;
import domain.Kweet;
import services.KweetService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/kweet")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
public class KweetController {
    @Inject
    private
    KweetService service;

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") long id) {
        return Response.ok(service.getKweet(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Kweet kweet) {
        if (kweet == null) return Response.status(400).build();

        Kweet posted = service.postKweet(kweet);
        if (posted == null) return Response.status(400).build();

        return Response.ok(posted).build();
    }
}
