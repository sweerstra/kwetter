package controllers;

import authentication.JWT;
import config.Link;
import domain.User;
import domain.UserGroup;
import models.ResponseBody;
import services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    @Inject
    private UserService service;

    @GET
    @Path("/{username}")
    public Response getUser(@PathParam("username") String username, @Context UriInfo uriInfo) {
        User user = service.getUserByUsername(username);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        String selfHref = uriInfo.getBaseUriBuilder()
                .path(UserController.class)
                .path(username)
                .build()
                .toString();

        String kweetsHref = uriInfo.getBaseUriBuilder()
                .path(KweetController.class)
                .path(UserController.class)
                .path(username)
                .build()
                .toString();

        user.addLink(new Link(selfHref, "self", "GET"));
        user.addLink(new Link(kweetsHref, "kweets", "GET"));

        return Response.ok(user.serialized()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        User createdUser = service.addUser(user);

        if (createdUser == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(createdUser).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editUser(User user) {
        User editedUser = service.editUser(user);

        if (editedUser == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(editedUser.serialized()).build();
    }

    @POST
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(User user) {
        User authenticatedUser = service.authenticateUser(user.getUsername(), user.getPassword());

        if (authenticatedUser != null) {
            return Response.ok(authenticatedUser.serialized()).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("/{id}/follow/{followingId}")
    @JWT
    public Response followUser(@PathParam("id") long id, @PathParam("followingId") long followingId) {
        boolean result = service.followUser(id, followingId);
        String errorMessage = !result ? "User does not exist or the connection between these users already exists" : null;
        return Response.ok(new ResponseBody(result, errorMessage)).build();
    }

    @POST
    @Path("/{id}/unfollow/{unfollowingId}")
    @JWT
    public Response unfollowUser(@PathParam("id") long id, @PathParam("unfollowingId") long unfollowingId) {
        boolean result = service.unfollowUser(id, unfollowingId);
        String errorMessage = !result ? "User or the connection between these users does not exist" : null;
        return Response.ok(new ResponseBody(result, errorMessage)).build();
    }

    @GET
    @Path("/{id}/following")
    @JWT
    public Response getFollowing(@PathParam("id") long id) {
        return Response.ok(service.getFollowing(id)).build();
    }

    @GET
    @Path("/{id}/followers")
    @JWT
    public Response getFollowers(@PathParam("id") long id) {
        return Response.ok(service.getFollowers(id)).build();
    }

    @PUT
    @Path("/{id}/group/{group}")
    public Response editUserGroup(@PathParam("id") long id, @PathParam("group") String group) {
        User userWithGroup = service.editUserGroup(id, new UserGroup(group));

        if (userWithGroup == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(userWithGroup.serialized()).build();
    }

    @PUT
    @Path("/{id}/groups")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editUserGroups(@PathParam("id") long id, List<UserGroup> groups) {
        User userWithGroup = service.editUserGroups(id, groups);

        if (userWithGroup == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(userWithGroup.serialized()).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeUser(@PathParam("id") long id) {
        boolean deleteResult = service.deleteUser(id);
        String message = deleteResult ? String.format("Deleted user with id: %d", id) : "User does not exist";

        return Response.ok(new ResponseBody(deleteResult, message)).build();
    }
}
