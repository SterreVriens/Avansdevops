package backlogitem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.backlogitem.models.Activity;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    private Activity activity;

    @BeforeEach
    void setUp() {
        activity = new Activity("Initial title", "Initial description");
    }

    @Test
    void testGetTitle() {
        assertEquals("Initial title", activity.getTitle());
    }

    @Test
    void testSetTitle() {
        activity.setTitle("Updated title");
        assertEquals("Updated title", activity.getTitle());
    }

    @Test
    void testGetDescription() {
        assertEquals("Initial description", activity.getDescription());
    }

    @Test
    void testSetDescription() {
        activity.setDescription("Updated description");
        assertEquals("Updated description", activity.getDescription());
    }

}
