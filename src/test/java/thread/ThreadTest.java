package thread;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.backlogitem.interfaces.IBacklogItemState;
import domain.backlogitem.models.Activity;
import domain.backlogitem.models.BacklogItem;
import domain.backlogitem.models.states.DoingState;
import domain.backlogitem.models.states.FinalizedState;
import domain.backlogitem.models.states.ReadyForTestingState;
import domain.common.enums.UserRole;
import domain.common.models.Backlog;
import domain.common.models.Project;
import domain.thread.models.Comment;
import domain.thread.models.Thread;
import domain.common.models.User;
import domain.sprint.Sprint;
import domain.sprint.strategies.ReleaseSprintStrategy;
import infrastructure.adapters.notifications.EmailAdapter;
import infrastructure.adapters.notifications.SlackAdapter;

public class ThreadTest {
    
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

    //TC-29 Er kan een discussie worden gestart en gekoppeld aan een backlog item.
    @Test
    void testStartDiscussion_ShouldLinkToBacklogItem() {
        // Arrange
        Thread thread = new Thread("Thread 1", "Description 1", backlogItem, developer);

        // Act
        backlogItem.addThread(thread); // Koppel de discussie aan het backlog item

        // Assert
        assertEquals(thread, backlogItem.getThreadByTitle("Thread 1"), "De discussie moet correct gekoppeld zijn aan het backlog item.");
    }

    //TC-30 Een discussiepunt kan worden aangepast door de maker.
    @Test
    void testEditThread_ShouldUpdateTitleAndDescription() {
        // Arrange
        Thread thread = new Thread("Thread 1", "Description 1", backlogItem, developer);
        String newTitle = "Updated Thread Title";
        String newDescription = "Updated description of the thread";

        // Act
        thread.setTitle(newTitle, developer); // Pas de titel aan
        thread.setDescription(newDescription, developer); // Pas de beschrijving aan

        // Assert
        assertEquals(newTitle, thread.getTitle(), "De titel van de discussie moet zijn bijgewerkt.");
        assertEquals(newDescription, thread.getDescription(), "De beschrijving van de discussie moet zijn bijgewerkt.");
    }
    @Test
    void testEditThread_ShouldNotUpdateTitle() {
        // Arrange
        Thread thread = new Thread("Thread 1", "Description 1", backlogItem, developer);
        String newTitle = "Updated Thread Title";

        // Act
        thread.setTitle(newTitle, anotherDeveloper); // Pas de titel aan

        // Assert
        assertNotEquals("Updated Thread Title", thread.getTitle(), "De titel van de discussie mag niet bijgewerkt worden door een andere gebruiker.");
    }

    @Test
    void testEditThread_ShouldNotUpdateDescription() {
        // Arrange
        Thread thread = new Thread("Thread 1", "Description 1", backlogItem, developer);
        String newDescription = "Updated description of the thread";

        // Act
        thread.setDescription(newDescription, anotherDeveloper); // Pas de beschrijving aan

        // Assert
        assertNotEquals("Updated description of the thread", thread.getDescription(), "De beschrijving van de discussie mag niet bijgewerkt worden door een andere gebruiker.");
    }
    //TC-31 Een discussiepunt heeft een titel en een beschrijving en heeft een eigenaar.
    @Test
    void testThread_ShouldHaveTitleDescriptionAndOwner() {
        // Arrange
        String title = "Thread 1";
        String description = "Description of thread 1";
        Thread thread = new Thread(title, description, backlogItem, developer);

        // Act & Assert
        assertEquals(title, thread.getTitle(), "De titel van de discussie moet correct zijn.");
        assertEquals(description, thread.getDescription(), "De beschrijving van de discussie moet correct zijn.");
        assertEquals(developer, thread.getOwner(), "De eigenaar van de discussie moet correct zijn.");
    }

