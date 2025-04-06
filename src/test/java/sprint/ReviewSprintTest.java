package sprint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import domain.backlogitem.models.BacklogItem;
import domain.common.enums.UserRole;
import domain.common.models.Backlog;
import domain.common.models.Project;
import domain.common.models.User;
import domain.sprint.Sprint;
import domain.sprint.states.CreatedSprintState;
import domain.sprint.states.FinalizedSprintState;
import domain.sprint.states.raportedSprintState;
import domain.sprint.strategies.ReviewSprintStrategy;
import infrastructure.adapters.notifications.EmailAdapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Date;

public class ReviewSprintTest {
    private User productOwner;
    private User scrumMaster;
    private Sprint mocked_Sprint;

    @BeforeEach
    void setUp() {
        // Initialiseer de benodigde objecten
        mocked_Sprint = mock(Sprint.class);
        productOwner = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER,
                new EmailAdapter());
        scrumMaster = new User("scrum", "123", "scrum@example.com", "slack1", UserRole.SCRUMMASTER, new EmailAdapter());
    }

    // TC-20 Een review sprint wordt afgerond zonder dat er een review samenvatting
    // is geüpload
    @Test
    void testFinishReviewSprintWithoutReviewSummary_ShouldModifyReviewSummary() {
        // Arrange
        Project project = new Project(1, "Project Alpha", "Description of project alpha", productOwner, null);
        Sprint reviewSprint = new Sprint("Sprint Review", new Date(), new Date(), scrumMaster,
                new ReviewSprintStrategy(), project);
        Backlog backlog = new Backlog(1, project);
        project.setBacklog(backlog);

        assertNull(reviewSprint.getReviewSummery(), "Review summary should be null before finishing.");

        // Act
        reviewSprint.start();
        reviewSprint.finish();

        reviewSprint.setReviewSummery("Default Review Summary");

        // Assert
        assertNotNull(reviewSprint.getReviewSummery());
        assertEquals("Default Review Summary", reviewSprint.getReviewSummery());
    }

    // TC-21 Een afgeronde review sprint wordt onthouden
    @Test
    void testCompletedReviewSprintShouldBeRemembered() {
        // Arrange
        Project project = new Project(1, "Project Beta", "Description of project beta", productOwner, null);
        Sprint reviewSprint = new Sprint("Sprint Review", new Date(), new Date(), scrumMaster,
                new ReviewSprintStrategy(), project);

        assertNull(reviewSprint.getReviewSummery(), "Review summary should be null before finishing.");

        // Act
        reviewSprint.start();
        reviewSprint.finish();
        reviewSprint.setReviewSummery("Default Review Summary");
        reviewSprint.finalized();

        // Assert
        assertTrue(reviewSprint.getState() instanceof FinalizedSprintState);
    }

    // TC-22 Een sprint van het type review kan worden aangemaakt
    @Test
    void testCreateReviewSprint_ShouldBeCreatedWithCorrectType() {
        // Arrange
        Project project = new Project(1, "Project Gamma", "Description of project gamma", productOwner, null);
        Sprint reviewSprint = new Sprint("Sprint Review", new Date(), new Date(), scrumMaster,
                new ReviewSprintStrategy(), project);

        // Act
        assertTrue(reviewSprint.getSprintStrategy() instanceof ReviewSprintStrategy,
                "The sprint strategy should be 'ReviewSprintStrategy'.");
    }

    // TC-25 Een release sprint wordt afgerond, met een werkende pipeline. De scrum
    // master en product owner krijgen een notificatie van de correcte afsluiting.
    @Test
    void testFinishReleaseSprintWithWorkingPipeline_ShouldSendNotification() {
        // Arrange
        Project project = new Project(1, "Project Gamma", "Description of project gamma", productOwner, null);
        Sprint realSprint = new Sprint("Sprint Review", new Date(), new Date(), scrumMaster, new ReviewSprintStrategy(), project);
        Sprint spy = Mockito.spy(realSprint);

        spy.setState(new CreatedSprintState(spy));

        // Ensure the sprint is aware of the strategy
        doNothing().when(spy).sendNotification(any(User[].class), anyString(), anyString());

        // Set the review summary so the strategy's if-check passes
        spy.setReviewSummery("Some review summary");

        // Act
        spy.start();
        spy.finish();
        spy.finalized();

        // Capture the arguments passed to sendNotification
        ArgumentCaptor<User[]> sendToCaptor = ArgumentCaptor.forClass(User[].class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

        // Verify the spy, not mocked_Sprint
        verify(spy).sendNotification(sendToCaptor.capture(), subjectCaptor.capture(), bodyCaptor.capture());

        // Assert
        User[] capturedUsers = sendToCaptor.getValue();
        String capturedSubject = subjectCaptor.getValue();
        String capturedBody = bodyCaptor.getValue();

        assertNotNull(capturedUsers, "Captured users should not be null.");
        assertEquals(2, capturedUsers.length, "There should be two users in the notification.");
        assertTrue(Arrays.stream(capturedUsers).anyMatch(user -> user.getRole() == UserRole.SCRUMMASTER),
                "Scrum Master should be notified.");
        assertTrue(Arrays.stream(capturedUsers).anyMatch(user -> user.getRole() == UserRole.PRODUCTOWNER),
                "Product Owner should be notified.");
    }
}
