package beans;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.Principal;

@Named
@SessionScoped
public class AuthBean implements Serializable {
    @NotNull(message = "Please enter a username")
    private String username = "admin1";

    @NotNull(message = "Please enter a password")
    private String password = "password";

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.login(this.username, this.password);

            boolean isModerator = request.isUserInRole("ModeratorRole");
            boolean isAdmin = request.isUserInRole("AdminRole");

            if (isModerator || isAdmin) {
                return "admin/administration.xhtml";
            } else {
                ((HttpSession) FacesContext.getCurrentInstance()
                        .getExternalContext().getSession(false))
                        .invalidate();
            }
        } catch (ServletException e) {}
        return "error/403.xhtml";
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
            return "/login.xhtml";
        } catch (ServletException e) {
            return null;
        }
    }

    public String getUserPrincipalName() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Principal principal = (fc.getExternalContext()).getUserPrincipal();
        if (principal == null) {
            return null;
        }
        return principal.getName();
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

