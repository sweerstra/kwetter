package controllers;

import domain.User;
import services.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class UserController {
    @Inject
    private UserService userService;

    @GET
    @Path("user/{id}")
    public Response getUser(@PathParam("id") long id) {
        return Response.ok(userService.getUserById(id)).build();
    }

    @GET
    @Path("users")
    public Response getUsers() {
        List<User> list = new ArrayList<User>();
        list.add(new User("Test", "123", User.Role.USER));
        return Response.ok(list).build();
    }

    @POST
    public Response post(@QueryParam("username") String username, @QueryParam("password") String password) {
        return Response.ok(userService.addUser(new User(username, password, User.Role.USER))).build();
    }
}
