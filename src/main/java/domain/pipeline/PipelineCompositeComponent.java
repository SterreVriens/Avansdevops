package  domain.pipeline;

import java.util.ArrayList;
import java.util.List;

//Composite pattern

public class PipelineCompositeComponent extends PipelineComponent {
    private List<PipelineComponent> children = new ArrayList<>();

    public void addChild(PipelineComponent component) {
        children.add(component);
    }

    public void removeChild(PipelineComponent component) {
        children.remove(component);
    }

    @Override
    public void performRun() {
        for (PipelineComponent child : children) {
            child.performRun();
        }
    }
}