package services;

import domain.Kweet;
import domain.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
@javax.ejb.Startup
public class Startup {
    @Inject
    private UserService userService;

    @Inject
    private KweetService kweetService;

    @PostConstruct
    public void initData() {
        User user = new User("user", "password", User.Role.USER);
        User admin = new User("admin", "password", User.Role.ADMINISTRATOR);
        User moderator = new User("moderator", "password", User.Role.MODERATOR);
        userService.addUser(user);
        userService.addUser(admin);
        userService.addUser(moderator);

        kweetService.postKweet(new Kweet("#heftig ongeluk hier", user));
        kweetService.postKweet(new Kweet("#fissa in de stad #heftig", user));
        kweetService.postKweet(new Kweet("Dit is niet meer normaal #heftig", admin));

        userService.followUser(1, 2);
        userService.followUser(1, 3);
    }
}
