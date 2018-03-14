package controllers;

import domain.Kweet;
import interceptors.KweetLoggingInterceptor;
import models.ResponseBody;
import services.KweetService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
@Path("/kweet")
@Produces(MediaType.APPLICATION_JSON)
public class KweetController {
    @Inject
    private KweetService service;

    @GET
    @Path("/{id}")
    public Response getKweet(@PathParam("id") long id) {
        Kweet kweet = service.getKweet(id);
        return Response.ok(kweet).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors(KweetLoggingInterceptor.class)
    public Response postKweet(Kweet kweet) {
        Kweet postedKweet = service.postKweet(new Kweet(kweet.getText(), kweet.getUser()));
        if (postedKweet == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(postedKweet).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editKweet(Kweet kweet) {
        Kweet editedKweet = service.editKweet(kweet);

        if (editedKweet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(editedKweet).build();
    }

    @GET
    @Path("/search/{text}")
    public Response searchKweets(@PathParam("text") String text) {
        return Response.ok(service.searchKweets(text)).build();
    }

    @GET
    @Path("/user/{id}")
    public Response getKweetsOfUser(@PathParam("id") long id) {
        List<Kweet> kweets = service.getKweetsOfUser(id);

        if (kweets == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(kweets).build();
    }

    @GET
    @Path("/trend/{trend}")
    public Response getKweetsByTrend(@PathParam("trend") String trend) {
        return Response.ok(service.getKweetsByTrend(trend)).build();
    }

    @GET
    @Path("/mention/{mention}")
    public Response getKweetsByMention(@PathParam("mention") String mention) {
        return Response.ok(service.getKweetsByMention(mention)).build();
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

    @POST
    @Path("/like/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response likeKweet(Kweet kweet, @PathParam("userId") long userId) {
        boolean result = service.likeKweet(kweet, userId);
        return Response.ok(new ResponseBody(result, null)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteKweet(@PathParam("id") long id) {
        service.removeKweet(id);
        return Response.ok(new ResponseBody(true, null)).build();
    }
}
