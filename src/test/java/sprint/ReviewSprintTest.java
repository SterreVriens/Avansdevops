package sprint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.backlogitem.models.BacklogItem;
import domain.common.enums.UserRole;
import domain.common.models.Backlog;
import domain.common.models.Project;
import domain.common.models.User;
import domain.sprint.Sprint;
import domain.sprint.states.FinalizedSprintState;
import domain.sprint.states.raportedSprintState;
import domain.sprint.strategies.ReviewSprintStrategy;
import infrastructure.adapters.notifications.EmailAdapter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class ReviewSprintTest {
    private User productOwner;
    private User scrumMaster;

    @BeforeEach
    void setUp() {
        // Initialiseer de benodigde objecten
        productOwner = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER, new EmailAdapter());
        scrumMaster = new User("scrum", "123", "scrum@example.com", "slack1", UserRole.SCRUMMASTER, new EmailAdapter());
    }

    //TC-20 Een review sprint wordt afgerond zonder dat er een review samenvatting is geüpload
    @Test
    void testFinishReviewSprintWithoutReviewSummary_ShouldModifyReviewSummary() {
        // Arrange
        Project project = new Project(1, "Project Alpha", "Description of project alpha", productOwner, null);
        Sprint reviewSprint = new Sprint("Sprint Review", new Date(), new Date(), scrumMaster, new ReviewSprintStrategy(), project);
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

    //TC-21 Een afgeronde review sprint wordt onthouden
    @Test
    void testCompletedReviewSprintShouldBeRemembered() {
        // Arrange
        Project project = new Project(1, "Project Beta", "Description of project beta", productOwner, null);
        Sprint reviewSprint = new Sprint("Sprint Review", new Date(), new Date(), scrumMaster, new ReviewSprintStrategy(), project);

        assertNull(reviewSprint.getReviewSummery(), "Review summary should be null before finishing.");

        // Act
        reviewSprint.start();
        reviewSprint.finish();
        reviewSprint.setReviewSummery("Default Review Summary");
        reviewSprint.finalize();

        // Assert
        assertTrue(reviewSprint.getState() instanceof FinalizedSprintState);
    }

    //TC-22 Een sprint van het type review kan worden aangemaakt
    @Test
    void testCreateReviewSprint_ShouldBeCreatedWithCorrectType() {
        // Arrange
        Project project = new Project(1, "Project Gamma", "Description of project gamma", productOwner, null);
        Sprint reviewSprint = new Sprint("Sprint Review", new Date(), new Date(), scrumMaster, new ReviewSprintStrategy(), project);
        
        // Act
        assertTrue(reviewSprint.getSprintStrategy() instanceof ReviewSprintStrategy, "The sprint strategy should be 'ReviewSprintStrategy'.");
    }

    
}
