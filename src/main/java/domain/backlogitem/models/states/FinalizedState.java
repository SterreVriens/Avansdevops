package domain.backlogitem.models.states;

import domain.backlogitem.interfaces.IBacklogItemState;

public class FinalizedState implements IBacklogItemState {
    @Override
    public String toString() {
        return "Finalized";
    }   
}
