package  domain.notification.interfaces;

import  domain.common.models.User;

public interface ISenderStrategy {
    public void sendNotification(User toUser, String subject, String body);
}
