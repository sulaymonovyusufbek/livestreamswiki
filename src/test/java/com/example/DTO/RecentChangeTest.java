package com.example.DTO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class RecentChangeTest {

    @Test
    void testRecentChangeGettersAndSetters() {
        RecentChange change = new RecentChange();

        change.setTitle("Sample Title");
        change.setUser("TestUser");
        change.setTimestamp(1707456000L);
        change.setTitleUrl("https://example.com/sample-title");
        change.setWiki("TestWiki");

        assertEquals("Sample Title", change.getTitle(), "Title should be correctly set and retrieved");
        assertEquals("TestUser", change.getUser(), "User should be correctly set and retrieved");
        assertEquals(1707456000L, change.getTimestamp(), "Timestamp should be correctly set and retrieved");
        assertEquals("https://example.com/sample-title", change.getTitleUrl(), "Title URL should be correctly set and retrieved");
        assertEquals("TestWiki", change.getWiki(), "Wiki should be correctly set and retrieved");
    }



}
