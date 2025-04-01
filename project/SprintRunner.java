// package project;

// public class Main {

//     public static void main(String[] args) {
//         System.out.println("Hello, World!");
//     }
// }

package project;

import project.domain.sprint.Sprint;
import project.domain.sprint.interfaces.ISprintStrategy;
import project.domain.sprint.states.CreatedSprintState;
import project.domain.sprint.states.StartedSprintState;
import project.domain.sprint.strategies.ReleaseSprintStrategy;

import java.util.Date;

public class SprintRunner {
    public static void main(String[] args) {

        // Create a sprint with CreatedSprintState
        Sprint sprint = new Sprint("Initial Sprint", new Date(), new Date(), new ReleaseSprintStrategy());

        // Try changing the name while in CreatedSprintState
        System.out.println("Current Sprint Name: " + sprint.getName());
        sprint.setName("Updated Sprint");
        System.out.println("Updated Sprint Name: " + sprint.getName());

        // Change state to ActiveSprintState
        sprint.setState(new StartedSprintState());
        System.out.println("State changed to ActiveSprintState.");

        // Try changing the name while in ActiveSprintState
        sprint.setName("Another Name");
        System.out.println("Final Sprint Name: " + sprint.getName());
    }
}