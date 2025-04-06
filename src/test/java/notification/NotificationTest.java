package notification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import domain.backlogitem.interfaces.IBacklogItemState;
import domain.backlogitem.models.Activity;
import domain.backlogitem.models.BacklogItem;
import domain.backlogitem.models.StateNotifier;
import domain.backlogitem.models.states.DoingState;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import domain.backlogitem.models.states.ReadyForTestingState;
import domain.common.enums.UserRole;
import domain.common.models.Backlog;
import domain.common.models.Project;
import domain.common.models.User;
import domain.notification.models.NotificationService;
import domain.sprint.Sprint;
import domain.sprint.strategies.ReleaseSprintStrategy;
import infrastructure.adapters.notifications.EmailAdapter;
import infrastructure.adapters.notifications.SlackAdapter;

public class NotificationTest {

    private User productOwner;
    private Project project;
    private BacklogItem backlogItem;
    private Backlog backlog;
    private User scrumMaster;
    private User developer;
    private User anotherDeveloper;
    private User tester;
    private Activity activity;
    private NotificationService mocked_notificationService;
    private StateNotifier stateNotifier;

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    private Sprint sprint;
    private ReleaseSprintStrategy ReleaseSprintStrategy;

    @BeforeEach
    void setUp() {
        // Initializeer de benodigde objecten
        mocked_notificationService = mock(NotificationService.class);
        stateNotifier = new StateNotifier(mocked_notificationService);
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

    //TC-19 De notificatie bevat juiste gegevens
    @Test
    void testNotificationContent_ShouldContainCorrectDetails() {
        // Arrange
        IBacklogItemState newState = new ReadyForTestingState(); // Assuming DoingState is a valid state
        backlogItem.setAssignedTo(developer, developer);
        backlogItem.setStatus(developer, newState);
    
        // Mock the notification service behavior for a void method
        doNothing().when(mocked_notificationService).sendToMultipleUsers(any(User[].class), anyString(), anyString());
    
        // Act
        stateNotifier.Update(backlogItem);
    
        // Capture the arguments passed to sendToMultipleUsers
        ArgumentCaptor<User[]> testersCaptor = ArgumentCaptor.forClass(User[].class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);
    
        verify(mocked_notificationService).sendToMultipleUsers(testersCaptor.capture(), subjectCaptor.capture(), bodyCaptor.capture());
    
        // Assert
        User[] capturedTesters = testersCaptor.getValue();
        String capturedSubject = subjectCaptor.getValue();
        String capturedBody = bodyCaptor.getValue();
    
        assertNotNull(capturedTesters);
        assertTrue(List.of(capturedTesters).contains(tester)); // Ensure the tester is included
        assertEquals("Backlog Item Ready for Testing", capturedSubject); // Replace with the expected subject
        assertTrue(capturedBody.contains(backlogItem.getAssignedTo().getUsername())); // Replace with expected body content
        assertTrue(capturedBody.contains(backlogItem.getTitle())); // Replace with expected body content
    }
    
    //notificatie bevat geen username should fail
    @Test
    void testNotificationContent_ShouldNotContainUsername() {
        // Arrange
        IBacklogItemState newState = new ReadyForTestingState(); // Assuming DoingState is a valid state
        backlogItem.setAssignedTo(developer, developer);
        backlogItem.setStatus(developer, newState);
    
        // Mock the notification service behavior for a void method
        doNothing().when(mocked_notificationService).sendToMultipleUsers(any(User[].class), anyString(), anyString());
    
        // Act
        stateNotifier.Update(backlogItem);
    
        // Capture the arguments passed to sendToMultipleUsers
        ArgumentCaptor<User[]> testersCaptor = ArgumentCaptor.forClass(User[].class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);
    
        verify(mocked_notificationService).sendToMultipleUsers(testersCaptor.capture(), subjectCaptor.capture(), bodyCaptor.capture());
    
        // Assert
        User[] capturedTesters = testersCaptor.getValue();
        String capturedSubject = subjectCaptor.getValue();
        String capturedBody = bodyCaptor.getValue();
    
        assertNotNull(capturedTesters);
        assertTrue(List.of(capturedTesters).contains(tester)); // Ensure the tester is included
        assertEquals("Backlog Item Ready for Testing", capturedSubject); // Replace with the expected subject
        assertNotEquals(backlogItem.getAssignedTo().getUsername(), capturedBody); // Replace with expected body content
    }
    
    @AfterEach
    void tearDown() {
        // Reset System.out naar de originele waarde
        System.setOut(originalOut);
    }
}
