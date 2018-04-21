package controllers;

import config.KwetterProperties;
import domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import services.UserService;

import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

@RequestScoped
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {
    @Inject
    private UserService service;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(User user, @Context UriInfo uriInfo) throws NoSuchAlgorithmException {
        String username = user.getUsername();
        String password = user.getPassword();

        User authenticatedUser = service.authenticateUser(username, password);

        if (authenticatedUser != null) {
            String token = issueToken(authenticatedUser.getId(), username, uriInfo);
            return Response.ok("\"Bearer " + token + "\"").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String issueToken(long id, String username, UriInfo uriInfo) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, 12);

        // https://stormpath.com/blog/jwt-java-create-verify
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        KwetterProperties properties = new KwetterProperties("config.properties");
        String secret = properties.getValue("secret");
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setId(String.valueOf(id))
                .setSubject(username)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(cal.getTime())
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
    }
}