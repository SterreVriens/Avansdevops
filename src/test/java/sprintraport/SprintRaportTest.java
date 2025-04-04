package sprintraport;

import domain.common.enums.UserRole;
import domain.common.models.Backlog;
import domain.common.models.Project;
import domain.common.models.User;
import domain.sprint.Sprint;
import domain.sprint.states.raportedSprintState;
import domain.sprint.strategies.ReviewSprintStrategy;
import infrastructure.adapters.notifications.EmailAdapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SprintRaportTest {

    private User scrumMaster;
    private Project project;
    private Sprint sprint;
    private Backlog backlog;
    private User productOwner;

    @BeforeEach
    void setup() {
        // Arrange
        productOwner = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER, new EmailAdapter());
        scrumMaster = new User("scrum", "123", "scrum@example.com", "slack1", UserRole.SCRUMMASTER, new EmailAdapter());
        project = new Project(1, "Project Alpha", "Description of project alpha", productOwner, null);
        backlog = new Backlog(1, project);
        project.setBacklog(backlog);

        sprint = new Sprint("Sprint 1", new Date(), new Date(), scrumMaster, new ReviewSprintStrategy(), project);
        sprint.setState(new raportedSprintState(sprint)); // Simuleer afgeronde sprint
    }

     //TC-36 Positive case: Raport genereren na afgeronde sprint
    @Test
    void testGenerateRaportAfterSprintIsFinished() {
        // Arrange
        sprint.setState(new raportedSprintState(sprint));

        // Act & Assert
        assertDoesNotThrow(() -> sprint.generateRaport(), "Rapport moet gegenereerd worden zonder fouten.");
    }

    @Test
    void testGenerateRaportWithoutFinishedSprint_ShouldFail() {
        // Act & Assert
        assertDoesNotThrow(() -> sprint.generateRaport(), "Moet foutmelding tonen maar geen exception gooien.");
        // Je zou hier ook kunnen controleren op console output of mocking gebruiken
    }

    // TC-37 Parameterized test: verschillende headers/footers/inhoud instellen
    @ParameterizedTest
    @CsvSource({
        "Header A, Inhoud A, Footer A",
        "Sprint Rapport, Inhoud B, Scrum Team",
        "'', '', ''"
    })
    void testModifyRaportData_MultipleCases(String header, String content, String footer) {
        // Arrange
        sprint.setState(new raportedSprintState(sprint));
        sprint.generateRaport();

        // Act & Assert
        assertDoesNotThrow(() -> sprint.modifyRaport(header, content, footer));
    }

    //TC-38 Export als PDF
    @Test
    void testExportRaportAsPDF() {
        // Arrange
        sprint.setState(new raportedSprintState(sprint));
        sprint.generateRaport();

        // Act & Assert
        assertDoesNotThrow(() -> sprint.exportRaportAsPDF());
    }

    //TC-39 Export als PNG
    @Test
    void testExportRaportAsPNG() {
        // Arrange
        sprint.setState(new raportedSprintState(sprint));
        sprint.generateRaport();

        // Act & Assert
        assertDoesNotThrow(() -> sprint.exportRaportAsPNG());
    }

    @Test
    void testExportWithoutGeneratingRaport_ShouldFailGracefully() {
        // Act & Assert
        assertDoesNotThrow(() -> sprint.exportRaportAsPDF());
        assertDoesNotThrow(() -> sprint.exportRaportAsPNG());
    }

    @Test
    void testModifyRaportWithoutGenerating_ShouldFailGracefully() {
        // Act & Assert
        assertDoesNotThrow(() -> sprint.modifyRaport("H", "C", "F"));
    }
}

