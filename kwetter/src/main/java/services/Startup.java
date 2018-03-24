package services;

import domain.Kweet;
import domain.User;
import domain.UserGroup;

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

    @Inject
    private UserGroupService userGroupService;

    @PostConstruct
    public void initData() {
        String password = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
        User user = userService.addUser("1", password);
        User moderator = userService.addUser("2", password);
        User admin = userService.addUser("3", password);

        UserGroup userGroup = new UserGroup("user");
        UserGroup moderatorGroup = new UserGroup("moderator");
        UserGroup adminGroup = new UserGroup("admin");
        userGroup.addUser(user);
        moderatorGroup.addUser(moderator);
        adminGroup.addUser(admin);
        userGroupService.addUserGroup(userGroup);
        userGroupService.addUserGroup(moderatorGroup);
        userGroupService.addUserGroup(adminGroup);

        kweetService.postKweet(new Kweet("#heftig ongeluk hier", user));
        kweetService.postKweet(new Kweet("@niffo #fissa in de stad #heftig", user));
        kweetService.postKweet(new Kweet("Dit is niet meer normaal #heftig", admin));

        /*userService.followUser(1, 2);
        userService.followUser(1, 3);*/
    }
}
