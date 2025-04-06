package  domain.backlogitem.models.states;

import  domain.backlogitem.interfaces.IBacklogItemState;

public class TestedState implements IBacklogItemState{
    @Override
    public String toString() {
        return "Tested";
    }
}
