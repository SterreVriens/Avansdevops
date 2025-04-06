package  domain.common.models;

import  domain.common.enums.UserRole;
import  domain.notification.interfaces.ISenderStrategy;

public class User {
    private String username;
    private String password;
    private String email;
    private String slackId;
    private UserRole role;
    private ISenderStrategy senderStrategy;

    public User(String username, String password, String email, UserRole role, ISenderStrategy senderStrategy) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;

        this.senderStrategy = senderStrategy;
    }
    public User(String username, String password, String email, String slackId, UserRole role, ISenderStrategy senderStrategy) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.slackId = slackId;
        this.role = role;

        this.senderStrategy = senderStrategy;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
    public String getSlackId() {
        return slackId;
    }

    public UserRole getRole() {
        return role;
    }

    
    public ISenderStrategy getSenderStrategy() {
        return senderStrategy;
    }

}
