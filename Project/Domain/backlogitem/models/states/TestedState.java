package project.domain.backlogitem.models.states;

import project.domain.backlogitem.interfaces.IBacklogItemState;

public class TestedState implements IBacklogItemState{
    @Override
    public String toString() {
        return "Tested";
    }
}
