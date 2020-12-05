package pl.edu.agh.gg.transformations;

import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;

public interface Transformation {
    boolean isTransformationApplicable(GraphModel graph, InteriorNode interior);

    void transform(GraphModel graph, InteriorNode interior);
}
