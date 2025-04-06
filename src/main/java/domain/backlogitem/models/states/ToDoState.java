package  domain.backlogitem.models.states;

import  domain.backlogitem.interfaces.IBacklogItemState;

public class ToDoState implements IBacklogItemState{
    @Override
    public String toString() {
        return "To Do";
    }
}
