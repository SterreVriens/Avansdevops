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
import project.domain.sprint.Sprint;
import project.domain.sprint.interfaces.ISprintStrategy;
import project.domain.sprint.states.CreatedSprintState;
import project.domain.sprint.states.StartedSprintState;
import project.domain.sprint.strategies.ReleaseSprintStrategy;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        User user1 = new User("user1", "123", "user1@example.com", "c33dsds", UserRole.SCRUMMASTER);
        User user2 = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.DEVELOPER);
        User user3 = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.PRODUCTOWNER);
        User user4 = new User("user2", "456", "user2@example.com", "a12bc34", UserRole.TESTER);
        User user5 = new User("user2", "456", "user2@example.com", UserRole.TESTER);

        Project project = new Project(0, "Project", "Making app", user3, null);

        Backlog backlog = new Backlog(1, project);

        Sprint sprint1 = new Sprint("Initial Sprint", new Date(), new Date(), user1,new ReleaseSprintStrategy());

        project.addTeamMembers(user1);
        project.addTeamMembers(user2);
        project.addTeamMembers(user4);
        project.addTeamMembers(user5);

        // ------------------------------------------Section NotifyOnStateChange------------------------------------------
        BacklogItem bi1 = new BacklogItem(1, "Item1", "Description of item 1", user2, backlog);
        sprint1.addBacklogItem(bi1);

        // bi1.setState(new DoingState());
        bi1.setState(new ReadyForTestingState());

        // ------------------------------------------Section End------------------------------------------

        // // Create a sprint with CreatedSprintState
        // Sprint sprint = new Sprint("Initial Sprint", new Date(), new Date(), new
        // ReleaseSprintStrategy());

        // // Try changing the name while in CreatedSprintState
        // System.out.println("Current Sprint Name: " + sprint.getName());
        // sprint.setName("Updated Sprint");
        // System.out.println("Updated Sprint Name: " + sprint.getName());

        // // Change state to ActiveSprintState
        // sprint.setState(new StartedSprintState());
        // System.out.println("State changed to ActiveSprintState.");

        // // Try changing the name while in ActiveSprintState
        // sprint.setName("Another Name");
        // System.out.println("Final Sprint Name: " + sprint.getName());
    }
}