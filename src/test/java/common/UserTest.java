package common;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.common.enums.UserRole;
import domain.common.models.User;
import infrastructure.adapters.notifications.EmailAdapter;
import infrastructure.adapters.notifications.SlackAdapter;

class UserTest {

    // TC-46 Gebruiker maakt account aan met Slack ID
    @Test
    void testUserWithSlackId_ShouldUseSlackAdapter() {
        // Arrange: Creëer een gebruiker met een Slack ID en SlackAdapter
        User user = new User("user2", "456", "user2@example.com", UserRole.TESTER, new SlackAdapter());

        // Act: Geen specifieke actie nodig, we controleren direct de SenderStrategy

        // Assert: Verifieer dat de SenderStrategy van de gebruiker een SlackAdapter is
        assertTrue(user.getSenderStrategy() instanceof SlackAdapter, "User met Slack ID moet SlackAdapter gebruiken");
    }

    // TC-47 Gebruiker maakt account aan zonder Slack ID
    @Test
    void testUserWithoutSlackId_ShouldUseEmailAdapter() {
        // Arrange: Creëer een gebruiker zonder Slack ID en met EmailAdapter
        User user = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.DEVELOPER, new EmailAdapter());

        // Act: Geen specifieke actie nodig, we controleren direct de SenderStrategy

        // Assert: Verifieer dat de SenderStrategy van de gebruiker een EmailAdapter is
        assertTrue(user.getSenderStrategy() instanceof EmailAdapter, "User zonder Slack ID moet EmailAdapter gebruiken");
    }
}
