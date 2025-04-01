package project.infrastructure.adapters;

import project.domain.common.models.User;
import project.infrastructure.libs.SlackLibrary;

public class SlackAdapter {
    private SlackLibrary slackLibrary;

 public void sendNotification(User toUser, String subject, String body) {
        String message = "Subject: " + subject + "\n" + "Body: " + body;
        slackLibrary.sendMessage(message, toUser.getSlackId());
    }
}
