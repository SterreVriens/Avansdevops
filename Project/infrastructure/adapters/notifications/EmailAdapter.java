package project.infrastructure.adapters.notifications;

import project.domain.common.models.User;
import project.domain.notification.interfaces.ISenderStrategy;
import project.infrastructure.libs.notifications.EmailLibrary;

public class EmailAdapter implements ISenderStrategy {
    private EmailLibrary emailLibrary = new EmailLibrary();

    public void sendNotification(User toUser, String subject, String body) {
        emailLibrary.sendEmail(toUser.getEmail(), subject, body);
    }
}
