package domain.common.models;

import java.util.ArrayList;

import domain.backlogitem.models.BacklogItem;

public class Backlog {
    private int id;
    private Project project;
    private ArrayList<BacklogItem> backlogItems = new ArrayList<>();

    // Constructor
    public Backlog(int id, Project project) {
        this.project = project;
        this.id = id;

        project.setBacklog(this);
    }

    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<BacklogItem> getBacklogItems() {
        return backlogItems;
    }

    public void addBacklogItem(BacklogItem backlogItem) {
        this.backlogItems.add(backlogItem);
    }

    public User[] getTesters() {
        return this.project.getTesters();
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
