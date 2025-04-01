package project.domain.common.models;

import java.util.ArrayList;

import project.domain.backlogitem.models.BacklogItem;

public class Backlog {
    private int id;
    private ArrayList<BacklogItem> backlogItems = new ArrayList<>();

    // Constructor
    public Backlog(int id) {
        this.id = id;
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
}
