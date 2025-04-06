package domain.backlogitem.models.states;

import domain.backlogitem.interfaces.IBacklogItemState;
import domain.backlogitem.models.BacklogItem;

//State pattern

public class FinalizedState implements IBacklogItemState {
    @Override
    public String toString() {
        return "Finalized";
    }   
    @Override
    public void setState(BacklogItem backlogItem) {
        backlogItem.setCurrentState(this);
        backlogItem.notifyObservers();
    }
}
