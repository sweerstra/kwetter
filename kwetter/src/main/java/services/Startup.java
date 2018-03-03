package services;

import domain.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
@javax.ejb.Startup
public class Startup {
    @Inject
    private UserService userService;

    @PostConstruct
    public void initData() {
        try {
            userService.addUser(new User("user", "password", User.Role.USER));
            userService.addUser(new User("admin", "password", User.Role.ADMINISTRATOR));
            userService.addUser(new User("moderator", "password", User.Role.MODERATOR));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
