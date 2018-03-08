package domain;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KweetTest {
    @Test
    public void mentionsShouldBeReturnedAsStrings() {
        User user = new User("username", "password", User.Role.USER);
        Kweet kweetWithMentions = new Kweet("@otherUsername @anotherUsername goedendag", user);

        List<String> mentions1 = kweetWithMentions.getMentions();
        assertEquals(2, mentions1.size());
        assertTrue(mentions1.contains("@otherUsername"));
        assertTrue(mentions1.contains("@anotherUsername"));

        Kweet kweetWithoutMentions = new Kweet("normaal berichtje", user);
        List<String> mentions2 = kweetWithoutMentions.getMentions();
        assertEquals(0, mentions2.size());

        Kweet kweetWithAtSign = new Kweet("ken je dit teken @?", user);
        assertEquals(0, kweetWithAtSign.getMentions().size());
    }

    @Test
    public void hashtagsShouldBeReturnedAsStrings() {
        User user = new User("username", "password", User.Role.USER);
        Kweet kweetWithHashtags = new Kweet("#heftig #intens dit", user);

        List<String> hashtags1 = kweetWithHashtags.getHashtags();
        assertEquals(2, hashtags1.size());
        assertTrue(hashtags1.contains("#heftig"));
        assertTrue(hashtags1.contains("#intens"));

        Kweet kweetWithoutHashtags = new Kweet("normaal berichtje", user);
        List<String> hashtags2 = kweetWithoutHashtags.getHashtags();
        assertEquals(0, hashtags2.size());

        Kweet kweetWithSingleHashtag = new Kweet("bel me #", user);
        assertEquals(0, kweetWithSingleHashtag.getHashtags().size());
    }
}
