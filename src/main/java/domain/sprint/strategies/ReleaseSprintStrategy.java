package  domain.sprint.strategies;

import  domain.pipeline.Pipeline;
import  domain.sprint.Sprint;
import  domain.sprint.interfaces.ISprintStrategy;
import  domain.sprint.states.FinalizedSprintState;

//Strategy pattern

public class ReleaseSprintStrategy implements ISprintStrategy {

    private Pipeline pipeline; // Pipeline voor de release

    public ReleaseSprintStrategy(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Override
    public void finalizeSprint(Sprint sprint) {
        // Logic to release the sprint
        System.out.println("Releasing the sprint...");
        if (pipeline != null) {
            pipeline.performRun(); // Voer alle stappen in de pipeline uit
        } else {
            System.out.println("No pipeline configured for release.");
        }
        sprint.setState(new FinalizedSprintState(sprint));


    
    }
    
}

