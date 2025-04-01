package project.domain.backlogitem.models;

import java.util.ArrayList;

import project.domain.backlogitem.interfaces.IBacklogItemObserver;
import project.domain.backlogitem.interfaces.IBacklogItemState;
import project.domain.backlogitem.interfaces.IBacklogItemObserver;
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
        this.assignedTo = assignedTo;
    }


    public void setState(IBacklogItemState state) {
        this.currentState = state;
        NotifyObservers();
    }

    public void AddObserver(IBacklogItemObserver observer) {
        observers.add(observer);
    }

    public void DeleteObserver(IBacklogItemObserver observer) {
        observers.remove(observer);
    }

    public void NotifyObservers() {
        for (IBacklogItemObserver observer : observers) {
            observer.Update(this);
        }
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
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


    public IBacklogItemState getCurrentState() {
        return currentState;
    }


    public void setCurrentState(IBacklogItemState currentState) {
        this.currentState = currentState;
    }


    public ArrayList<IBacklogItemObserver> getObservers() {
        return observers;
    }


    public void setObservers(ArrayList<IBacklogItemObserver> observers) {
        this.observers = observers;
    }


    public ArrayList<Activty> getActivities() {
        return activities;
    }


    public void setActivities(ArrayList<Activty> activities) {
        this.activities = activities;
    }


    public User getAssignedTo() {
        return assignedTo;
    }


    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }
}
