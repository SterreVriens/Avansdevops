package project.domain.sprint.strategies;

import project.domain.sprint.Sprint;
import project.domain.sprint.interfaces.ISprintStrategy;

public class ReleaseSprintStrategy implements ISprintStrategy {

    @Override
    public void finalizeSprint(Sprint sprint) {
        // Logic to release the sprint
        System.out.println("Releasing the sprint...");
        // TODO - add pipeline logic
        // TODO update state to finished state
        // Additional logic for releasing the sprint can be added here
    }

    // Other methods specific to ReleaseSprintStrategy can be added here
    
}
