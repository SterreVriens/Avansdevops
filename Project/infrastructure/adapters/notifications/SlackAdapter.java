package project.infrastructure.adapters.notifications;

import project.domain.common.models.User;
import project.domain.notification.interfaces.ISenderStrategy;
import project.infrastructure.libs.notifications.SlackLibrary;

public class SlackAdapter implements ISenderStrategy {
    private SlackLibrary slackLibrary;

    public SlackAdapter(){
        this.slackLibrary = new SlackLibrary();
    }
    
 public void sendNotification(User toUser, String subject, String body) {
        String message = "Subject: " + subject + "\n" + "Body: " + body;
        slackLibrary.sendMessage(message, toUser.getSlackId());
    }
}
