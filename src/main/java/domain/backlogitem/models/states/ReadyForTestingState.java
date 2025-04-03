package  domain.backlogitem.models.states;

import  domain.backlogitem.interfaces.IBacklogItemState;

public class ReadyForTestingState implements IBacklogItemState{
    @Override
    public String toString() {
        return "Ready for testing";
    }
}
