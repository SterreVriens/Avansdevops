package  domain.thread.models;

import domain.common.models.User;

public class Comment extends ThreadComponent {
    private String text;
    private User author;
    private String date;

    public Comment(String text, User author, String date) {
        this.text = text;
        this.author = author;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text, User initiator) {
        if(initiator.equals(author)) {
            this.text = text;
        } else {
            System.out.println("Only the author can change the comment.");
        }
    }

    public User getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", author='" + author.getUsername() + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public void print() {
       System.out.println("Comment: " + text + " by " + author.getUsername() + " on " + date);
    }
    
}
