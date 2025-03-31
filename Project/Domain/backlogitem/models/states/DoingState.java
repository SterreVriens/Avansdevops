package project.domain.backlogitem.states;

import project.domain.backlogitem.interfaces.IBacklogItemState;

public class DoingState implements IBacklogItemState{
    @Override
    public String toString() {
        return "Doing";
    }
}
