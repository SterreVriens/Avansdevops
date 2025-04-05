package sprint;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.backlogitem.models.BacklogItem;
import domain.common.enums.UserRole;
import domain.common.models.Backlog;
import domain.common.models.Project;
import domain.common.models.User;
import domain.sprint.Sprint;
import domain.sprint.strategies.ReleaseSprintStrategy;
import domain.sprint.strategies.ReviewSprintStrategy;
import infrastructure.adapters.notifications.EmailAdapter;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SprintTest {

    private User scrumMaster;
    private Project project;
    private Sprint sprint;
    private BacklogItem backlogItem;
    private Backlog backlog;
    private User productOwner;

    @BeforeEach
    void setUp() {
        // Initialiseer de benodigde objecten
        productOwner = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER, new EmailAdapter());
        scrumMaster = new User("scrum", "123", "scrum@example.com", "slack1", UserRole.SCRUMMASTER, new EmailAdapter());
        project = new Project(1, "Project Alpha", "Description of project alpha", productOwner, null);
        backlog = new Backlog(1, project);
        project.setBacklog(backlog);

        // Maak een nieuwe sprint aan met correcte gegevens
        
        sprint = new Sprint("Sprint 1", new Date(), new Date(), scrumMaster, new ReviewSprintStrategy(), project);

        // Maak een backlog item aan
        backlogItem = new BacklogItem(1, "Item A", "Description", null, project.getBacklog());
    }

    // TC-15 Een sprint wordt aangemaakt met correcte gegevens en gekoppeld aan het juiste project
    @Test
    void testCreateSprint_ShouldLinkToProject() {
        // Assert: Sprint moet gekoppeld zijn aan het juiste project
        assertEquals(project, sprint.getProject());
        assertEquals("Sprint 1", sprint.getName());
        assertNotNull(sprint.getStartDate());
        assertNotNull(sprint.getEndDate());
    }

    // TC-16 Een backlog item kan gekoppeld worden aan een sprint
    @Test
    void testAddBacklogItemToSprint_ShouldBeCorrectlyLinked() {
        // Act: Voeg het backlog item toe aan de sprint
        sprint.addBacklogItem(backlogItem);

        // Assert: Het backlog item moet gekoppeld zijn aan de sprint
        assertEquals(sprint, backlogItem.getSprint());
        assertTrue(sprint.getBacklogItems().contains(backlogItem));
    }

    // TC-24 Alle sprintdoelen binnen een project worden weergegeven.
    @Test
    void testGetAllSprintGoalsInProject_ShouldReturnAllSprintGoals() {
        // Arrange: Maak een project aan en voeg meerdere sprints toe met een doel
        Project project = new Project(1, "Project Alpha", "Description of project alpha", productOwner, null);
        
        // Maak een aantal sprints aan met doelen
        Sprint sprint1 = new Sprint("Sprint 1", new Date(), new Date(), scrumMaster, new ReviewSprintStrategy(), project);
        Sprint sprint2 = new Sprint("Sprint 2", new Date(), new Date(), scrumMaster, new ReviewSprintStrategy(), project);
        
        // Stel doelen in voor de sprints
        sprint1.setReviewSummery("Sprint 1 Goal: Complete feature A");
        sprint2.setReviewSummery("Sprint 2 Goal: Implement user stories for B");

        // Voeg de sprints toe aan het project
        project.setSprints(sprint1);
        project.setSprints(sprint2);

        // Act: Haal alle sprints binnen het project op
        List<Sprint> allSprintsInProject = project.getSprints();
        
        // Assert: Controleer of de juiste doelen worden weergegeven
        assertEquals(2, allSprintsInProject.size(), "The project should have 2 sprints.");
        assertTrue(allSprintsInProject.stream().anyMatch(sprint -> sprint.getReviewSummery().equals("Sprint 1 Goal: Complete feature A")), "Sprint 1 goal should be displayed.");
        assertTrue(allSprintsInProject.stream().anyMatch(sprint -> sprint.getReviewSummery().equals("Sprint 2 Goal: Implement user stories for B")), "Sprint 2 goal should be displayed.");
    }


}
