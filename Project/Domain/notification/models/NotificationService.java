package project.domain.notification.models;

import project.domain.backlogitem.interfaces.IBacklogItemObserver;
import project.domain.backlogitem.interfaces.IBacklogItemState;
import project.domain.backlogitem.models.*;
import project.domain.backlogitem.models.states.*;
import project.domain.notification.interfaces.ISenderStrategy;

public class NotificationService implements IBacklogItemObserver {
    private ISenderStrategy senderStrategy;

    public NotificationService(ISenderStrategy senderStrategy) {
        this.senderStrategy = senderStrategy;
    }

    @Override
    public void Update(BacklogItem item) {
        IBacklogItemState currentState = item.getCurrentState();
        if (currentState instanceof ReadyForTestingState) {
            sendToTesters(item);
        } else if (currentState instanceof DoingState){

        }

    }
    
    private void sendToTesters(BacklogItem item) {
        String subject = "Backlog Item Ready for Testing";
        String body = "The backlog item " + item.getTitle() + " is now ready for testing.";
        User[] testers = item.getTesters();
        
        for (User tester : testers) {
            senderStrategy.sendNotification(tester, subject, body);
        }
    }
    private void sendToScrumMaster(BacklogItem item) {
        String subject = "Backlog Item Ready for Testing";
        String body = "The backlog item " + item.getTitle() + " is now ready for testing.";
        User scrumMaster = item.getScrumMaster();
        
        senderStrategy.sendNotification(scrumMaster, subject, body);
    }
}
