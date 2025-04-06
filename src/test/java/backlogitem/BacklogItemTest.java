package backlogitem;

import domain.backlogitem.interfaces.IBacklogItemState;
import domain.backlogitem.models.Activity;
import domain.backlogitem.models.BacklogItem;
import domain.backlogitem.models.states.DoingState;
import domain.backlogitem.models.states.ReadyForTestingState;
import domain.common.enums.UserRole;
import domain.common.models.Backlog;
import domain.common.models.Project;
import domain.common.models.User;
import domain.sprint.Sprint;
import domain.sprint.strategies.ReleaseSprintStrategy;
import infrastructure.adapters.notifications.EmailAdapter;
import infrastructure.adapters.notifications.SlackAdapter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class BacklogItemAssignmentTest {

    private User productOwner;
    private Project project;
    private BacklogItem backlogItem;
    private Backlog backlog;
    private User scrumMaster;
    private User developer;
    private User anotherDeveloper;
    private User tester;
    private Activity activity;

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    private Sprint sprint;
    private ReleaseSprintStrategy ReleaseSprintStrategy;

    @BeforeEach
    void setUp() {
        // Initializeer de benodigde objecten
        productOwner = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER, new EmailAdapter());
        project = new Project(1, "Project Alpha", "Description of project alpha", productOwner, null);
        backlog = new Backlog(1, project);
        project.setBacklog(backlog);

        scrumMaster = new User("scrum", "123", "scrum@example.com", "slack1", UserRole.SCRUMMASTER, new EmailAdapter());
        developer = new User("dev1", "123", "dev1@example.com", "slack2", UserRole.DEVELOPER, new EmailAdapter());
        anotherDeveloper = new User("dev2", "123", "dev2@example.com", "slack3", UserRole.DEVELOPER, new EmailAdapter());
        tester = new User("tester", "123", "tester@example.com", "slack4", UserRole.TESTER, new SlackAdapter());

        sprint = new Sprint("Sprint 1", null, null, scrumMaster, ReleaseSprintStrategy, project); // Maak de sprint aan
        backlogItem = new BacklogItem(1, "Item A", "Description", null, backlog);

        activity = new Activity("Activity 1", "Description of activity 1");
        backlogItem.addActivity(activity); // Voeg een activiteit toe aan het backlog item

        backlogItem.setSprint(sprint);  // Stel de sprint in voor het backlog item
        project.addTeamMembers(tester);

        // Initialiseer de ByteArrayOutputStream en PrintStream
        outContent = new ByteArrayOutputStream();
        originalOut = System.out; // Bewaar de originele System.out
        System.setOut(new PrintStream(outContent)); // Zet System.out naar de ByteArrayOutputStream
    }

    void resetOutput() {
        outContent.reset(); // Reset de inhoud van de output stream
    }
    //TC-05 Backlog item aanmaken met correcte gegevens
    @Test
    void testCreateBacklogItem_ShouldStoreCorrectly() {
        // Act
        backlogItem = new BacklogItem(2, "Item A", "Description", null, backlog); 

        // Assert
        assertEquals("Item A", backlog.getBacklogItemById(2).getTitle());
    }

    //TC-06 Backlog item bewerken
    @Test
    void testEditBacklogItem_ShouldEditSuccessfully() {
        // Arrange
        String newTitle = "Updated Title";
        String newDescription = "Updated Description";

        // Act
        backlogItem.setTitle(newTitle);
        backlogItem.setDescription(newDescription);

        // Assert
        assertEquals(newTitle, backlogItem.getTitle());
        assertEquals(newDescription, backlogItem.getDescription());
    }

    //TC-07 Backlog item verwijderen
    @Test
    void testDeleteBacklogItem_ShouldDeleteSuccessfully() {
        // Arrange
        BacklogItem backlogItemToDelete = new BacklogItem(3, "Item B", "Description", null, backlog);

        // Act
        backlog.removeBacklogItem(3);

        // Assert
        assertNull(backlog.getBacklogItemById(3)); // Controleer of het item is verwijderd
    }

    //TC-08 Subtaak toevoegen aan een backlog item.
    @Test
    void testAddSubtask_ShouldAddSuccessfully() {
        // Arrange
        Activity subtask = new Activity("Subtask 1", "Description of subtask 1");

        // Act
        backlogItem.addActivity(subtask);

        // Assert
        assertTrue(backlogItem.getActivities().contains(subtask)); // Controleer of de subtaak is toegevoegd
    }

    //TC-09 De status van een subtaak veranderen.
    @Test
    void testChangeSubtaskStatus_ShouldChangeSuccessfully() {
        // Arrange
        Activity subtask = new Activity("Subtask 1", "Description of subtask 1");
        backlogItem.addActivity(subtask);

        // Act
        subtask.setDone(true); // Stel de status van de subtaak in op 'done'

        // Assert
        assertTrue(subtask.isDone()); // Controleer of de status is veranderd naar 'done'
    }

    //TC-10 Scrum master wijst zichzelf toe aan een backlog item.
    @Test
    void testAssignScrumMasterBySelf_ShouldAssignSuccessfully() {
        // Act
        backlogItem.setAssignedTo(scrumMaster, scrumMaster);

        // Assert
        assertEquals(scrumMaster, backlogItem.getAssignedTo());
        assertEquals("", outContent.toString().trim());
    }

    @Test
    void testAssignScrumMasterByOtherDeveloper_ShouldFail() {
        // Act
        backlogItem.setAssignedTo(scrumMaster, developer);

        // Assert
        assertNull(backlogItem.getAssignedTo());
        assertTrue(outContent.toString().contains("Warning: You are not allowed to assign this backlog item"));
    }

    //TC-11 Developer probeert zichzelf toe te wijzen aan een backlog item.
    @Test
    void testAssignDeveloperBySelf_ShouldAssignSuccessfully() {
        backlogItem.setAssignedTo(developer, developer);

        assertEquals(developer, backlogItem.getAssignedTo());
        assertEquals("", outContent.toString().trim()); // Geen waarschuwingen verwacht
    }

    //TC-12 Er wordt geprobeerd om een tweede developer toe te wijzen aan een backlog item.
    @Test
    void testAssignWhenAlreadyAssigned_ShouldShowAlreadyAssignedWarning() {
        // Arrange
        backlogItem.setAssignedTo(scrumMaster, scrumMaster);
        resetOutput();

        // Act: probeer een tweede keer
        backlogItem.setAssignedTo(anotherDeveloper, anotherDeveloper);

        // Assert
        assertEquals(scrumMaster, backlogItem.getAssignedTo());
        assertTrue(outContent.toString().contains("already assigned to scrum"));
    }

    //TC-13 De toegewezen developer wijzigt de status van een backlog item.
    @Test
    void testAssignedDeveloperChangesStatus_ShouldChangeStatusSuccessfully() {
        // Arrange
        IBacklogItemState newState = new DoingState(); // Assuming DoingState is a valid state
        backlogItem.setAssignedTo(developer, developer);

        // Act
        backlogItem.setStatus(developer, newState);

        // Assert
        assertEquals(newState, backlogItem.getCurrentState());
    }

    //TC-14 Een niet toegewezen developer wijzigt de status van een backlog item.
    @Test
    void testNonAssignedDeveloperChangesStatus_ShouldFailWithWarning() {
        // Arrange
        IBacklogItemState newState = new DoingState(); // Assuming DoingState is a valid state
        backlogItem.setAssignedTo(developer, developer);

        // Act
        backlogItem.setStatus(anotherDeveloper, newState);

        // Assert
        assertNotEquals(newState, backlogItem.getCurrentState()); // Status moet niet veranderd zijn
        assertTrue(outContent.toString().contains("Only the assigned developer can change the status of this backlog item"));
    }

    @Test
    void testNoAssignedDeveloperChangeStatus_ShouldFaile(){
        // Arrange
        IBacklogItemState newState = new DoingState(); // Assuming DoingState is a valid state

        // Act
        backlogItem.setStatus(developer, newState);

        // Assert
        assertNotEquals(newState, backlogItem.getCurrentState()); // Status moet niet veranderd zijn
        assertTrue(outContent.toString().contains("Warning: No developer is assigned to this backlog item."));
    }

    @Test
    void testAssignWithNullAssignedTo_ShouldFailSilently() {
        // Act
        backlogItem.setAssignedTo(null, scrumMaster);

        // Assert
        assertNull(backlogItem.getAssignedTo());
        assertTrue(outContent.toString().contains("You are not allowed to assign this backlog item"));
    }

    @Test
    void testAssignWithNullAssigner_ShouldFailSilently() {
        // Act
        backlogItem.setAssignedTo(scrumMaster, null);

        // Assert
        assertNull(backlogItem.getAssignedTo());
        assertTrue(outContent.toString().contains("You are not allowed to assign this backlog item"));
    }

    //TC-17 De status van een backlog item komt in ready for testing.
    @Test
    void testStatusReadyForTesting_ShouldChangeSuccessfully() {
        // Arrange
        IBacklogItemState newState = new ReadyForTestingState(); // Assuming DoingState is a valid state
        backlogItem.setAssignedTo(developer, developer);

        // Act
        backlogItem.setStatus(developer, newState);

        // Assert
        assertEquals(newState, backlogItem.getCurrentState());
        assertTrue(outContent.toString().contains("Backlog Item Ready for Testing"));
    }

    @AfterEach
    void tearDown() {
        // Reset System.out naar de originele waarde
        System.setOut(originalOut);
    }
}
