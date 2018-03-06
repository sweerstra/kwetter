package domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @JsonManagedReference
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> hashtags = new ArrayList<String>();

    public Kweet(String text, User user) {
        this.text = text;
        this.user = user;
        this.date = new Timestamp(System.currentTimeMillis());

        this.setHashtags(matchList("(#\\w+)"));
        // this.setMentions(matchList("(@\\w+)"));
    }

    public Kweet() {
    }

    private List<String> matchList(String regex) {
        Matcher hashtagMatcher = Pattern.compile(regex).matcher(text);
        List<String> list = new ArrayList<String>();
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

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }
}
