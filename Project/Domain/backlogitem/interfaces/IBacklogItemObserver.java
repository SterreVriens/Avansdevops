package project.domain.backlogitem.interfaces;

import project.domain.backlogitem.models.BacklogItem;

public interface IBacklogItemObserver {
    public void Update(BacklogItem item);
}
