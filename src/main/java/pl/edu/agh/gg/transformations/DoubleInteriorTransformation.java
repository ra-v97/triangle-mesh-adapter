package pl.edu.agh.gg.transformations;

import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;

public interface DoubleInteriorTransformation {
    boolean isApplicable(GraphModel graph, InteriorNode firstInterior, InteriorNode secondInterior);

    void transform(GraphModel graph, InteriorNode firstInterior, InteriorNode secondInterior);
}
