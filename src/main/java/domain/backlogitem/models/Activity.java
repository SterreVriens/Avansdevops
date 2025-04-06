package  domain.backlogitem.models;

public class Activity {
    private String title;
    private String description;
    private boolean isDone;

    public Activity(String title, String description) {
        this.title = title;
        this.description = description;
        this.isDone = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }
    
}
