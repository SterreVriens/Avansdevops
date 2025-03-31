package Project.domain.common.models;

import java.util.List;

public class Backlog {
    private int id;
    // TODO Add list of backlogitems
    // private List<Backlog> backlogItems;

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
    // TODO Add methods to manage backlog items
    // Backlog Items
    // public List<Backlog> getBacklogItems() {
    //     return backlogItems;
    // }
    // public void addBacklogItem(Backlog backlogItem) {
    //     this.backlogItems.add(backlogItem);
    // }
}
