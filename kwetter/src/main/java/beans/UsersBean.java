package beans;

import domain.User;
import domain.UserGroup;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.event.SelectEvent;
import services.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class UsersBean implements Serializable {
    @Inject
    private UserService userService;

    @Inject
    private KweetsBean kweetsBean;

    private List<User> users;

    private User selectedUser;

    private List<UserGroup> selectedUserGroups;

    @PostConstruct
    public void init() {
        this.users = userService.getUsers();
        this.selectedUserGroups = new ArrayList<>();
    }

    public void onSaveUser() {
        FacesMessage message;

        if (selectedUser == null) {
            message = new FacesMessage("Please select an user to update", "");
        } else if (selectedUserGroups.isEmpty()) {
            message = new FacesMessage("Please select any user group before updating", selectedUser.getUsername());
        } else {
            userService.editUserGroups(selectedUser.getId(), selectedUserGroups);
            this.users = userService.getUsers();
            return;
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onAddUser(String username, String password) {
        User user = new User(username, password);
        User createdUser = userService.addUser(user);

        if (createdUser != null) {
            this.users.add(createdUser);
        }

        FacesMessage message = new FacesMessage(createdUser == null ? "Username already in use" : "Added user", username);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void selectUser(SelectEvent event) {
        User user = ((User) event.getObject());
        this.setSelectedUser(user);
        kweetsBean.onUserRowSelect(user);
    }

    public void onUserGroupEdit(AjaxBehaviorEvent event) {
        String group = (String) event.getComponent().getAttributes().get("group");
        boolean value = ((SelectBooleanCheckbox) event.getSource()).isSelected();

        if (value) {
            addUserGroup(new UserGroup(group));
        } else {
            removeUserGroup(group);
        }
    }

    public void onRemoveUser(User user) {
        users.remove(user);
        kweetsBean.setKweets(new ArrayList<>());
        userService.deleteUser(user.getId());
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void addUserGroup(UserGroup group) {
        this.selectedUserGroups.add(group);
    }

    public void removeUserGroup(String groupName) {
        for (UserGroup group : this.selectedUserGroups) {
            if (group.getName().equals(groupName)) {
                this.selectedUserGroups.remove(group);
            }
        }
    }
}
