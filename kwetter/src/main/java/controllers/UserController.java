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
    private UserService service;

    @GET
    @Path("/{username}")
    public Response getUser(@PathParam("username") String username) {
        User user = service.getUserByUsername(username);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(user).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User _user) {
        User user = service.addUser(new User(_user.getUsername(), _user.getPassword(), User.Role.USER));
        return Response.ok(user).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editUser(User user) {
        User editedUser = service.editUser(user);

        if (editedUser != null) {
            return Response.ok("{ \"response\": \"true\" }").build();
        } else {
            return Response.status(400).build();
        }
    }

    @GET
    @Path("/list")
    public Response getUsers() {
        return Response.ok(service.getAll()).build();
    }

    @POST
    @Path("/follow/{id}/{followingId}")
    public Response followUser(@PathParam("id") long id, @PathParam("followingId") long followingId) {
        boolean result = service.followUser(id, followingId);

        String json = result
                ? "{ \"response\": \"true\" }"
                : "{ \"response\": \"false\", \"message\": \"User does not exist or the connection between these users already exists\" }";

        return Response.ok(json).build();
    }

    @POST
    @Path("/unfollow/{id}/{unfollowingId}")
    public Response unfollowUser(@PathParam("id") long id, @PathParam("unfollowingId") long unfollowingId) {
        boolean result = service.unfollowUser(id, unfollowingId);

        String json = result
                ? "{ \"response\": \"true\" }"
                : "{ \"response\": \"false\", \"message\": \"User or the connection between these users does not exist\" }";

        return Response.ok(json).build();
    }

    @GET
    @Path("/following/{id}")
    public Response getFollowing(@PathParam("id") long id) {
        return Response.ok(service.getFollowing(id)).build();
    }

    @GET
    @Path("/followers/{id}")
    public Response getFollowers(@PathParam("id") long id) {
        return Response.ok(service.getFollowers(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeUser(@PathParam("id") long id) {
        service.deleteUser(id);
        return Response.ok("{ \"response\": \"true\" }").build();
    }
}
