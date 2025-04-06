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
import infrastructure.adapters.scm.GitAdapter;
import infrastructure.libs.scm.GitLibrary;

public class SCMTest {

    private Project project;
    private User productOwner;
    private User developer;
    private Backlog backlog;
    private BacklogItem backlogItem;
    private ISCMAdapter mocked_scmAdapter;

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private GitAdapter gitAdapter;
    private GitLibrary gitLibraryMock;
    private User author;
    private Repository repository;
    private Branch branch;
    private Commit commit;
    

    @BeforeEach
void setUp() {
    // Setup zoals al aanwezig
    productOwner = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER, new EmailAdapter());
    developer = new User("dev1", "123", "dev1@example.com", "slack2", UserRole.DEVELOPER, new EmailAdapter());
    project = new Project(1, "Project Alpha", "Description of project alpha", productOwner, null);
    backlog = new Backlog(1, project);
    backlogItem = new BacklogItem(1, "Backlog Item 1", "Description of backlog item 1", developer, backlog);
    project.setBacklog(backlog);
    mocked_scmAdapter = mock(ISCMAdapter.class);

    // Output opvangen
    outContent = new ByteArrayOutputStream();
    originalOut = System.out;
    System.setOut(new PrintStream(outContent));

    // ✅ Extra: Setup voor concrete GitAdapter tests
    gitLibraryMock = mock(GitLibrary.class);
    gitAdapter = new GitAdapter() {
        {
            this.gitLibrary = gitLibraryMock; // Inject de mock GitLibrary
        }
    };

    // ✅ Dummy data voor GitAdapter tests
    author = developer;
    repository = new Repository("Repo1", gitAdapter);
    branch = new Branch("main", repository, gitAdapter);
    commit = new Commit("Initial commit", backlogItem, author);
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

    @Test
    void testCreateBranch_ShouldCallGitLibrary() {
        gitAdapter.createBranch(branch, repository);
        verify(gitLibraryMock).createBranch("main", "Repo1");
    }

    @Test
    void testCreateCommit_ShouldCallGitLibrary() {
        gitAdapter.createCommit(commit, branch, repository);
        verify(gitLibraryMock).commit("Initial commit", "dev1", "main", "Repo1");
    }

    @Test
    void testGitLibraryCommit_ShouldPrintCommitDetails() {
        // Arrange
        GitLibrary gitLibrary = new GitLibrary();
        String message = "Test commit message";
        String authorName = "dev1";
        String branch = "main";
        String repoName = "Repo1";
        
        // Act
        gitLibrary.commit(message, authorName, branch, repoName);

        // Assert - Controleren of een deel van de output aanwezig is
        assertTrue(outContent.toString().contains("Committing changes with message: " + message), 
            "Expected commit message to be printed.");
        resetOutput();
    }

    @Test
    void testGitLibraryCreateBranch_ShouldPrintBranchDetails() {
        // Arrange
        GitLibrary gitLibrary = new GitLibrary(); // Using real GitLibrary instance
        String branchName = "TestBranch";
        String repoName = "Repo1";
        
        // Act
        gitLibrary.createBranch(branchName, repoName);

        // Assert - Controleren of een deel van de output aanwezig is
        assertTrue(outContent.toString().contains("Creating branch: " + branchName), 
            "Expected branch creation message to be printed.");
        resetOutput();
    }

    @Test
    void testGitLibraryCreateRepo_ShouldPrintRepoDetails() {
        // Arrange
        GitLibrary gitLibrary = new GitLibrary(); // Using real GitLibrary instance
        String repoName = "TestRepo";
        
        // Act
        gitLibrary.createRepo(repoName);
    
        // Assert - Controleren of een deel van de output aanwezig is
        assertTrue(outContent.toString().contains("Creating repository: " + repoName), 
            "Expected repository creation message to be printed.");
        resetOutput();
    }

    @AfterEach
    void tearDown() {
        // Reset System.out naar de originele waarde
        System.setOut(originalOut);
    }
}
