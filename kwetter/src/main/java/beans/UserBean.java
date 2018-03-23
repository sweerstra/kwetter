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

    @NotNull(message = "Please enter a role")
    private String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
