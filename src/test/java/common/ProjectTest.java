package common;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.common.models.Project;
import domain.common.models.User;
import domain.common.models.Backlog;
import infrastructure.adapters.notifications.EmailAdapter;
import domain.common.enums.UserRole;

class ProjectTest {

    private Project project;
    private User productOwner;
    private Backlog backlog;

    @BeforeEach
    void setUp() {
        productOwner = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER, new EmailAdapter());
        project = new Project(1, "Project Alpha", "Description of project alpha", productOwner, null); // Pass null for backlog initially
        backlog = new Backlog(1, project); // Now pass the fully initialized project
        project.setBacklog(backlog); // Associate backlog with the project
    }
    

    // TC-01: Aanmaken van een nieuw project met geldige invoer
    @Test
    void testCreateProjectWithValidInput() {
        // Arrange: Een geldig project wordt al aangemaakt in de setUp-methode.

        // Act: De projectgegevens worden gecontroleerd tijdens de asserties.

        // Assert: Verifieer of het project correct is aangemaakt
        assertNotNull(project);
        assertEquals(1, project.getId());
        assertEquals("Project Alpha", project.getProjectName());
        assertEquals("Description of project alpha", project.getDescription());
        assertEquals(productOwner, project.getProductOwner());
    }

    // TC-02: Aanmaken van een project met ongeldige invoer
    @Test
    void testCreateProjectWithInvalidInput() {
        // Arrange: Test met een null projectnaam
        Project invalidProject = new Project(1, null, "Description", productOwner, backlog);

        // Act: De projectnaam wordt gecontroleerd.

        // Assert: Verifieer dat de projectnaam null is wanneer deze niet geldig is
        assertNull(invalidProject.getProjectName());

        // Arrange: Test met een lege projectnaam
        invalidProject = new Project(1, "", "Description", productOwner, backlog);

        // Act: De projectnaam wordt gecontroleerd.

        // Assert: Verifieer dat de projectnaam leeg is
        assertEquals("", invalidProject.getProjectName());
    }

    // TC-03: Wijzigingen in projectgegevens
    @Test
    void testUpdateProjectDetails() {
        // Arrange: De projectgegevens zijn al ingesteld tijdens de setUp-methode.

        // Act: Wijzig de projectnaam en beschrijving.
        project.setProjectName("Updated Project Alpha");
        project.setDescription("Updated description");

        // Assert: Controleer of de wijzigingen correct zijn doorgevoerd.
        assertEquals("Updated Project Alpha", project.getProjectName());
        assertEquals("Updated description", project.getDescription());
    }

    // TC-04: Toevoegen van een nieuw teamlid aan een project
    @Test
    void testAddTeamMember() {
        // Arrange: Een nieuw teamlid wordt aangemaakt.
        User newMember = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.TESTER, new EmailAdapter());

        // Act: Voeg het nieuwe teamlid toe aan het project.
        project.addTeamMembers(newMember);

        // Assert: Verifieer dat het teamlid correct is toegevoegd aan het project.
        assertTrue(project.getTeamMembers().contains(newMember));
    }
}
