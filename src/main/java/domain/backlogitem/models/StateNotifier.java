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
    public StateNotifier(NotificationService notificationService) {
        this.notificationService = notificationService;
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
        String body = "The backlog item " + item.getTitle() + " is now ready for testing. This item is assigned to " + item.getAssignedTo().getUsername() + ".";
        User[] testers = item.getTesters();

        this.notificationService.sendToMultipleUsers(testers, subject, body);
    }

    private void sendToScrumMaster(BacklogItem item) {
        User scrumMaster = item.getScrumMaster();
        String subject = "Item state changed to Doing";
        String body = "The backlog item " + item.getTitle() + " has been set to doing. This item is assigned to " + item.getAssignedTo().getUsername() + ".";

        if (scrumMaster == null) {
            System.out.println("Scrum Master is not assigned to this backlog item.");
            return;
        }

        this.notificationService.sendToUser(scrumMaster, subject, body);
    }
}
