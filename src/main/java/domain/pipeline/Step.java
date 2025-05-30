package  domain.pipeline;

//Composite pattern

public class Step extends PipelineComponent {
    private String name;
    private String command;

    public Step(String name, String command) {
        this.name = name;
        this.command = command;
    }

    @Override
    public void performRun() {
        System.out.println("Executing step: " + name + " with command: " + command);
    }
    
}
