package pl.edu.agh.gg.app;

import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.transformations.*;
import pl.edu.agh.gg.visualization.Visualizer;

import java.io.IOException;
import java.util.ArrayList;

public final class ExerciseRegistry {

    public static void executeTaskB() {
        final GraphModel graph = createStartingGraph();
        Visualizer visualizer = new Visualizer(graph);
        visualizer.visualize();
        System.out.println("Starting graph" + " Press any key to continue");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Transformation> transformations = new ArrayList<>();
        transformations.add(new TransformationP1());
        transformations.add(new TransformationP2());
        transformations.add(new TransformationP9());

        for (int i = 0; i < 2; i++) { // this will be replaced with a do-while loop when we have the logic for refining the triangles
            InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
            for (InteriorNode interior : interiors) {
                for (Transformation t : transformations) {
                    if (t.isApplicable(graph, interior)) {
                        System.out.println("Executing transformation: " + t.getClass().getSimpleName() + " on interior" + interior.getLabel());
                        t.transform(graph, interior);
                        transformations.remove(t);
                        visualizer = new Visualizer(graph);
                        visualizer.visualize(new LayerDescriptor(0));
                        visualizer.visualize(new LayerDescriptor(1));
                        visualizer.visualize(new LayerDescriptor(2));
                        System.out.println("Graph after " + t.getClass().getSimpleName() + " Press any key to continue");
                        try {
                            System.in.read();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }

        ArrayList<DoubleInteriorTransformation> doubleInteriorTransformations = new ArrayList<>();
        doubleInteriorTransformations.add(new TransformationP10());

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

        visualizer = new Visualizer(graph);
        visualizer.visualize(new LayerDescriptor(0));
        visualizer.visualize(new LayerDescriptor(1));
        visualizer.visualize(new LayerDescriptor(2));
        System.out.println("Final graph (after TransformationP10)");
    }

    private static GraphModel createStartingGraph() {
        final GraphModel graphModel = new GraphModel();
        graphModel.insertStartingInterior("S", new LayerDescriptor(0), new Coordinates(0, 0, 0));
        return graphModel;
    }

    private ExerciseRegistry() {
    }
}
