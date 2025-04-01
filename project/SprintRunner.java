// package project;

// public class Main {

//     public static void main(String[] args) {
//         System.out.println("Hello, World!");
//     }
// }

package project;

import project.domain.common.models.User;
import project.domain.pipeline.Pipeline;
import project.domain.pipeline.Step;
import project.domain.common.enums.UserRole;
import project.domain.sprint.Sprint;
import project.domain.sprint.interfaces.ISprintStrategy;
import project.domain.sprint.states.CreatedSprintState;
import project.domain.sprint.states.StartedSprintState;
import project.domain.sprint.strategies.ReleaseSprintStrategy;
import project.domain.sprint.strategies.ReviewSprintStrategy;
import project.domain.sprintraport.Raport;
import project.domain.sprintraport.RaportBuilder;

import java.util.Date;

public class SprintRunner {
    public static void main(String[] args) {

        // Create a user (scrum master)
        User user = new User("Bart", "123", "bart@123","asd", UserRole.SCRUMMASTER);

        // Create a pipeline and add steps
        Pipeline pipeline = new Pipeline("Release Pipeline");
        pipeline.addChild(new Step("Build", "mvn clean install"));
        pipeline.addChild(new Step("Test", "mvn test"));
        pipeline.addChild(new Step("Deploy", "kubectl apply"));

        // Create a sprint with CreatedSprintState
        Sprint sprint = new Sprint("Initial Sprint", new Date(), new Date(),user, new ReleaseSprintStrategy(pipeline));

        // Try changing the name while in CreatedSprintState
        System.out.println("Current Sprint Name: " + sprint.getName());
        sprint.setName("Updated Sprint");
        System.out.println("Updated Sprint Name: " + sprint.getName());

        // Change state to ActiveSprintState
        sprint.start();
        System.out.println("State changed to ActiveSprintState.");

        // sprint.generateRaport();

        sprint.finish();

        sprint.raport();

        sprint.generateRaport();

        sprint.exportRaportAsPDF();

        sprint.finalize();

        // Step step1 = new Step("Build", "mvn clean install");
        // Step step2 = new Step("Test", "mvn test");
        // Step step3 = new Step("Deploy", "kubectl apply");

        // // Maak een pipeline en voeg stappen toe
        // Pipeline pipeline = new Pipeline("Release Pipeline");
        // pipeline.addChild(step1);
        // pipeline.addChild(step2);
        // pipeline.addChild(step3);

        // // Voer de hele pipeline uit
        // pipeline.performRun();

        // sprint.setReviewSummery("Review completed successfully.");


        // sprint.finalize();
        // sprint.finalize();

        // sprint.cancel();

        // Raport myraport = new RaportBuilder()
        //     .setHeader("Sales raport 2024")
        //     .setContent("Dit is de inhoud van het rapport...")
        //     .setFooter("Einddatum: 31-12-2024")
        //     .build();

        // // 🔹 Bekijk en exporteer het rapport
        // myraport.showRaport();
        // myraport.exportAsPDF();
        // myraport.exportAsPNG();
    // }
    }
}