package pl.edu.agh.gg.app;

import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphEdge;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;
import pl.edu.agh.gg.processing.ControlDiagram;
import pl.edu.agh.gg.processing.ProductionChainController;
import pl.edu.agh.gg.processing.StepDescriptor;
import pl.edu.agh.gg.transformations.*;
import pl.edu.agh.gg.transformations.TransformationP5;
import pl.edu.agh.gg.visualization.Visualizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class Main {

    private static final int stepsToDo = 4;

    public static void main(String[] args) {

        final GraphModel graph = createStartingGraph();

        final ControlDiagram controlDiagram = ControlDiagram.builder()
                .addStep(new StepDescriptor(new LayerDescriptor(), new TransformationP1()))
                .addStep(new StepDescriptor(new LayerDescriptor(1), new TransformationP9()))
                .addStep(new StepDescriptor(new LayerDescriptor(1), new TransformationP9()))
                .addStep(new StepDescriptor(new LayerDescriptor(2), new TransformationP12()))
                .build();

        final var productionChainController = new ProductionChainController(controlDiagram, graph);

        // Initial graph
        final Visualizer initialVisualizer = new Visualizer(graph);
        initialVisualizer.visualize(new LayerDescriptor(0));

        for (int i = 0; i < stepsToDo; i++) {
            final int topLayer = i + 1;
            productionChainController.nextStep()
                    .ifPresent(graphModel -> {
                        final Visualizer vs = new Visualizer(graph);
                        vs.visualize(new LayerDescriptor(topLayer));
                    });
        }

        // Full graph after transformations
        productionChainController.activeStage().ifPresent(graphModel -> {
            final Visualizer fullVisualizer = new Visualizer(graph);
            fullVisualizer.visualize();
        });
    }

    private static GraphModel createStartingGraph() {
        final GraphModel graphModel = new GraphModel();
        graphModel.insertStartingInterior("S", new LayerDescriptor(0), new Coordinates(0, 0, 0));
        return graphModel;
    }
}
