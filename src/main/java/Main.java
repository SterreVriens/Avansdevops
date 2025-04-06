// // package project;

// // public class Main {

// //     public static void main(String[] args) {
// //         System.out.println("Hello, World!");
// //     }
// // }


// import java.util.Date;

// import  domain.backlogitem.models.BacklogItem;
// import domain.backlogitem.models.states.DoingState;
// import domain.backlogitem.models.states.ReadyForTestingState;
// import  domain.common.enums.UserRole;
// import  domain.common.models.Backlog;
// import  domain.common.models.Project;
// import  domain.common.models.User;
// import domain.thread.models.Comment;
// import  domain.thread.models.Thread;
// import  domain.pipeline.Pipeline;
// import  domain.pipeline.Step;
// import  domain.sprint.Sprint;
// import  domain.sprint.strategies.ReleaseSprintStrategy;
// import  domain.sprint.strategies.ReviewSprintStrategy;
// import  infrastructure.adapters.notifications.EmailAdapter;
// import  infrastructure.adapters.notifications.SlackAdapter;

// public class Main {
//     public static void main(String[] args) {
//         //region setup
//         User user1 = new User("user1", "123", "user1@example.com", "c33dsds", UserRole.DEVELOPER, new EmailAdapter());
//         User user2 = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.DEVELOPER, new EmailAdapter());
//         User user3 = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER, new EmailAdapter());
//         User user4 = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.TESTER, new EmailAdapter());
//         User user5 = new User("user2", "456", "user2@example.com", UserRole.TESTER, new SlackAdapter());

//         Project project = new Project(0, "Project", "Making app", user3, null);

//         Backlog backlog = new Backlog(1, project);

//         Pipeline pipeline = new Pipeline("Release Pipeline");
//         pipeline.addChild(new Step("Build", "mvn clean install"));
//         pipeline.addChild(new Step("Test", "mvn test"));
//         pipeline.addChild(new Step("Deploy", "kubectl apply"));

//         Sprint releaseSprint1 = new Sprint("Initial Sprint", new Date(), new Date(), user1, new ReleaseSprintStrategy(pipeline), project);
//         Sprint reviewSprint1 = new Sprint("Initial Sprint", new Date(), new Date(), user1, new ReviewSprintStrategy(), project);

//         reviewSprint1.setReviewSummery("Review summary");

//          project.addTeamMembers(user1);
//          project.addTeamMembers(user2);
//          project.addTeamMembers(user4);
//          project.addTeamMembers(user5);

//         BacklogItem bi1 = new BacklogItem(1, "Item1", "Description of item 1", user2, backlog);
//         releaseSprint1.addBacklogItem(bi1);

//         bi1.setAssignedTo(user1,user1);

//         bi1.setStatus(user2, new DoingState());
//        System.out.println( bi1.getCurrentState());
//         // bi1.setStatus(user2, new ReadyForTestingState());

//         //region GIT
//         // project.addRepository("Backend", new GitAdapter());
//         // project.addRepository("Frontend", new GitAdapter());

//         // Repository repo1 =  project.getRepositoryByName("Backend");
//         // Repository repo2 =  project.getRepositoryByName("Frontend");

//         // repo1.addBranch(new Branch("main", repo1, new GitAdapter()));
//         // repo1.addBranch(new Branch("dev", repo1, new GitAdapter()));
//         // repo2.addBranch(new Branch("main", repo2, new GitAdapter()));

//         // Branch branch1 = repo1.getBranchByName("main");
//         // Branch branch2 = repo1.getBranchByName("dev");
//         // Branch branch3 = repo2.getBranchByName("main");

//         // branch1.addCommit(new Commit("Initial", bi1, user5));
//         // branch1.addCommit(new Commit("Added feature", bi1, user5));
//         // branch1.addCommit(new Commit("Fixed bug", bi1, user5));
//         // branch2.addCommit(new Commit("Initial", bi1, user5));
//         // branch3.addCommit(new Commit("Initial", bi1, user5));
//         // branch3.addCommit(new Commit("Added feature", bi1, user5));

//         //region NotifyOnStateChange

//         // bi1.setState(new DoingState());
//         bi1.setStatus(user2, new ReadyForTestingState());
//         System.out.println(bi1.getCurrentState());


//         //region Threads


//         // bi1.addThread(new Thread("Problem", bi1));
//         // bi1.addThread(new Thread("Fixxing guide", bi1));

//         // bi1.getThreadByTitle("Problem").addChild(new Comment("There is a defect with the item", "Programmer12", "2023-10-01"));
//         // bi1.getThreadByTitle("Problem").addChild(new Comment("I will fix it", "Senior Programmer15", "2023-11-01"));
//         // bi1.getThreadByTitle("Problem").addChild(new Comment("Tnx", "Programmer12", "2023-11-01"));

//         // bi1.getThreadByTitle("Fixxing guide").addChild(new Comment("I fixxed it", "Senior Programmer15", "2023-12-01"));

//         // bi1.printThreads();

//         //region Pipeline notifications

//         // //Release
//         // releaseSprint1.start();

//         // releaseSprint1.finish();

//         // releaseSprint1.raport();

//         // releaseSprint1.cancel();

//         // System.out.println("\n----------------");

//         // //review
//         // reviewSprint1.start();

//         // reviewSprint1.finish();

//         // reviewSprint1.finalize();
//     }
// }
//         // reviewSprint1.raport();