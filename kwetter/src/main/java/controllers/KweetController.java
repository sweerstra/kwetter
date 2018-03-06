package controllers;

import domain.Kweet;
import services.KweetService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/kweet")
@Produces(MediaType.APPLICATION_JSON)
public class KweetController {
    @Inject
    private KweetService service;

    @GET
    @Path("/{id}")
    public Response getKweet(@PathParam("id") long id) {
        return Response.ok(service.getKweet(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Kweet _kweet) {
        if (_kweet == null) {
            return Response.status(404).build();
        }

        Kweet posted = service.postKweet(new Kweet(_kweet.getText(), _kweet.getUser()));
        if (posted == null) {
            return Response.status(400).build();
        }

        return Response.ok(posted).build();
    }

    @GET
    @Path("/search/{text}")
    public Response searchKweets(@PathParam("text") String text) {
        return Response.ok(service.searchKweets(text)).build();
    }

    @GET
    @Path("/user/{id}")
    public Response getKweets(@PathParam("id") long id) {
        return Response.ok(service.getKweetsOfUser(id)).build();
    }

    @GET
    @Path("/trend/{trend}")
    public Response getKweetsByTrend(@PathParam("trend") String trend) {
        return Response.ok(service.getKweetsByTrend(trend)).build();
    }

    @GET
    @Path("/trends")
    public Response getCurrentTrends() {
        return Response.ok(service.getTrends()).build();
    }

    @GET
    @Path("/timeline/{id}")
    public Response getTimeline(@PathParam("id") long id) {
        return Response.ok(service.getTimeline(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteKweet(@PathParam("id") long id) {
        service.removeKweet(id);
        return Response.ok().build();
    }
}
