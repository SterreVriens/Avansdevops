package backlogitem;

import domain.backlogitem.interfaces.IBacklogItemState;
import domain.backlogitem.models.BacklogItem;
import domain.backlogitem.models.states.DoingState;
import domain.common.enums.UserRole;
import domain.common.models.Backlog;
import domain.common.models.Project;
import domain.common.models.User;
import domain.sprint.states.StartedSprintState;
import infrastructure.adapters.notifications.EmailAdapter;

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

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture output
        // This is necessary to test the output of System.out.println
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        productOwner = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER, new EmailAdapter());
        project = new Project(1, "Project Alpha", "Description of project alpha", productOwner, null);
        backlog = new Backlog(1, project);
        project.setBacklog(backlog);

        scrumMaster = new User("scrum", "123", "scrum@example.com", "slack1", UserRole.SCRUMMASTER, new EmailAdapter());
        developer = new User("dev1", "123", "dev1@example.com", "slack2", UserRole.DEVELOPER, new EmailAdapter());
        anotherDeveloper = new User("dev2", "123", "dev2@example.com", "slack3", UserRole.DEVELOPER, new EmailAdapter());

        backlogItem = new BacklogItem(1, "Item A", "Description", null, backlog);
    }

    void resetOutput() {
        outContent.reset();
    }

    // TC-10  Scrum master wijst zichzelf toe aan een backlog item.
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

    // TC-11  Developer probeert zichzelf toe te wijzen aan een backlog item.
    @Test
    void testAssignDeveloperBySelf_ShouldAssignSuccessfully() {
        // TC-11: Developer wijst zichzelf toe aan een backlog item.
        backlogItem.setAssignedTo(developer, developer);

        assertEquals(developer, backlogItem.getAssignedTo());
        assertEquals("", outContent.toString().trim()); // Geen waarschuwingen verwacht
    }

    // TC-12 Er wordt geprobeerd om een tweede developer toe te wijzen aan een backlog item.
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

   // TC-13: De toegewezen developer wijzigt de status van een backlog item.
    @Test
    void testAssignedDeveloperChangesStatus_ShouldChangeStatusSuccessfully() {
        // Arrange
        IBacklogItemState newState = new DoingState(); // Assuming DoingState is a valid state

        // Act
        backlogItem.setStatus(developer, newState);

        // Assert
        assertEquals(newState, backlogItem.getCurrentState());
        assertTrue(outContent.toString().isEmpty()); // Geen waarschuwingen verwacht
    }

    // TC-14: Een niet toegewezen developer wijzigt de status van een backlog item.
    @Test
    void testNonAssignedDeveloperChangesStatus_ShouldFailWithWarning() {
        // Arrange
        IBacklogItemState newState = new DoingState(); // Assuming DoingState is a valid state

        // Act
        backlogItem.setStatus(anotherDeveloper, newState);

        // Assert
        assertNotEquals(newState, backlogItem.getCurrentState()); // Status moet niet veranderd zijn
        assertTrue(outContent.toString().contains("Only the assigned developer can change the status of this backlog item"));
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

    @AfterEach
    void tearDown() {
        // Reset System.out to its original state
        System.setOut(originalOut);
    }
}
