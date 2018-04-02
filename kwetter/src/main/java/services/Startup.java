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
        kweetService.postKweet(new Kweet("@moderator1 ben helemaal klaar met jou #fuckmoderators", createdUser));
        kweetService.postKweet(new Kweet("krijg nou de pest pleuris #hondenlul", createdUser));
        kweetService.postKweet(new Kweet("@niffo #fissa in de stad #heftig", createdUser));

        kweetService.postKweet(new Kweet("@user1 gedraag je, ik ben de moderator hier", createdModerator));
        kweetService.postKweet(new Kweet("Op Kwetter heb ik ook iets te zeggen #metoo", createdModerator));
        kweetService.postKweet(new Kweet("Dit is niet meer normaal #heftig", createdAdmin));

        user.setProfilePicture("https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg");
        user.setBio("Intuligente jongun man, die graag opzoek is naar baan, stuur mail naar user1@mail.com.");
        user.setLocation("Noord-Brabant");
        user.setWebsite("user1.portfolio.nl");
        userService.editUser(user);
        moderator.setProfilePicture("https://cdn4.iconfinder.com/data/icons/business-and-marketing/500/Account_avatar_big_boss_business_businessman_client_boss_chef_consultant_man_user_support-512.png");
        userService.editUser(moderator);
        admin.setProfilePicture("https://cdn4.iconfinder.com/data/icons/people-std-pack/512/boss-512.png");
        userService.editUser(admin);

        userService.followUser(1, 2);
        userService.followUser(1, 3);
    }
}
