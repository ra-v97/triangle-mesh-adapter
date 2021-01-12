package pl.edu.agh.gg.app;

import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.*;
import pl.edu.agh.gg.transformations.*;
import pl.edu.agh.gg.visualization.Visualizer;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;


public class Main_C_KK_JJ {
    public static void main(String[] args) {
        GraphModel graphTaskB = executeTaskB();
        executeTaskC(graphTaskB);
    }

    private static GraphModel executeTaskB() {
        final GraphModel graph = createStartingGraph();
        Visualizer visualizer = new Visualizer(graph);
        visualizer.visualize();
//        System.out.println("Starting graph" + " Press any key to continue");
//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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
//                        visualizer = new Visualizer(graph);
//                        visualizer.visualize(new LayerDescriptor(0));
//                        visualizer.visualize(new LayerDescriptor(1));
//                        visualizer.visualize(new LayerDescriptor(2));
//                        System.out.println("Graph after " + t.getClass().getSimpleName() + " Press any key to continue");
//                        try {
//                            System.in.read();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
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

//        visualizer = new Visualizer(graph);
//        visualizer.visualize(new LayerDescriptor(0));
//        visualizer.visualize(new LayerDescriptor(1));
//        visualizer.visualize(new LayerDescriptor(2));
//        System.out.println("Graph after P10  Press any key to continue");
//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return graph;
    }

    private static void executeTaskC(GraphModel graph) {
        Visualizer visualizer = new Visualizer(graph);

        ArrayList<Transformation> transformations = new ArrayList<>();
        transformations.add(new TransformationP9());
        transformations.add(new TransformationP3());

        for (int i = 0; i < 3; i++) { // this will be replaced with a do-while loop when we have the logic for refining the triangles
            InteriorNode[] interiors1 = graph.getInteriors().toArray(new InteriorNode[0]);
            for (Transformation t1 : transformations) {
                for (InteriorNode interior : interiors1) {
                    if (t1.isApplicable(graph, interior) && graph.resolveInteriorLayer(interior.getUUID()).get().getLayerNo() == 2) {
                        System.out.println("Executing transformation: " + t1.getClass().getSimpleName() + " on interior" + interior.getCoordinates().toString());
                        t1.transform(graph, interior);
                        //                visualizer = new Visualizer(graph);
                        //                visualizer.visualize(new LayerDescriptor(0));
                        //                visualizer.visualize(new LayerDescriptor(1));
                        //                visualizer.visualize(new LayerDescriptor(2));
                        //                visualizer.visualize(new LayerDescriptor(3));
                        //                System.out.println("Graph after " + t1.getClass().getSimpleName() + " Press any key to continue");
                        //                try {
                        //                    System.in.read();
                        //                } catch (IOException e) {
                        //                    e.printStackTrace();
                        //                }
                        break;
                    }
                }
            }
        }

        ArrayList<DoubleInteriorTransformation> doubleInteriorTransformations = new ArrayList<>();
        doubleInteriorTransformations.add(new TransformationP12());
        doubleInteriorTransformations.add(new TransformationP12());
        doubleInteriorTransformations.add(new TransformationP13());

        InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
        for (DoubleInteriorTransformation t : doubleInteriorTransformations) {
            for (InteriorNode interior1 : interiors) {
                for (InteriorNode interior2 : interiors) {
                    if (!interior1.equals(interior2) && t.isApplicable(graph, interior1, interior2)) {
                        System.out.println("Executing transformation: " + t.getClass().getSimpleName() + " on interiors " + interior1.getLabel() + " and " + interior2.getLabel());
                        t.transform(graph, interior1, interior2);
//                        visualizer = new Visualizer(graph);
//                        visualizer.visualize(new LayerDescriptor(0));
//                        visualizer.visualize(new LayerDescriptor(1));
//                        visualizer.visualize(new LayerDescriptor(2));
//                        visualizer.visualize(new LayerDescriptor(3));
//                        System.out.println("Graph after " + t.getClass().getSimpleName() + " Press any key to continue");
//                        try {
//                            System.in.read();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        break;
                    }
                }
            }
        }
        visualizer = new Visualizer(graph);
        visualizer.visualize(new LayerDescriptor(0));
        visualizer.visualize(new LayerDescriptor(1));
        visualizer.visualize(new LayerDescriptor(2));
        visualizer.visualize(new LayerDescriptor(3));
    }

    private static GraphModel createStartingGraph() {
        final GraphModel graphModel = new GraphModel();
        graphModel.insertStartingInterior("S", new LayerDescriptor(0), new Coordinates(0, 0, 0));
        return graphModel;
    }
}
