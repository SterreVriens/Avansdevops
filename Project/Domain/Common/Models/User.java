package project.domain.common.models;

import project.domain.common.enums.UserRole;
import project.domain.notification.interfaces.ISenderStrategy;
import project.infrastructure.adapters.EmailAdapter;
import project.infrastructure.adapters.SlackAdapter;

public class User {
    private String username;
    private String password;
    private String email;
    private String slackId;
    private UserRole role;
    private ISenderStrategy senderStrategy;

    public User(String username, String password, String email, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;

        senderStrategy = new EmailAdapter();
    }
    public User(String username, String password, String email, String slackId, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.slackId = slackId;
        this.role = role;

        senderStrategy = new SlackAdapter();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlackId() {
        return slackId;
    }

    public void setSlackId(String slackId) {
        this.slackId = slackId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public ISenderStrategy getSenderStrategy() {
        return senderStrategy;
    }

    public void setSenderStrategy(ISenderStrategy senderStrategy) {
        this.senderStrategy = senderStrategy;
    }
}