    //TC-32 Een ander kan een reactie plaatsen op een discussiepunt. 
    @Test
    void testAddComment_ShouldAddCommentToThread() {
        // Arrange
        Thread thread = new Thread("Thread 1", "Description of thread 1", backlogItem, developer);
        String commentText = "This is a comment";
        String date = "2023-10-01";

        // Act
        thread.addChild(new Comment(commentText, anotherDeveloper, date)); // Voeg een reactie toe aan de discussie

        // Assert
        assertEquals(1, thread.getChildren().size(), "De discussie moet 1 reactie bevatten.");
        assertEquals(commentText, ((Comment) thread.getChildren().get(0)).getText(), "De tekst van de reactie moet overeenkomen.");
    }

    //TC-33 Een reactie kan worden aangepast door de maker.
    @Test
    void testEditComment_ShouldUpdateCommentText() {
        // Arrange
        Thread thread = new Thread("Thread 1", "Description of thread 1", backlogItem, developer);
        String commentText = "This is a comment";
        String date = "2023-10-01";
        Comment comment = new Comment(commentText, anotherDeveloper, date);
        thread.addChild(comment); // Voeg een reactie toe aan de discussie

        String newCommentText = "Updated comment text";

        // Act
        comment.setText(newCommentText, anotherDeveloper); // Pas de tekst van de reactie aan

        // Assert
        assertEquals(newCommentText, comment.getText(), "De tekst van de reactie moet zijn bijgewerkt.");
    }
    @Test
    void testEditComment_ShouldNotUpdateCommentText() {
        // Arrange
        Thread thread = new Thread("Thread 1", "Description of thread 1", backlogItem, developer);
        String commentText = "This is a comment";
        String date = "2023-10-01";
        Comment comment = new Comment(commentText, anotherDeveloper, date);
        thread.addChild(comment); // Voeg een reactie toe aan de discussie

        String newCommentText = "Updated comment text";

        // Act
        comment.setText(newCommentText, developer); // Probeer de tekst van de reactie aan te passen door een andere gebruiker

        // Assert
        assertNotEquals(newCommentText, comment.getText(), "De tekst van de reactie mag niet worden bijgewerkt door een andere gebruiker.");
    }
    //TC-34 Een discussiepunt/reactie aanpassen of toevoegen wanneer het gekoppelde backlog item op done staat.
    @Test
    void testAddComment_ShouldNotAddCommentWhenBacklogItemIsFinilized() {
        // Arrange
        backlogItem.setCurrentState(new FinalizedState()); // Zet de status van het backlog item op "Ready for Testing"
        Thread thread = new Thread("Thread 1", "Description of thread 1", backlogItem, developer);
        String commentText = "This is a comment";
        String date = "2023-10-01";

        // Act
        thread.addChild(new Comment(commentText, anotherDeveloper, date)); // Probeer een reactie toe te voegen

        // Assert
        assertEquals(0, thread.getChildren().size(), "Er mogen geen reacties worden toegevoegd aan een voltooid backlog item.");
    }
    //TC-35 Er wordt een nieuwe reactie toegevoegd aan een lopende discussie.
    @Test
    void testAddComment_ShouldAddCommentToOngoingThread() {
        // Arrange
        Thread thread = new Thread("Thread 1", "Description of thread 1", backlogItem, developer);
        thread.addChild(new Comment("Comment", developer, "2023-10-01"));
        thread.addChild(new Comment("Comment 2", developer, "2023-11-01"));
        String commentText = "This is a comment";
        String date = "2023-15-01";

        // Act
        thread.addChild(new Comment(commentText, anotherDeveloper, date));

        // Assert
        int threadSize = thread.getChildren().size();
        assertEquals(3, threadSize, "De discussie moet 1 reactie bevatten.");
        assertEquals(commentText, ((Comment) thread.getChildren().get(threadSize - 1)).getText(), "De tekst van de reactie moet overeenkomen.");
    }

    @AfterEach
    void tearDown() {
        // Reset System.out naar de originele waarde
        System.setOut(originalOut);
    }
}
