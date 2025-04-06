package domain.thread.models;

import domain.common.models.Project;
import domain.common.models.User;
import domain.thread.models.Thread;
import domain.notification.models.NotificationService;
import domain.thread.interfaces.IThreadObserver;

public class CommentNotifier implements IThreadObserver {
    private NotificationService notificationService;

    public CommentNotifier() {
        this.notificationService = new NotificationService();
    }

    @Override
    public void update(ThreadComponent component, Thread thread) {
        if (component instanceof Comment) {
            Comment comment = (Comment) component;
            String subject = comment.getAuthor().getUsername() + " commented";
            String body = '"' + comment.getText() + '"' + "\nOn: " + comment.getDate();
            User[] teamMembers = thread.getBacklogItem().getBacklog().getProject().getTeamMembers()
                    .toArray(new User[0]);

            notificationService.sendToMultipleUsers(teamMembers, subject, body);
        }

    }

}
