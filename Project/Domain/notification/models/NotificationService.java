package project.domain.notification.models;

import project.domain.backlogitem.interfaces.IBacklogItemObserver;
import project.domain.backlogitem.interfaces.IBacklogItemState;
import project.domain.backlogitem.models.*;
import project.domain.backlogitem.models.states.*;
import project.domain.common.models.User;

public class NotificationService implements IBacklogItemObserver {

    @Override
    public void Update(BacklogItem item) {
        IBacklogItemState currentState = item.getCurrentState();
        if (currentState instanceof ReadyForTestingState) {
            sendToTesters(item);
        } else if (currentState instanceof DoingState){
            sendToScrumMaster(item);
        }
    }
    
    private void sendToTesters(BacklogItem item) {
        String subject = "Backlog Item Ready for Testing";
        String body = "The backlog item " + item.getTitle() + " is now ready for testing.";
        User[] testers = item.getTesters();

        for (User tester : testers) {
            tester.getSenderStrategy().sendNotification(tester, subject, body);
        }
    }

    private void sendToScrumMaster(BacklogItem item) {
        String subject = "Backlog Item Ready for Testing";
        String body = "The backlog item " + item.getTitle() + " is now ready for testing.";
        User scrumMaster = item.getScrumMaster();
        
        //TODO: Check if the scrumMaster is null

        scrumMaster.getSenderStrategy().sendNotification(scrumMaster, subject, body);
    }

    public void sendToUser(User toUser, String subject, String body) {
        toUser.getSenderStrategy().sendNotification(toUser, subject, body);
    }

    public void sendToMultipleUsers(User[] toUsers, String subject, String body) {
        for (User user : toUsers) {
            sendToUser(user, subject, body);
        }
    }
}
