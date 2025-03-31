package project.domain.backlogitem.models.states;

import project.domain.backlogitem.interfaces.IBacklogItemState;

public class ReadyForTestingState implements IBacklogItemState{
    @Override
    public String toString() {
        return "Ready for testing";
    }
}
