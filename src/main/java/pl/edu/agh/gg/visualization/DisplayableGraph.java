package pl.edu.agh.gg.visualization;

import org.graphstream.graph.implementations.MultiGraph;
import pl.edu.agh.gg.common.LayerDescriptor;

public interface DisplayableGraph {

    MultiGraph getGraphVisualization();

    MultiGraph getGraphVisualization(LayerDescriptor layerDescriptor);
}
