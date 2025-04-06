package infrastructure.adapters.notifications;

import domain.common.models.User;
import domain.notification.interfaces.ISenderStrategy;
import infrastructure.libs.notifications.EmailLibrary;

//adapter pattern

public class EmailAdapter implements ISenderStrategy {
    private EmailLibrary emailLibrary = new EmailLibrary();

    public void sendNotification(User toUser, String subject, String body) {
        emailLibrary.sendEmail(subject, body, toUser.getEmail());
    }
}
