package  domain.backlogitem.models.states;

import  domain.backlogitem.interfaces.IBacklogItemState;

public class DoneState implements IBacklogItemState{
    @Override
    public String toString() {
        return "Done";
    }
}
