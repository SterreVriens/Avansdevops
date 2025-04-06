package  domain.notification.models;

import  domain.common.models.User;

public class NotificationService {

    public void sendToUser(User toUser, String subject, String body) {
        toUser.getSenderStrategy().sendNotification(toUser, subject, body);
    }

    public void sendToMultipleUsers(User[] toUsers, String subject, String body) {
        for (User user : toUsers) {
            sendToUser(user, subject, body);
        }
    }
}
