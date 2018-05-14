package controllers;

import config.Link;
import domain.User;
import services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UsersController {
    @Inject
    private UserService service;

    @GET
    public Response getUsers(@Context UriInfo uriInfo) {
        List<User> users = service.getUsers();

        for (User user : users) {
            String href = uriInfo.getBaseUriBuilder()
                    .path(UserController.class)
                    .path("follow")
                    .path(String.valueOf(user.getId()))
                    .build()
                    .toString();

            user.addLink(new Link(href, "follow", "POST"));
        }

        return Response.ok(users).build();
    }
}