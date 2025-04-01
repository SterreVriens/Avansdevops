package project.domain.sprint.strategies;

import project.domain.sprint.Sprint;
import project.domain.sprint.interfaces.ISprintStrategy;
import project.domain.sprint.states.FinalizedSprintState;

public class ReviewSprintStrategy implements ISprintStrategy {

    @Override
    public void finalizeSprint(Sprint sprint) {
        System.out.println("Reviewing the sprint...");

        if(sprint.getReviewSummery() == null) {
            System.out.println("⛔️ Review summary is not created yet.");

        } else {
            // TODO - Add notification logic

            sprint.setState(new FinalizedSprintState(sprint));
            System.out.println("Review summary: " + sprint.getReviewSummery());
        }
    }

    // Other methods specific to ReviewSprintStrategy can be added here
    
}
