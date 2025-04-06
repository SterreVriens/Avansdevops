package domain.backlogitem.models.states;

import domain.backlogitem.interfaces.IBacklogItemState;
import domain.backlogitem.models.BacklogItem;

public class  FinalizedState implements IBacklogItemState {
    @Override
    public String toString() {
        return " finalizedd";
    }   
    @Override
    public void setState(BacklogItem backlogItem) {
        backlogItem.setCurrentState(this);
        backlogItem.notifyObservers();
    }
}
