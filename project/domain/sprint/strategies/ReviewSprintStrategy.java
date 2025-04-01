package project.domain.sprint.strategies;

import project.domain.sprint.Sprint;
import project.domain.sprint.interfaces.ISprintStrategy;

public class ReviewSprintStrategy implements ISprintStrategy {

    @Override
    public void finalizeSprint(Sprint sprint) {
        // Logic to review the sprint
        System.out.println("Reviewing the sprint...");
        //TODO - add check if review document is created
        // TODO - Add notification logic
        // Additional logic for reviewing the sprint can be added here
    }

    // Other methods specific to ReviewSprintStrategy can be added here
    
}
