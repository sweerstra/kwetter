package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

@Entity
@XmlRootElement
public class User implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String email;

    @JsonIgnore
    private String password;
    private String profilePicture;
    private String bio;
    private String location;
    private String website;
    private Role role;

    @JoinTable(name = "user_groups",
            joinColumns = @JoinColumn(name = "USERNAME", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "GROUPNAME", referencedColumnName = "groupName"))
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Collection<Group> groups = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "following")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<User> followers = new ArrayList<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<User> following = new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Kweet> kweets = new ArrayList<>();

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Kweet> liked = new HashSet<>();

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public enum Role {
        USER,
        MODERATOR,
        ADMINISTRATOR
    }

    public User() {}

    public void addKweet(Kweet kweet) {
        kweets.add(kweet);
    }

    public boolean addLike(Kweet kweet) {
        return liked.add(kweet);
    }

    public boolean addFollowing(User user) {
        for (User following : this.following) {
            if (following.getId() == user.getId()) {
                return false;
            }
        }

        following.add(user);
        return true;
    }

    public boolean addFollower(User user) {
        for (User follower : this.followers) {
            if (follower.getId() == user.getId()) {
                return false;
            }
        }

        followers.add(user);
        return true;
    }

    public boolean removeFollowing(User user) {
        for (User following : this.following) {
            if (following.getId() == user.getId()) {
                this.following.remove(following);
                return true;
            }
        }
        return false;
    }

    public boolean removeFollower(User user) {
        for (User follower : this.followers) {
            if (follower.getId() == user.getId()) {
                this.followers.remove(follower);
                return true;
            }
        }
        return false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Collection<Group> getGroups() {
        return groups;
    }

    public void setGroups(Collection<Group> groups) {
        this.groups = groups;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(List<Kweet> kweets) {
        this.kweets = kweets;
    }

    public Set<Kweet> getLiked() {
        return liked;
    }

    public void setLiked(Set<Kweet> liked) {
        this.liked = liked;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof User) && ((User) obj).getId() == this.getId();
    }

    public User serialized() {
        this.setKweets(new ArrayList<>());
        this.setFollowers(new ArrayList<>());
        this.setFollowing(new ArrayList<>());
        return this;
    }
}
