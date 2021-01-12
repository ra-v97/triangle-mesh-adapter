package pl.edu.agh.gg.processing;

import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.transformations.DoubleInteriorTransformation;
import pl.edu.agh.gg.transformations.Transformation;

import java.util.Optional;

public final class StepDescriptor {

    private final LayerDescriptor activeLayer;

    private final Transformation transformation;

    private final DoubleInteriorTransformation doubleInteriorTransformation;

    private final boolean isDoubleTransformation;

    public StepDescriptor(LayerDescriptor activeLayer, Transformation transformation) {
        this.activeLayer = activeLayer;
        this.transformation = transformation;
        this.doubleInteriorTransformation = null;
        this.isDoubleTransformation = false;
    }

    public StepDescriptor(LayerDescriptor activeLayer, DoubleInteriorTransformation doubleInteriorTransformation) {
        this.activeLayer = activeLayer;
        this.transformation = null;
        this.doubleInteriorTransformation = doubleInteriorTransformation;
        this.isDoubleTransformation = true;
    }

    public LayerDescriptor getActiveLayer() {
        return activeLayer;
    }

    public Optional<Transformation> getTransformation() {
        return Optional.ofNullable(transformation);
    }

    public Optional<DoubleInteriorTransformation> getDoubleInteriorTransformation() {
        return Optional.ofNullable(doubleInteriorTransformation);
    }

    public boolean isDoubleTransformation() {
        return isDoubleTransformation;
    }
}
