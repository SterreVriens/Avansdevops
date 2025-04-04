package sprint;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.common.enums.UserRole;
import domain.common.models.Project;
import domain.common.models.User;
import domain.pipeline.Pipeline;
import domain.sprint.Sprint;
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
}
