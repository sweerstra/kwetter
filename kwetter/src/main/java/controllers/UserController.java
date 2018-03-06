package controllers;

import domain.User;
import services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    @Inject
    private UserService userService;

    @GET
    @Path("/{username}")
    public Response getUser(@PathParam("username") String username) {
        User user = userService.getUserByUsername(username);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(user).build();
    }

    @GET
    @Path("/list")
    public Response getUsers() {
        return Response.ok(userService.getAll()).build();
    }

    @POST
    @Path("/follow/{id}/{followingId}")
    public Response followUser(@PathParam("id") long id, @PathParam("followingId") long followingId) {
        if (userService.followUser(id, followingId)) {
            return Response.ok().build();
        } else {
            return Response.status(400).build();
        }
    }

    @GET
    @Path("/following/{id}")
    public Response getFollowing(@PathParam("id") long id) {
        return Response.ok(userService.getFollowing(id)).build();
    }

    @GET
    @Path("/followers/{id}")
    public Response getFollowers(@PathParam("id") long id) {
        return Response.ok(userService.getFollowers(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(User _user) {
        User user = userService.addUser(new User(_user.getUsername(), _user.getPassword(), User.Role.USER));
        return Response.ok(user).build();
    }
}
