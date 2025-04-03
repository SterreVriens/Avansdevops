package  common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import  domain.common.enums.UserRole;
import  domain.common.models.User;
import  domain.notification.interfaces.ISenderStrategy;
import infrastructure.adapters.notifications.EmailAdapter;
import infrastructure.adapters.notifications.SlackAdapter;

class UserTest {
    private ISenderStrategy slackAdapterMock;
    private ISenderStrategy emailAdapterMock;

    @BeforeEach
    void setUp() {
        slackAdapterMock = mock(SlackAdapter.class);
        emailAdapterMock = mock(EmailAdapter.class);
    }

    // TC-46: Gebruiker maakt account aan met Slack ID
    @Test
    void testUserWithSlackId_ShouldUseSlackAdapter() {
        
        User user = new User("user2", "456", "user2@example.com", UserRole.TESTER, new SlackAdapter());
        
        // Assert that the sender strategy is a SlackAdapter
        assertTrue(user.getSenderStrategy() instanceof SlackAdapter, "User met Slack ID moet SlackAdapter gebruiken");
    }

    // TC-47: Gebruiker maakt account aan zonder Slack ID
    @Test
    void testUserWithoutSlackId_ShouldUseEmailAdapter() {
        
        User user = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.DEVELOPER, new EmailAdapter());
        
        // Assert that the sender strategy is an EmailAdapter
        assertTrue(user.getSenderStrategy() instanceof EmailAdapter, "User zonder Slack ID moet EmailAdapter gebruiken");
    }
}