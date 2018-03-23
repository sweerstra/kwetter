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
        User user = userService.addUser("user1", "password", User.Role.USER);
        User admin = userService.addUser("administrator1", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", User.Role.ADMINISTRATOR);
        userService.addUser("moderator1", "password", User.Role.USER);

        kweetService.postKweet(new Kweet("#heftig ongeluk hier", user));
        kweetService.postKweet(new Kweet("@niffo #fissa in de stad #heftig", user));
        kweetService.postKweet(new Kweet("Dit is niet meer normaal #heftig", admin));

        /*userService.followUser(1, 2);
        userService.followUser(1, 3);*/
    }
}
