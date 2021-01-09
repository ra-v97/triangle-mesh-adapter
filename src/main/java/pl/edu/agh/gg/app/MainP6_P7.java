package pl.edu.agh.gg.app;

import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.*;
import pl.edu.agh.gg.transformations.*;
import pl.edu.agh.gg.visualization.Visualizer;

import java.util.Arrays;
import java.util.List;


public class MainP6_P7 {
    public static void main(String[] args) {
        final GraphModel graph = createStartingGraph();
        Visualizer startVisualizer = new Visualizer(graph);
        startVisualizer.visualize(new LayerDescriptor(0));
        startVisualizer.visualize(new LayerDescriptor(1));
        startVisualizer.visualize(new LayerDescriptor(2));
        List<Transformation> transformations = Arrays.asList(new TransformationP1(), new TransformationP2());
        List<DoubleInteriorTransformation> doubleInteriorTransformations = Arrays.asList(new TransformationP6(), new TransformationP7());

        for (int i = 0; i < 2; i ++) { // this will be replaced with a do-while loop when we have the logic for refining the triangles
            InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
//            for (InteriorNode interior : interiors) {
//                for (Transformation t : transformations) {
//                    if (t.isApplicable(graph, interior)) {
//                        System.out.println("Executing transformation: " + t.getClass().getSimpleName() + " on interior" + interior.getLabel());
//                        t.transform(graph, interior);
//                    }
//                }
//            }

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
        }

//        System.out.println(graph.getInteriors().size());
//        System.out.print(graph.getVertices().size());

        Visualizer visualizer = new Visualizer(graph);
        visualizer.visualize(new LayerDescriptor(0));
        visualizer.visualize(new LayerDescriptor(1));
        visualizer.visualize(new LayerDescriptor(2));
    }

    private static GraphModel createStartingGraph() {
        GraphModel graphModel = new GraphModel();

        //layer 0
        LayerDescriptor layer0 = new LayerDescriptor(0);
        StartingNode startingNode = graphModel.insertStartingInterior("e", layer0, new Coordinates(50, 50, 0));

        //layer 1
        LayerDescriptor layer1 = new LayerDescriptor(1);
        Vertex v1_1 = graphModel.insertVertex("E1_1", new Coordinates(0, 100, 100), layer1).get();
        Vertex v1_2 = graphModel.insertVertex("E1_2", new Coordinates(0, 0, 100), layer1).get();
        Vertex v1_3 = graphModel.insertVertex("E1_2", new Coordinates(100, 0, 100), layer1).get();
        Vertex v1_4 = graphModel.insertVertex("E1_2", new Coordinates(100, 100, 100), layer1).get();

        InteriorNode i1_1 = graphModel.insertInterior("i1_1", layer1, v1_1, v1_2, v1_4).get();
        InteriorNode i1_2 = graphModel.insertInterior("i1_2", layer1, v1_2, v1_3, v1_4).get();

        //edges between layer 0 and 1
        graphModel.insertEdge(startingNode, i1_1);
        graphModel.insertEdge(startingNode, i1_2);

        //edges in layer 1
        graphModel.insertEdge(v1_1, v1_2, layer1);
        graphModel.insertEdge(v1_2, v1_3, layer1);
        graphModel.insertEdge(v1_3, v1_4, layer1);
        graphModel.insertEdge(v1_4, v1_1, layer1);
        graphModel.insertEdge(v1_2, v1_4, layer1);

        //layer 2
        LayerDescriptor layer2 = new LayerDescriptor(2);
        Vertex v2_1 = graphModel.insertVertex("E2_1", new Coordinates(0, 100, 200), layer2).get();
        Vertex v2_2 = graphModel.insertVertex("E2_2", new Coordinates(0, 0, 200), layer2).get();
        Vertex v2_3 = graphModel.insertVertex("E2_3", new Coordinates(100, 0, 200), layer2).get();
        Vertex v2_4 = graphModel.insertVertex("E2_4", new Coordinates(100, 100, 200), layer2).get();
        Vertex v2_5 = graphModel.insertVertex("E2_5", new Coordinates(100, 100, 200), layer2).get();
        Vertex v2_6 = graphModel.insertVertex("E2_6", new Coordinates(50, 50, 200), layer2).get();
        Vertex v2_7 = graphModel.insertVertex("E2_7", new Coordinates(50, 50, 200), layer2).get();

        InteriorNode i2_1 = graphModel.insertInterior("I2_1", layer2, v2_1, v2_5, v2_6).get();
        InteriorNode i2_2 = graphModel.insertInterior("I2_2", layer2, v2_1, v2_2, v2_6).get();
        InteriorNode i2_3 = graphModel.insertInterior("I2_3", layer2, v2_2, v2_3, v2_7).get();
        InteriorNode i2_4 = graphModel.insertInterior("I2_4", layer2, v2_3, v2_4, v2_7).get();

        //edges between layer 1 and 2
        graphModel.insertEdge(i1_1, i2_1);
        graphModel.insertEdge(i1_1, i2_2);
        graphModel.insertEdge(i1_2, i2_3);
        graphModel.insertEdge(i1_2, i2_4);

        //edges in layer 2
        graphModel.insertEdge(v2_1, v2_2, layer2);
        graphModel.insertEdge(v2_1, v2_5, layer2);
        graphModel.insertEdge(v2_2, v2_3, layer2);
        graphModel.insertEdge(v2_3, v2_4, layer2);
        graphModel.insertEdge(v2_4, v2_7, layer2);
        graphModel.insertEdge(v2_7, v2_2, layer2);
        graphModel.insertEdge(v2_2, v2_6, layer2);
        graphModel.insertEdge(v2_5, v2_6, layer2);
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
