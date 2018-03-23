package beans;

import domain.User;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import services.UserService;

import javax.annotation.PostConstruct;
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

    private List<User> users;

    @PostConstruct
    public void init() {
        this.users = userService.getUsers();
    }

    public void onCellEdit(CellEditEvent event) {
        String oldValue = event.getOldValue().toString();
        String newValue = event.getNewValue().toString();

        if (!oldValue.equals(newValue)) {
            User entity = (User) ((DataTable) event.getComponent()).getRowData();
            editUser(entity.getUsername(), newValue);
        }
    }

    public void addUser(String username, String password, String role) {
        User user = userService.addUser(username, password, User.Role.valueOf(role));

        if (user != null) {
            this.users.add(user);
        }

        FacesMessage message = new FacesMessage(user == null ? "Username already in use" : "Added user", username);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void editUser(String username, String role) {
        User user = userService.getUserByUsername(username);
        User.Role newRole = User.Role.valueOf(role);
        FacesMessage message;

        if (user == null) {
            message = new FacesMessage("Please select an user to update", "");
        } else if (user.getRole() == newRole) {
            message = new FacesMessage("Please select a different role to update ", user.getUsername());
        } else {
            user.setRole(newRole);
            userService.editRole(user.getId(), newRole);
            message = new FacesMessage(user.getUsername() + "'s role updated to", newRole.name());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
