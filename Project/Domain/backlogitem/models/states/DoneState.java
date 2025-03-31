package project.domain.backlogitem.states;

import project.domain.backlogitem.interfaces.IBacklogItemState;

public class ToDoState implements IBacklogItemState{
    @Override
    public String toString() {
        return "To Do";
    }
}
