package  domain.sprint.strategies;

import  domain.common.models.User;
import  domain.sprint.Sprint;
import  domain.sprint.interfaces.ISprintStrategy;
import  domain.sprint.states.FinalizedSprintState;

// Strategy pattern

public class ReviewSprintStrategy implements ISprintStrategy {

    @Override
    public void finalizeSprint(Sprint sprint) {
        System.out.println("Reviewing the sprint...");

        if(sprint.getReviewSummery() == null) {
            System.out.println("⛔️ Review summary is not created yet.");

        } else {
            String subject = "Sprint Finilized";
            String message = "Sprint " + sprint.getName() + " has been finilized.";
            User[] users = new User[] { sprint.getScrumMaster(), sprint.getProject().getProductOwner() };
            sprint.sendNotification(users, subject, message);

            sprint.setState(new FinalizedSprintState(sprint));
            System.out.println("Review summary: " + sprint.getReviewSummery());
        }
    }

    
}
