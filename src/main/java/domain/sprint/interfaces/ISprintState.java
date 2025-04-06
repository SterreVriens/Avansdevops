package  domain.sprint.interfaces;

//State pattern

public interface ISprintState {
    public void start();
    public void finish();
    public void raport();
    public void finalized();
    public void cancel();

}
