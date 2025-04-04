package domain.backlogitem.models;

import domain.backlogitem.interfaces.IBacklogItemObserver;
import domain.backlogitem.interfaces.IBacklogItemState;
import domain.backlogitem.models.states.DoingState;
import domain.backlogitem.models.states.ReadyForTestingState;
import domain.common.models.User;
import domain.notification.models.NotificationService;

public class StateNotifier implements IBacklogItemObserver {
    private NotificationService notificationService;

    public StateNotifier() {
        this.notificationService = new NotificationService();
    }

    @Override
    public void Update(BacklogItem item) {
        IBacklogItemState currentState = item.getCurrentState();
        if (currentState instanceof ReadyForTestingState) {
            sendToTesters(item);
        } else if (currentState instanceof DoingState) {
            sendToScrumMaster(item);
        }
    }

    private void sendToTesters(BacklogItem item) {
        String subject = "Backlog Item Ready for Testing";
        String body = "The backlog item " + item.getTitle() + " is now ready for testing.";
        User[] testers = item.getTesters();

        this.notificationService.sendToMultipleUsers(testers, subject, body);
    }

    private void sendToScrumMaster(BacklogItem item) {
        String subject = "Backlog Item Ready for Testing";
        String body = "The backlog item " + item.getTitle() + " is now ready for testing.";
        User scrumMaster = item.getScrumMaster();

        if (scrumMaster == null) {
            System.out.println("Scrum Master is not assigned to this backlog item.");
            return;
        }

        this.notificationService.sendToUser(scrumMaster, subject, body);
    }
}
