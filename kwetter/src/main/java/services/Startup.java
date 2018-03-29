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
        String password = "password";

        User user = new User("user1", password);
        User moderator = new User("moderator1", password);
        User admin = new User("admin1", password);

        User createdUser = userService.addUser(user);
        User createdModerator = userService.addUser(moderator);
        User createdAdmin = userService.addUser(admin);

        UserGroup userGroup = userGroupService.addUserGroup(new UserGroup("user"));
        UserGroup moderatorGroup = userGroupService.addUserGroup(new UserGroup("moderator"));
        UserGroup adminGroup = userGroupService.addUserGroup(new UserGroup("admin"));

        userService.editUserGroup(1, userGroup);
        userService.editUserGroup(2, moderatorGroup);
        userService.editUserGroup(3, adminGroup);

        kweetService.postKweet(new Kweet("heftig ongeluk hier", createdUser));
        kweetService.postKweet(new Kweet("@niffo #fissa in de stad #heftig", createdModerator));
        kweetService.postKweet(new Kweet("Dit is niet meer normaal #heftig", createdAdmin));

        /*userService.followUser(1, 2);
        userService.followUser(1, 3);*/
    }
}
