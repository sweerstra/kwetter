package beans;

import domain.User;
import org.primefaces.event.SelectEvent;
import services.UserService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class UsersBean implements Serializable {
    @Inject
    private UserService userService;

    private User selectedUser;
    private String selectedRole;

    public List<User> getUsers() {
        return userService.getUsers();
    }

    public void onUserSelect(SelectEvent event) {
        User user = (User) event.getObject();
        this.setSelectedUser(user);
    }

    public void saveUser() {
        User.Role newRole = User.Role.valueOf(this.selectedRole);
        FacesMessage message;

        if (this.selectedUser == null) {
            message = new FacesMessage("Please select an user to update", "");
        } else if (this.selectedUser.getRole() == newRole) {
            message = new FacesMessage("Please select a different role to update ", this.selectedUser.getUsername());
        } else {
            selectedUser.setRole(newRole);
            userService.editRole(this.selectedUser.getId(), newRole);
            message = new FacesMessage(this.selectedUser.getUsername() + "'s role updated to", newRole.name());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
}
