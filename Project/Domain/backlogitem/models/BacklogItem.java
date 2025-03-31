package project.domain.backlogitem.models;

import java.util.ArrayList;

import project.domain.backlogitem.interfaces.IBacklogItemState;
import project.domain.backlogitem.models.states.ToDoState;
import project.domain.common.models.User;

public class BacklogItem {
    private String id;
    private String title;
    private String description;
    private IBacklogItemState currentState = new ToDoState();
    private ArrayList<IBacklogItemObserver> observers = new ArrayList<>();
    private ArrayList<Activty> activities = new ArrayList<>();
    private User assignedTo;

    public BacklogItem(String id, String title, String description, User assignedTo) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
