package pl.edu.agh.gg.app;

import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphEdge;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;
import pl.edu.agh.gg.transformations.*;
import pl.edu.agh.gg.transformations.TransformationP5;
import pl.edu.agh.gg.visualization.Visualizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        final GraphModel graph = createStartingGraph();
        List<Transformation> transformations = Arrays.asList(new TransformationP1(), new TransformationP2());

        for (int i = 0; i < 1; i++) { // this will be replaced with a do-while loop when we have the logic for refining the triangles
            InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
            for (InteriorNode interior : interiors) {
                for (Transformation t : transformations) {
                    if (t.isApplicable(graph, interior)) {
                        System.out.println("Executing transformation: " + t.getClass().getSimpleName() + " on interior" + interior.getLabel());
                        t.transform(graph, interior);
                    }
                }
            }
        }

        List<DoubleInteriorTransformation> doubleInteriorTransformations =
                Arrays.asList(new TransformationP6(), new TransformationP7());
        InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
        for (DoubleInteriorTransformation t : doubleInteriorTransformations) {
            for (InteriorNode interior1 : interiors) {
                for (InteriorNode interior2 : interiors) {
                    if (!interior1.equals(interior2) && t.isApplicable(graph, interior1, interior2)) {
                        System.out.println("Executing transformation: " + t.getClass().getSimpleName() + " on interiors " + interior1.getLabel() + " and " + interior2.getLabel());
                        t.transform(graph, interior1, interior2);
                    }
                }
            }
        }

        final Visualizer visualizer = new Visualizer(graph);
//        visualizer.visualize();
        visualizer.visualize(new LayerDescriptor(0));
        visualizer.visualize(new LayerDescriptor(1));
//        visualizer.visualize(new LayerDescriptor(2));
    }

    private static GraphModel createStartingGraph() {
        final GraphModel graphModel = new GraphModel();
        graphModel.insertStartingInterior("S", new LayerDescriptor(0), new Coordinates(0, 0, 0));
        return graphModel;
    }
}
