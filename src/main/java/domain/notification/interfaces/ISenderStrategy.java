package  domain.notification.interfaces;

import  domain.common.models.User;

//Strategy pattern

public interface ISenderStrategy {
    public void sendNotification(User toUser, String subject, String body);
}
