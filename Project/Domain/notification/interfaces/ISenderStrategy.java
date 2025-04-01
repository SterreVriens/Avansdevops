package project.domain.notification.interfaces;

import project.domain.common.models.User;

public interface ISenderStrategy {
    public void sendNotification(User toUser, String subject, String body);
}
