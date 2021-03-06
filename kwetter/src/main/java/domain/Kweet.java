package domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import config.Link;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@XmlRootElement
public class Kweet implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private String text;

    @ManyToOne
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    private int likes;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> hashtags = new ArrayList<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> mentions = new ArrayList<>();

    @Transient
    private List<Link> links = new ArrayList<>();

    public Kweet(String text, User user) {
        this.text = text;
        this.user = user;
        this.date = new Timestamp(System.currentTimeMillis());
        this.likes = 0;
        this.setHashtags();
        this.setMentions();
    }

    public Kweet() {}

    private List<String> getListOfMatchedText(String regex) {
        Matcher hashtagMatcher = Pattern.compile(regex).matcher(this.text);
        List<String> list = new ArrayList<>();
        while (hashtagMatcher.find()) {
            list.add(hashtagMatcher.group(1));
        }
        return list;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags() {
        this.hashtags = getListOfMatchedText("(#\\w+)");
    }

    public List<String> getMentions() {
        return mentions;
    }

    public void setMentions() {
        this.mentions = getListOfMatchedText("(@\\w+)");
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void incrementLike() {
        this.likes += 1;
    }

    public List<Link> getLinks() {
        this.addLink(new Link("http://localhost:8080/kwetter/api/kweet/" + this.id, "self", "GET"));
        this.addLink(new Link("http://localhost:8080/kwetter/api/kweet/", "add", "POST"));
        this.addLink(new Link("http://localhost:8080/kwetter/api/kweet/" + this.id, "remove", "DELETE"));
        this.addLink(new Link("http://localhost:8080/kwetter/api/kweet/" + this.id, "edit", "PUT"));
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {
        this.links.add(link);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Kweet) && ((Kweet) obj).getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }
}
