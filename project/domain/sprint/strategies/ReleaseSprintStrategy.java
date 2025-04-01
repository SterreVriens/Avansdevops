package project.domain.sprint.strategies;

import project.domain.pipeline.Pipeline;
import project.domain.sprint.Sprint;
import project.domain.sprint.interfaces.ISprintStrategy;
import project.domain.sprint.states.FinalizedSprintState;

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

