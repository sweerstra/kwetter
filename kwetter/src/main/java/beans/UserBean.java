package beans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Named
@SessionScoped
public class UserBean implements Serializable {
    @NotNull(message = "Please enter an username")
    private String username;

    @NotNull(message = "Please enter a password")
    private String password;

    private boolean userRole = false;
    private boolean moderatorRole = false;
    private boolean adminRole = false;

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

    public boolean isUserRole() {
        return userRole;
    }

    public void setUserRole(boolean userRole) {
        this.userRole = userRole;
    }

    public boolean isModeratorRole() {
        return moderatorRole;
    }

    public void setModeratorRole(boolean moderatorRole) {
        this.moderatorRole = moderatorRole;
    }

    public boolean isAdminRole() {
        return adminRole;
    }

    public void setAdminRole(boolean adminRole) {
        this.adminRole = adminRole;
    }
}
