package  domain.backlogitem.models.states;

import  domain.backlogitem.interfaces.IBacklogItemState;

public class DoingState implements IBacklogItemState{
    @Override
    public String toString() {
        return "Doing";
    }
}
