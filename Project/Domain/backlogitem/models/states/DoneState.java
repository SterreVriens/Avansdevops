package project.domain.backlogitem.models.states;

import project.domain.backlogitem.interfaces.IBacklogItemState;

public class DoneState implements IBacklogItemState{
    @Override
    public String toString() {
        return "Done";
    }
}
