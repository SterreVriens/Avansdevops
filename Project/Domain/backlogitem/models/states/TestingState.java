package project.domain.backlogitem.states;

import project.domain.backlogitem.interfaces.IBacklogItemState;

public class TestingState implements IBacklogItemState{
    @Override
    public String toString() {
        return "Testing";
    }
}
