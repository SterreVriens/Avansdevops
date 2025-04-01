package project.domain.backlogitem.models;

import java.util.ArrayList;

import project.domain.backlogitem.interfaces.IBacklogItemObserver;
import project.domain.backlogitem.interfaces.IBacklogItemState;
import project.domain.backlogitem.models.states.ToDoState;
import project.domain.common.models.Backlog;
import project.domain.common.models.User;
import project.domain.notification.models.NotificationService;
import project.domain.sprint.Sprint;

public class BacklogItem {
    private Integer id;
    private String title;
    private String description;
    private IBacklogItemState currentState = new ToDoState();
    private ArrayList<IBacklogItemObserver> observers = new ArrayList<>();
    private ArrayList<Activty> activities = new ArrayList<>();
    private User assignedTo;
    private Backlog backlog;
    private Sprint sprint; // Coupled when the BacklogItem is assigned to a Sprint

    public BacklogItem(Integer id, String title, String description, User assignedTo, Backlog backlog) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.backlog = backlog;

        addObserver(new NotificationService());
        backlog.addBacklogItem(this);
    }

    public void setState(IBacklogItemState state) {
        this.currentState = state;
        notifyObservers();
    }

    public void addObserver(IBacklogItemObserver observer) {
        observers.add(observer);
    }

    public void deleteObserver(IBacklogItemObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (IBacklogItemObserver observer : observers) {
            observer.Update(this);
        }
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
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

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public User getScrumMaster() {
        return sprint.getScrumMaster();
    }

    public User[] getTesters() {
        return backlog.getTesters();
    }
}
