// // package project;

// // public class Main {

// //     public static void main(String[] args) {
// //         System.out.println("Hello, World!");
// //     }
// // }

// import domain.common.models.Backlog;
// import domain.common.models.Project;
// import  domain.common.models.User;
// import  domain.pipeline.Pipeline;
// import  domain.pipeline.Step;
// import  domain.common.enums.UserRole;
// import  domain.sprint.Sprint;
// import  domain.sprint.interfaces.ISprintStrategy;
// import  domain.sprint.states.CreatedSprintState;
// import  domain.sprint.states.StartedSprintState;
// import  domain.sprint.strategies.ReleaseSprintStrategy;
// import  domain.sprint.strategies.ReviewSprintStrategy;
// import  domain.sprintraport.Raport;
// import  domain.sprintraport.RaportBuilder;
// import infrastructure.adapters.notifications.EmailAdapter;

// import java.util.Date;

// public class SprintRunner {
//     public static void main(String[] args) {

//         // Create a user (scrum master)
//         User productOwner = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER, new EmailAdapter());
//         User scrumMaster = new User("scrum", "123", "scrum@example.com", "slack1", UserRole.SCRUMMASTER, new EmailAdapter());
//         Project project = new Project(1, "Project Alpha", "Description of project alpha", productOwner, null);
//         Backlog backlog = new Backlog(1, project);
//         project.setBacklog(backlog);


//         // Create a pipeline and add steps
//         Pipeline pipeline = new Pipeline("Release Pipeline");
//         pipeline.addChild(new Step("Build", "mvn clean install"));
//         pipeline.addChild(new Step("Test", "mvn test"));
//         pipeline.addChild(new Step("Deploy", "kubectl apply"));

//         // Create a sprint with CreatedSprintState
//         Sprint sprint = new Sprint("Initial Sprint", new Date(), new Date(),scrumMaster, new ReleaseSprintStrategy(pipeline), project);

//         // Try changing the name while in CreatedSprintState
//         System.out.println("Current Sprint Name: " + sprint.getName());
//         sprint.setName("Updated Sprint");
//         System.out.println("Updated Sprint Name: " + sprint.getName());

//         System.out.println(sprint.getReviewSummery());

//         // Change state to ActiveSprintState
//         sprint.start();
//         System.out.println("State changed to ActiveSprintState.");

//         // sprint.generateRaport();

//         sprint.finish(); // hier binnen wordt zoals in de sprint klasse staat de finalize van de release strategy aangeroepe die de pipeline start
//         sprint.finalized();

//         // sprint.raport();

//         // sprint.generateRaport();

//         // sprint.exportRaportAsPDF();

//         // sprint.finalize();
//         // System.out.println(sprint.getState());

//         // Step step1 = new Step("Build", "mvn clean install");
//         // Step step2 = new Step("Test", "mvn test");
//         // Step step3 = new Step("Deploy", "kubectl apply");

//         // // Maak een pipeline en voeg stappen toe
//         // Pipeline pipeline = new Pipeline("Release Pipeline");
//         // pipeline.addChild(step1);
//         // pipeline.addChild(step2);
//         // pipeline.addChild(step3);

//         // // Voer de hele pipeline uit
//         // pipeline.performRun();

//         // sprint.setReviewSummery("Review completed successfully.");


//         // sprint.finalize();
//         // sprint.finalize();

//         // sprint.cancel();

//         // Raport myraport = new RaportBuilder()
//         //     .setHeader("Sales raport 2024")
//         //     .setContent("Dit is de inhoud van het rapport...")
//         //     .setFooter("Einddatum: 31-12-2024")
//         //     .build();

//         // // 🔹 Bekijk en exporteer het rapport
//         // myraport.showRaport();
//         // myraport.exportAsPDF();
//         // myraport.exportAsPNG();
//     // }
//     }
// }