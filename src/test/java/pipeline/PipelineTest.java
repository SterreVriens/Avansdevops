package pipeline;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.common.enums.UserRole;
import domain.common.models.Project;
import domain.common.models.User;
import domain.pipeline.Pipeline;
import domain.pipeline.Step;
import domain.sprint.Sprint;
import domain.sprint.strategies.ReleaseSprintStrategy;
import infrastructure.adapters.notifications.EmailAdapter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PipelineTest {

    private Sprint sprint;
    private ByteArrayOutputStream output;

    @BeforeEach
    void setup() {
        // Arrange – Setup test pipeline & sprint context
        User productOwner = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER, new EmailAdapter());
        User scrumMaster = new User("scrum", "123", "scrum@example.com", "slack1", UserRole.SCRUMMASTER, new EmailAdapter());
        Project project = new Project(1, "Project Alpha", "Description of project alpha", productOwner, null);

        Pipeline pipeline = new Pipeline("Release Pipeline");
        pipeline.addChild(new Step("Build", "mvn clean install"));
        pipeline.addChild(new Step("Test", "mvn test"));
        pipeline.addChild(new Step("Deploy", "kubectl apply"));

        sprint = new Sprint("Initial Sprint", new Date(), new Date(), scrumMaster, new ReleaseSprintStrategy(pipeline), project);

        // Capture System.out
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    //TC-43 Pipeline correct aangemaakt
    @Test
    void testPipelineCreation() {
        // Assert – naam van de pipeline en presence van steps checken via finalize output
        sprint.finish(); // zet naar review
        sprint.finalized(); // triggert de pipeline

        String consoleOutput = output.toString();
        assertTrue(consoleOutput.contains("Starting pipeline: Release Pipeline"));
        assertTrue(consoleOutput.contains("Executing step: Build with command: mvn clean install"));
        assertTrue(consoleOutput.contains("Executing step: Test with command: mvn test"));
        assertTrue(consoleOutput.contains("Executing step: Deploy with command: kubectl apply"));
        assertTrue(consoleOutput.contains("Finished pipeline: Release Pipeline"));
    }

    //TC-44 Pipeline wordt uitgevoerd bij release
    @Test
    void testPipelineRunsOnReleaseSprint() {
        // Act – simulate finalize phase
        sprint.finish();
        sprint.finalized();

        // Assert
        String consoleOutput = output.toString();
        assertTrue(consoleOutput.contains("Releasing the sprint..."), "Should indicate the sprint is being released.");
        assertTrue(consoleOutput.contains("Starting pipeline"), "Pipeline execution should be triggered.");
    }

    //TC-45 Realtime statusupdates
    @Test
    void testRealtimeStatusUpdatesDuringPipeline() {
        // Act
        sprint.finish();
        sprint.finalized();

        // Assert
        String consoleOutput = output.toString();
        assertTrue(consoleOutput.contains("Executing step: Build"), "Build step should show progress.");
        assertTrue(consoleOutput.contains("Executing step: Test"), "Test step should show progress.");
        assertTrue(consoleOutput.contains("Executing step: Deploy"), "Deploy step should show progress.");
    }
}
