package scm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.backlogitem.models.BacklogItem;
import domain.common.enums.UserRole;
import domain.common.models.Backlog;
import domain.common.models.Project;
import domain.common.models.User;
import domain.scm.interfaces.ISCMAdapter;
import domain.scm.models.Branch;
import domain.scm.models.Repository;
import domain.scm.models.Commit;
import infrastructure.adapters.notifications.EmailAdapter;

public class SCMTest {

    private Project project;
    private User productOwner;
    private User developer;
    private Backlog backlog;
    private BacklogItem backlogItem;
    private ISCMAdapter mocked_scmAdapter;

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        productOwner = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER,
                new EmailAdapter());
        developer = new User("dev1", "123", "dev1@example.com", "slack2", UserRole.DEVELOPER, new EmailAdapter());
        project = new Project(1, "Project Alpha", "Description of project alpha", productOwner, null);
        backlog = new Backlog(1, project); // Now pass the fully initialized project
        backlogItem = new BacklogItem(1, "Backlog Item 1", "Description of backlog item 1", developer, backlog);
        project.setBacklog(backlog); // Associate backlog with the project
        mocked_scmAdapter = mock(ISCMAdapter.class);

        // Initialiseer de ByteArrayOutputStream en PrintStream
        outContent = new ByteArrayOutputStream();
        originalOut = System.out; // Bewaar de originele System.out
        System.setOut(new PrintStream(outContent)); // Zet System.out naar de ByteArrayOutputStream

    }

    void resetOutput() {
        outContent.reset(); // Reset de inhoud van de output stream
    }

    // TC-40 Een repository kan worden aangemaakt
    @Test
    void testCreateRepository_ShouldCallSCM() {
        // Arrange
        String repoName = "TestRepo";
        doNothing().when(mocked_scmAdapter).createRepo(repoName);

        // Act
        project.addRepository(repoName, mocked_scmAdapter);

        // Assert
        verify(mocked_scmAdapter).createRepo(repoName);
        assertTrue(project.getRepositories().size() > 0, "Repository should not be null after creation.");
    }

    // repo ophalen bij naaam vanuit project.get
    @Test
    void testCreateRepository_ShouldbeBeFoundInProject() {
        // Arrange
        String repoName = "TestRepo";
        doNothing().when(mocked_scmAdapter).createRepo(repoName);

        // Act
        project.addRepository(repoName, mocked_scmAdapter);

        // Assert
        assertEquals(repoName, project.getRepositoryByName(repoName).getName(),
                "Repository name should match the created repository.");
    }

    // TC-41 Een branch kan worden aangemaakt
    @Test
    void testCreateBranch_ShouldCallSCM() {
        // Arrange
        String repoName = "TestRepo";
        String branchName = "TestBranch";
        Repository repository = new Repository(repoName, mocked_scmAdapter);
        Branch branch = new Branch(branchName, repository, mocked_scmAdapter);
        doNothing().when(mocked_scmAdapter).createBranch(any(), any());

        // Act
        repository.addBranch(branch);

        // Assert
        verify(mocked_scmAdapter).createBranch(branch, repository);
    }

    // TC-42 Een commit kan worden aangemaakt
    @Test
    void testCreateCommit_ShouldCallSCM() {
        // Arrange
        String repoName = "TestRepo";
        String branchName = "TestBranch";
        Repository repository = new Repository(repoName, mocked_scmAdapter);
        Branch branch = new Branch(branchName, repository, mocked_scmAdapter);
        Commit commit = new Commit("Initial commit", backlogItem, developer);
        doNothing().when(mocked_scmAdapter).createCommit(any(), any(), any());

        // Act
        branch.addCommit(commit);

        // Assert
        verify(mocked_scmAdapter).createCommit(commit, branch, repository);
    }

    // TC-48 Een commit kan worden geassocieerd met een backlog item
    @Test
    void testCommitToBacklogItem_ShouldAssociateCommitWithBacklogItem() {
        // Arrange
        String repoName = "TestRepo";
        String branchName = "TestBranch";
        Repository repository = new Repository(repoName, mocked_scmAdapter);
        Branch branch = new Branch(branchName, repository, mocked_scmAdapter);
        Commit commit = new Commit("Initial commit", backlogItem, developer);
        doNothing().when(mocked_scmAdapter).createCommit(any(), any(), any());

        // Act
        branch.addCommit(commit);
        commit.setBacklogItem(backlogItem);

        // Assert
        assertEquals(backlogItem, commit.getBacklogItem());
    }

    @Test
    void testCommitToBacklogItem_ShouldAssociateBacklogItemWithCommit() {
        // Arrange
        String repoName = "TestRepo";
        String branchName = "TestBranch";
        Repository repository = new Repository(repoName, mocked_scmAdapter);
        Branch branch = new Branch(branchName, repository, mocked_scmAdapter);
        Commit commit = new Commit("Initial commit", backlogItem, developer);
        doNothing().when(mocked_scmAdapter).createCommit(any(), any(), any());

        // Act
        branch.addCommit(commit);
        commit.setBacklogItem(backlogItem);

        // Assert
        assertTrue(backlogItem.getCommits().size() > 0, "Backlog item should contain the commit.");
    }
    // TC-49 Alle commits van een backlogItem kunnen worden weergeven
    @Test
    void testPrintCommits_ShouldDisplayAllCommits() {
        // Arrange
        String repoName = "TestRepo";
        String branchName = "TestBranch";
        Repository repository = new Repository(repoName, mocked_scmAdapter);
        Branch branch = new Branch(branchName, repository, mocked_scmAdapter);
        Commit commit1 = new Commit("Initial commit", backlogItem, developer);
        Commit commit2 = new Commit("Second commit", backlogItem, developer);
        doNothing().when(mocked_scmAdapter).createCommit(any(), any(), any());

        // Act
        branch.addCommit(commit1);
        branch.addCommit(commit2);
        branch.printCommits();

        // Assert
        assertTrue(outContent.toString().contains("Initial commit"));
        assertTrue(outContent.toString().contains("Second commit"));
    }
    
    @AfterEach
    void tearDown() {
        // Reset System.out naar de originele waarde
        System.setOut(originalOut);
    }
}
