package sprint;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import domain.common.enums.UserRole;
import domain.common.models.Project;
import domain.common.models.User;
import domain.pipeline.Pipeline;
import domain.sprint.Sprint;
import domain.sprint.states.CreatedSprintState;
import domain.sprint.strategies.ReleaseSprintStrategy;
import infrastructure.adapters.notifications.EmailAdapter;

public class ReleaseSprintTest {
    private User productOwner;
    private User scrumMaster;
    Pipeline pipeline ;

    @BeforeEach
    void setUp() {
        // Initialiseer de benodigde objecten
        productOwner = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER, new EmailAdapter());
        scrumMaster = new User("scrum", "123", "scrum@example.com", "slack1", UserRole.SCRUMMASTER, new EmailAdapter());
        pipeline = new Pipeline("Release Pipeline");
    }
    

    //TC-23 Een sprint van het type release kan worden aangemaakt
    @Test
    void testCreateReleaseSprint_ShouldBeCreatedWithCorrectType() {
        // Arrange
        Project project = new Project(1, "Project Gamma", "Description of project gamma", productOwner, null);
        Sprint reviewSprint = new Sprint("Sprint Review", new Date(), new Date(), scrumMaster, new ReleaseSprintStrategy(pipeline), project);
        
        // Act
        assertTrue(reviewSprint.getSprintStrategy() instanceof ReleaseSprintStrategy, "The sprint strategy should be 'ReviewSprintStrategy'.");
    }
    
    // TC-26 Een release sprint wordt afgerond, met een niet werkende pipeline.
    // De scrum master en product owner krijgen een notificatie van de niet correcte afsluiting.
    @Test
    void testFinishReleaseSprintWithNonWorkingPipeline_ShouldNotifyScrumMasterAndProductOwner() {
         // Arrange
        Project project = new Project(1, "Project Gamma", "Description of project gamma", productOwner, null);
        Sprint reviewSprint = new Sprint("Sprint Review", new Date(), new Date(), scrumMaster, new ReleaseSprintStrategy(pipeline), project);
        Sprint spy = Mockito.spy(reviewSprint);

        spy.setState(new CreatedSprintState(spy));

        doNothing().when(spy).sendNotification(any(User.class), anyString(), anyString());

        // Act
        spy.start();
        spy.finish();
        spy.cancel();

        // Capture the arguments passed to sendNotification
        ArgumentCaptor<User> sendToCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

        // Verify the spy, not mocked_Sprint
        verify(spy).sendNotification(sendToCaptor.capture(), subjectCaptor.capture(), bodyCaptor.capture());

        // Assert
        User capturedUsers = sendToCaptor.getValue();
        String capturedSubject = subjectCaptor.getValue();
        String capturedBody = bodyCaptor.getValue();

        assertEquals(scrumMaster, capturedUsers, "The notification should be sent to the scrum master.");
        assertEquals("Sprint Canceled", capturedSubject, "The subject should be 'Sprint Canceled'.");
    }
}
