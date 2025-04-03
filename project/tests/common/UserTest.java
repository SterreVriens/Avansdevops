package project.tests.common;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import project.domain.common.enums.UserRole;
import project.domain.common.models.User;
import project.domain.notification.interfaces.ISenderStrategy;
import project.infrastructure.adapters.EmailAdapter;
import project.infrastructure.adapters.SlackAdapter;

public class UserTest {
    private ISenderStrategy slackAdapterMock;
    private ISenderStrategy emailAdapterMock;

    @Before
    public void setUp() {
        slackAdapterMock = mock(SlackAdapter.class);
        emailAdapterMock = mock(EmailAdapter.class);
    }



    // TC-46: Gebruiker maakt account aan met Slack ID
    @Test
    public void testUserWithSlackId_ShouldUseSlackAdapter() {
        User user = new User("testuser", "password123", "test@example.com", "SLACK123", UserRole.DEVELOPER);

        assertTrue("User met Slack ID moet SlackAdapter gebruiken", 
                   user.getSenderStrategy() instanceof SlackAdapter);
    }

    // TC-47: Gebruiker maakt account aan zonder Slack ID
    @Test
    public void testUserWithoutSlackId_ShouldUseEmailAdapter() {
        User user = new User("testuser", "password123", "test@example.com", UserRole.DEVELOPER);

        assertTrue("User zonder Slack ID moet EmailAdapter gebruiken", 
                   user.getSenderStrategy() instanceof EmailAdapter);
    }
}
