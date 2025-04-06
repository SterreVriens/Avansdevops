package  domain.pipeline;

//Composite pattern

public class Pipeline extends PipelineCompositeComponent {
    private String name;

    public Pipeline(String name) {
        this.name = name;
    }

    @Override
    public void performRun() {
        System.out.println("Starting pipeline: " + name);
        super.performRun(); // Voer alle stappen in de pipeline uit
        System.out.println("Finished pipeline: " + name);
    }
}