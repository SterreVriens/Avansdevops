package  domain.backlogitem.models.states;

import  domain.backlogitem.interfaces.IBacklogItemState;

public class TestingState implements IBacklogItemState{
    @Override
    public String toString() {
        return "Testing";
    }
}
