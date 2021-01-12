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
        final GraphModel graph = createGraphApplicableForP3();
        List<Transformation> transformations = Arrays.asList(new TransformationP3());

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

    private static void executeTaskB() {
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

    private static GraphModel graphNotApplicableForP5WrongCords(){
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY()+ 0.3, stNoCo.getZ());
        Coordinates v23Co = new Coordinates(stNoCo.getX()+0.3, stNoCo.getY(), stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() +0.3, stNoCo.getY()+ 0.3, stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        final Vertex v12 = graphModel.insertVertex("V12", v12Co, layerDescriptor).get();
        final Vertex v23 = graphModel.insertVertex("V23", v23Co, layerDescriptor).get();
        final Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v12, layerDescriptor);
        graphModel.insertEdge(v12, v2, layerDescriptor);
        graphModel.insertEdge(v2, v23, layerDescriptor);
        graphModel.insertEdge(v23, v3, layerDescriptor);
        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }


    public static  GraphModel testIsApplicableMissingNode() {
        GraphModel graph = graphApplicableForP5();
        Coordinates c = new Coordinates(0.0, 0.5, 0);
        Vertex v = graph.getVertices().stream().filter(x -> x.getCoordinates().equals(c)).findFirst().get();
        graph.removeVertex(v);
        return graph;

    }



    private static GraphModel graphApplicableForP5(){
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY()+ 0.5, stNoCo.getZ());
        Coordinates v23Co = new Coordinates(stNoCo.getX()+0.5, stNoCo.getY(), stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() +0.5, stNoCo.getY()+ 0.5, stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        final Vertex v12 = graphModel.insertVertex("V12", v12Co, layerDescriptor).get();
        final Vertex v23 = graphModel.insertVertex("V23", v23Co, layerDescriptor).get();
        final Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v12, layerDescriptor);
        graphModel.insertEdge(v12, v2, layerDescriptor);
        graphModel.insertEdge(v2, v23, layerDescriptor);
        graphModel.insertEdge(v23, v3, layerDescriptor);
        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }


    private static GraphModel createStartingGraph() {
        final GraphModel graphModel = new GraphModel();
        graphModel.insertStartingInterior("S", new LayerDescriptor(0), new Coordinates(0, 0, 0));
        return graphModel;
    }

    private static GraphModel createGraphApplicableForP3() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX() - 0.5, stNoCo.getY(), stNoCo.getZ()); // TODO: those coordinates will probably change to accommodate the size of the map
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() - 1, stNoCo.getZ());
        Coordinates v4Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        final Vertex v4 = graphModel.insertVertex("V4", v4Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v2, layerDescriptor);
        graphModel.insertEdge(v1, v4, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);
        graphModel.insertEdge(v3, v4, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }

    private static GraphModel createGraphApplicableForP5() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ()); // TODO: those coordinates will probably change to accommodate the size of the map
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY()+ 0.5, stNoCo.getZ());
        Coordinates v23Co = new Coordinates(stNoCo.getX()+0.5, stNoCo.getY(), stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() +0.5, stNoCo.getY()+ 0.5, stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        final Vertex v12 = graphModel.insertVertex("V12", v12Co, layerDescriptor).get();
        final Vertex v23 = graphModel.insertVertex("V23", v23Co, layerDescriptor).get();
        final Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v12, layerDescriptor);
        graphModel.insertEdge(v12, v2, layerDescriptor);
        graphModel.insertEdge(v2, v23, layerDescriptor);
        graphModel.insertEdge(v23, v3, layerDescriptor);
        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }


    private static GraphModel createSimpleGraph() {
        final GraphModel graphModel = new GraphModel();
        final LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        final Vertex v1 = graphModel.insertVertex("V1", new Coordinates(0, 0, 0), layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", new Coordinates(2, 0, 0), layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", new Coordinates(1, 2, 0), layerDescriptor).get();
        final Vertex v4 = graphModel.insertVertex("V4", new Coordinates(0, 4, 0), layerDescriptor).get();
        final Vertex v5 = graphModel.insertVertex("V5", new Coordinates(2, 4, 0), layerDescriptor).get();

        final GraphEdge e1 = graphModel.insertEdge(v1, v2, layerDescriptor).get();
        final GraphEdge e2 = graphModel.insertEdge(v2, v3, layerDescriptor).get();
        final GraphEdge e3 = graphModel.insertEdge(v3, v1, layerDescriptor).get();
        final GraphEdge e4 = graphModel.insertEdge(v3, v4, layerDescriptor).get();
        final GraphEdge e5 = graphModel.insertEdge(v3, v5, layerDescriptor).get();
        final GraphEdge e6 = graphModel.insertEdge(v4, v5, layerDescriptor).get();

        final InteriorNode i1 = graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        final InteriorNode i2 = graphModel.insertInterior("I", layerDescriptor, v3, v4, v5).get();

        //graphModel.removeInterior(i1);
        graphModel.removeVertex(v1);
        return graphModel;
    }

    private static GraphModel createMultiLayerGraph() {
        final GraphModel graphModel = new GraphModel();
        final LayerDescriptor layerDescriptor0 = new LayerDescriptor(0);
        final LayerDescriptor layerDescriptor1 = layerDescriptor0.getNextLayerDescriptor();

        final Vertex v1 = graphModel.insertVertex("V1", new Coordinates(0, 0, 0), layerDescriptor0).get();
        final Vertex v2 = graphModel.insertVertex("V2", new Coordinates(2, 0, 0), layerDescriptor0).get();
        final Vertex v3 = graphModel.insertVertex("V3", new Coordinates(1, 2, 0), layerDescriptor0).get();
        final Vertex v4 = graphModel.insertVertex("V4", new Coordinates(0, 2, 1), layerDescriptor1).get();
        final Vertex v5 = graphModel.insertVertex("V5", new Coordinates(3, 1, 1), layerDescriptor1).get();
        final Vertex v6 = graphModel.insertVertex("V6", new Coordinates(3, 3, 1), layerDescriptor1).get();

        final GraphEdge e1 = graphModel.insertEdge(v1, v2, layerDescriptor0).get();
        final GraphEdge e2 = graphModel.insertEdge(v2, v3, layerDescriptor0).get();
        final GraphEdge e3 = graphModel.insertEdge(v3, v1, layerDescriptor0).get();
        final GraphEdge e4 = graphModel.insertEdge(v4, v5, layerDescriptor1).get();
        final GraphEdge e5 = graphModel.insertEdge(v5, v6, layerDescriptor1).get();
        final GraphEdge e6 = graphModel.insertEdge(v6, v4, layerDescriptor1).get();

        final InteriorNode i1 = graphModel.insertInterior("I1", layerDescriptor0, v1, v2, v3).get();
        final InteriorNode i2 = graphModel.insertInterior("I2", layerDescriptor1, v4, v5, v6).get();

        final GraphEdge ei = graphModel.insertEdge(i1, i2).get();
        //graphModel.removeInterior(i1.getUUID());
        graphModel.removeEdge(e1);
        return graphModel;
    }
}
