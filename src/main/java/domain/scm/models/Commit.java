package  domain.scm.models;

import  domain.backlogitem.models.BacklogItem;
import  domain.common.models.User;

public class Commit {
    private String message;
    private BacklogItem backlogItem;
    private User author;

    public Commit(String message, BacklogItem backlogItem, User author) {
        this.message = message;
        this.backlogItem = backlogItem;
        this.author = author;

        backlogItem.addCommit(this);
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public BacklogItem getBacklogItem() {
        return backlogItem;
    }
    public void setBacklogItem(BacklogItem backlogItem) {
        this.backlogItem = backlogItem;
    }
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
}
