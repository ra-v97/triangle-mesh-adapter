package pl.edu.agh.gg.visualization;

import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.Viewer;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;

public class Visualizer {

    private final DisplayableGraph graph;

    static{
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
    }

    public Visualizer(GraphModel graph) {
        this.graph = graph;
    }

    public void visualize() {
        final MultiGraph displayableGraph = graph.getGraphVisualization();

        configureGraphVisualization(displayableGraph);
        display(displayableGraph);
    }

    public void visualize(LayerDescriptor layerDescriptor) {
        final MultiGraph displayableGraph = graph.getGraphVisualization(layerDescriptor);
        configureGraphVisualization(displayableGraph);
        display(displayableGraph);
    }

    private void configureGraphVisualization(MultiGraph displayableGraph){
        displayableGraph.setAttribute("ui.stylesheet", "url('file:src/main/resources/styles.css')");
    }

    private void display(MultiGraph displayableGraph) {
        final Viewer viewer =  displayableGraph.display();
        viewer.disableAutoLayout();
    }
}
