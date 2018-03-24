package beans;

import domain.User;
import services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.Principal;

@Named
@RequestScoped
public class LoginBean implements Serializable {
    @Inject
    private UserService userService;

    @NotNull(message = "Please enter a username")
    private String username = "2";

    @NotNull(message = "Please enter a password")
    private String password = "password";

    /*public String login() {
        User loggedUser = userService.getUserByUsername(this.username);

        if (userService.authenticateUser(this.username, this.password)
                && loggedUser.getRole() == User.Role.MODERATOR || loggedUser.getRole() == User.Role.ADMINISTRATOR) {
            return "administration.xhtml";
        } else {
            FacesMessage message = new FacesMessage("Unauthenticated", this.username);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        return null;
    }*/

    public String getUserPrincipalName() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Principal principal = (fc.getExternalContext()).getUserPrincipal();
        if (principal == null) {
            return null;
        }
        return principal.getName();
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            User user = userService.getUserByUsername(this.username);
            String id = String.valueOf(user.getId());
            request.login(id, this.password);
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Login failed."));
            return "error/403.xhtml";
        }
        return "admin/administration.xhtml";
    }

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)
                context.getExternalContext().getRequest();
        try {
            request.logout();
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

