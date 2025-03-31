package project.domain.backlogitem.states;

import project.domain.backlogitem.interfaces.IBacklogItemState;

public class ReadyForTestingState implements IBacklogItemState{
    @Override
    public String toString() {
        return "Ready for testing";
    }
}
