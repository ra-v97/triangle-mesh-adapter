package pl.edu.agh.gg.processing;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

public final class ControlDiagram {

    private final Map<Integer, StepDescriptor> controlMap;

    private int activeStep;

    private ControlDiagram(Map<Integer, StepDescriptor> controlMap) {
        this.controlMap = Map.copyOf(controlMap);
        this.activeStep = -1;
    }

    public Optional<StepDescriptor> nextStep() {
        if (controlMap.containsKey(activeStep + 1)) {
            activeStep++;
            return Optional.of(controlMap.get(activeStep));
        }
        return Optional.empty();
    }

    public Optional<StepDescriptor> prevStep() {
        if (controlMap.containsKey(activeStep - 1)) {
            activeStep--;
            return Optional.of(controlMap.get(activeStep));
        }
        return Optional.empty();
    }

    public static ControlDiagramBuilder builder() {
        return new ControlDiagramBuilder();
    }

    public static final class ControlDiagramBuilder {

        private final Map<Integer, StepDescriptor> workingMap;

        private int lastElementIdx;

        public ControlDiagramBuilder() {
            this.workingMap = Maps.newHashMap();
            this.lastElementIdx = -1;
        }

        public ControlDiagramBuilder addStep(StepDescriptor stepDescriptor) {
            lastElementIdx++;
            workingMap.put(lastElementIdx, stepDescriptor);
            return this;
        }

        public ControlDiagram build() {
            return new ControlDiagram(workingMap);
        }
    }
}
