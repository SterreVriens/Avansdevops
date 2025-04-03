// package project;

// public class Main {

//     public static void main(String[] args) {
//         System.out.println("Hello, World!");
//     }
// }

package project;

import project.domain.backlogitem.models.BacklogItem;
import project.domain.backlogitem.models.states.DoingState;
import project.domain.backlogitem.models.states.DoneState;
import project.domain.backlogitem.models.states.ReadyForTestingState;
import project.domain.common.enums.UserRole;
import project.domain.common.models.Backlog;
import project.domain.common.models.Project;
import project.domain.common.models.User;
import project.domain.pipeline.Pipeline;
import project.domain.pipeline.Step;
import project.domain.sprint.Sprint;
import project.domain.sprint.interfaces.ISprintStrategy;
import project.domain.sprint.states.CreatedSprintState;
import project.domain.sprint.states.StartedSprintState;
import project.domain.sprint.strategies.ReleaseSprintStrategy;
import project.domain.sprint.strategies.ReviewSprintStrategy;
import project.domain.thread.Comment;
import project.domain.thread.Thread;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        //region setup
        User user1 = new User("user1", "123", "user1@example.com", "c33dsds", UserRole.SCRUMMASTER);
        User user2 = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.DEVELOPER);
        User user3 = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER);
        User user4 = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.TESTER);
        User user5 = new User("user2", "456", "user2@example.com", UserRole.TESTER);

        Project project = new Project(0, "Project", "Making app", user3, null);

        Backlog backlog = new Backlog(1, project);

        Pipeline pipeline = new Pipeline("Release Pipeline");
        pipeline.addChild(new Step("Build", "mvn clean install"));
        pipeline.addChild(new Step("Test", "mvn test"));
        pipeline.addChild(new Step("Deploy", "kubectl apply"));

        Sprint releaseSprint1 = new Sprint("Initial Sprint", new Date(), new Date(), user1, new ReleaseSprintStrategy(pipeline), project);
        Sprint reviewSprint1 = new Sprint("Initial Sprint", new Date(), new Date(), user1, new ReviewSprintStrategy(), project);

        //TODO: ?
        reviewSprint1.setReviewSummery("Review summary");

        project.addTeamMembers(user1);
        project.addTeamMembers(user2);
        project.addTeamMembers(user4);
        project.addTeamMembers(user5);

        BacklogItem bi1 = new BacklogItem(1, "Item1", "Description of item 1", user2, backlog);
        releaseSprint1.addBacklogItem(bi1);

        //region NotifyOnStateChange

        // bi1.setState(new DoingState());
        // bi1.setState(new ReadyForTestingState());

        //region Threads


        bi1.addThread(new Thread("Problem"));
        bi1.addThread(new Thread("Fixxing guide"));

        bi1.getThreadByTitle("Problem").addChild(new Comment("There is a defect with the item", "Programmer12", "2023-10-01"));
        bi1.getThreadByTitle("Problem").addChild(new Comment("I will fix it", "Senior Programmer15", "2023-11-01"));
        bi1.getThreadByTitle("Problem").addChild(new Comment("Tnx", "Programmer12", "2023-11-01"));

        bi1.getThreadByTitle("Fixxing guide").addChild(new Comment("I fixxed it", "Senior Programmer15", "2023-12-01"));

        bi1.printThreads();

        //region Pipeline notifications
        
        // //Release
        // releaseSprint1.start();

        // releaseSprint1.finish();

        // releaseSprint1.raport();

        // releaseSprint1.cancel();
 
        // System.out.println("\n----------------");

        // //review
        // reviewSprint1.start();

        // reviewSprint1.finish();

        // reviewSprint1.finalize();
    }
}