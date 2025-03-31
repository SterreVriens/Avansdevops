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

    // Make these + SetState(state : State) : void
// + AddObserver(observer : Observer) : void
// + DeleteObserver(observer : Observer) : void
// + NotifyObservers() : void

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
}
