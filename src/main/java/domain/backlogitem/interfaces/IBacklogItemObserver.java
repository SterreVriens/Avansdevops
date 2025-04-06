package  domain.backlogitem.interfaces;

import  domain.backlogitem.models.BacklogItem;

public interface IBacklogItemObserver {
    public void Update(BacklogItem item);
}
