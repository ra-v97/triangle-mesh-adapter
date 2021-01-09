package pl.edu.agh.gg.transformations.fixtures;

import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.StartingNode;
import pl.edu.agh.gg.model.Vertex;

public class TransformationP6Fixtures {

    public static GraphModel validGraphForProduction() {
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
        Vertex v2_8 = graphModel.insertVertex("E2_8", new Coordinates(0, 0, 200), layer2).get();

        InteriorNode i2_1 = graphModel.insertInterior("I2_1", layer2, v2_1, v2_5, v2_6).get();
        InteriorNode i2_2 = graphModel.insertInterior("I2_2", layer2, v2_1, v2_2, v2_6).get();
        InteriorNode i2_3 = graphModel.insertInterior("I2_3", layer2, v2_8, v2_3, v2_7).get();
        InteriorNode i2_4 = graphModel.insertInterior("I2_4", layer2, v2_3, v2_4, v2_7).get();

        //edges between layer 1 and 2
        graphModel.insertEdge(i1_1, i2_1);
        graphModel.insertEdge(i1_1, i2_2);
        graphModel.insertEdge(i1_2, i2_3);
        graphModel.insertEdge(i1_2, i2_4);

        //edges in layer 2
        graphModel.insertEdge(v2_1, v2_2, layer2);
        graphModel.insertEdge(v2_1, v2_5, layer2);
        graphModel.insertEdge(v2_8, v2_3, layer2);
        graphModel.insertEdge(v2_3, v2_4, layer2);
        graphModel.insertEdge(v2_4, v2_7, layer2);
        graphModel.insertEdge(v2_7, v2_8, layer2);
        graphModel.insertEdge(v2_2, v2_6, layer2);
        graphModel.insertEdge(v2_5, v2_6, layer2);
        graphModel.insertEdge(v2_1, v2_6, layer2);
        graphModel.insertEdge(v2_3, v2_7, layer2);

        return graphModel;
    }

    public static GraphModel graphForP7Transformation() {
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



    public static GraphModel graphWithMissingInterior() {
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
        Vertex v2_8 = graphModel.insertVertex("E2_8", new Coordinates(0, 0, 200), layer2).get();

        InteriorNode i2_1 = graphModel.insertInterior("I2_1", layer2, v2_1, v2_5, v2_6).get();
        //InteriorNode i2_2 = graphModel.insertInterior("I2_2", layer2, v2_1, v2_2, v2_6).get();
        InteriorNode i2_3 = graphModel.insertInterior("I2_3", layer2, v2_8, v2_3, v2_7).get();
        InteriorNode i2_4 = graphModel.insertInterior("I2_4", layer2, v2_3, v2_4, v2_7).get();

        //edges between layer 1 and 2
        graphModel.insertEdge(i1_1, i2_1);
        //graphModel.insertEdge(i1_1, i2_2);
        graphModel.insertEdge(i1_2, i2_3);
        graphModel.insertEdge(i1_2, i2_4);

        //edges in layer 2
        graphModel.insertEdge(v2_1, v2_2, layer2);
        graphModel.insertEdge(v2_1, v2_5, layer2);
        graphModel.insertEdge(v2_8, v2_3, layer2);
        graphModel.insertEdge(v2_3, v2_4, layer2);
        graphModel.insertEdge(v2_4, v2_7, layer2);
        graphModel.insertEdge(v2_7, v2_8, layer2);
        graphModel.insertEdge(v2_2, v2_6, layer2);
        graphModel.insertEdge(v2_5, v2_6, layer2);

        return graphModel;
    }

    public static GraphModel validGraphAsSubgraphForProduction() {
        GraphModel graphModel = new GraphModel();

        //layer 0
        LayerDescriptor layer0 = new LayerDescriptor(0);
        StartingNode startingNode = graphModel.insertStartingInterior("e", layer0, new Coordinates(50, 50, 0));

        // Layer 1a
        LayerDescriptor layer1a = new LayerDescriptor(1);
        Vertex v1a_1 = graphModel.insertVertex("E1a_1", new Coordinates(0, 0, 50), layer1a).get();
        Vertex v1a_2 = graphModel.insertVertex("E1a_2", new Coordinates(100, 0, 50), layer1a).get();
        Vertex v1a_3 = graphModel.insertVertex("E1a_3", new Coordinates(0, 100, 50), layer1a).get();

        InteriorNode i1a_1 = graphModel.insertInterior("i1a_1", layer1a, v1a_1, v1a_2, v1a_3).get();
        graphModel.insertEdge(startingNode, i1a_1);

        //layer 1
        LayerDescriptor layer1 = new LayerDescriptor(2);
        Vertex v1_1 = graphModel.insertVertex("E1_1", new Coordinates(0, 100, 100), layer1).get();
        Vertex v1_2 = graphModel.insertVertex("E1_2", new Coordinates(0, 0, 100), layer1).get();
        Vertex v1_3 = graphModel.insertVertex("E1_2", new Coordinates(100, 0, 100), layer1).get();
        Vertex v1_4 = graphModel.insertVertex("E1_2", new Coordinates(100, 100, 100), layer1).get();

        InteriorNode i1_1 = graphModel.insertInterior("i1_1", layer1, v1_1, v1_2, v1_4).get();
        InteriorNode i1_2 = graphModel.insertInterior("i1_2", layer1, v1_2, v1_3, v1_4).get();

        //edges between layer 0 and 1
        graphModel.insertEdge(i1a_1, i1_1);
        graphModel.insertEdge(i1a_1, i1_2);

        //edges in layer 1
        graphModel.insertEdge(v1_1, v1_2, layer1);
        graphModel.insertEdge(v1_2, v1_3, layer1);
        graphModel.insertEdge(v1_3, v1_4, layer1);
        graphModel.insertEdge(v1_4, v1_1, layer1);
        graphModel.insertEdge(v1_2, v1_4, layer1);

        //layer 2
        LayerDescriptor layer2 = new LayerDescriptor(3);
        Vertex v2_1 = graphModel.insertVertex("E2_1", new Coordinates(0, 100, 200), layer2).get();
        Vertex v2_2 = graphModel.insertVertex("E2_2", new Coordinates(0, 0, 200), layer2).get();
        Vertex v2_3 = graphModel.insertVertex("E2_3", new Coordinates(100, 0, 200), layer2).get();
        Vertex v2_4 = graphModel.insertVertex("E2_4", new Coordinates(100, 100, 200), layer2).get();
        Vertex v2_5 = graphModel.insertVertex("E2_5", new Coordinates(100, 100, 200), layer2).get();
        Vertex v2_6 = graphModel.insertVertex("E2_6", new Coordinates(50, 50, 200), layer2).get();
        Vertex v2_7 = graphModel.insertVertex("E2_7", new Coordinates(50, 50, 200), layer2).get();
        Vertex v2_8 = graphModel.insertVertex("E2_8", new Coordinates(0, 0, 200), layer2).get();

        InteriorNode i2_1 = graphModel.insertInterior("I2_1", layer2, v2_1, v2_5, v2_6).get();
        InteriorNode i2_2 = graphModel.insertInterior("I2_2", layer2, v2_1, v2_2, v2_6).get();
        InteriorNode i2_3 = graphModel.insertInterior("I2_3", layer2, v2_8, v2_3, v2_7).get();
        InteriorNode i2_4 = graphModel.insertInterior("I2_4", layer2, v2_3, v2_4, v2_7).get();

        //edges between layer 1 and 2
        graphModel.insertEdge(i1_1, i2_1);
        graphModel.insertEdge(i1_1, i2_2);
        graphModel.insertEdge(i1_2, i2_3);
        graphModel.insertEdge(i1_2, i2_4);

        //edges in layer 2
        graphModel.insertEdge(v2_1, v2_2, layer2);
        graphModel.insertEdge(v2_1, v2_5, layer2);
        graphModel.insertEdge(v2_8, v2_3, layer2);
        graphModel.insertEdge(v2_3, v2_4, layer2);
        graphModel.insertEdge(v2_4, v2_7, layer2);
        graphModel.insertEdge(v2_7, v2_8, layer2);
        graphModel.insertEdge(v2_2, v2_6, layer2);
        graphModel.insertEdge(v2_5, v2_6, layer2);

        return graphModel;
    }

    public static GraphModel graphWithMissingEdge() {
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
        Vertex v2_8 = graphModel.insertVertex("E2_8", new Coordinates(0, 0, 200), layer2).get();

        InteriorNode i2_1 = graphModel.insertInterior("I2_1", layer2, v2_1, v2_5, v2_6).get();
        InteriorNode i2_2 = graphModel.insertInterior("I2_2", layer2, v2_1, v2_2, v2_6).get();
        InteriorNode i2_3 = graphModel.insertInterior("I2_3", layer2, v2_8, v2_3, v2_7).get();
        InteriorNode i2_4 = graphModel.insertInterior("I2_4", layer2, v2_3, v2_4, v2_7).get();

        //edges between layer 1 and 2
        graphModel.insertEdge(i1_1, i2_1);
        //graphModel.insertEdge(i1_1, i2_2);
        graphModel.insertEdge(i1_2, i2_3);
        graphModel.insertEdge(i1_2, i2_4);

        //edges in layer 2
        graphModel.insertEdge(v2_1, v2_2, layer2);
        graphModel.insertEdge(v2_1, v2_5, layer2);
        graphModel.insertEdge(v2_8, v2_3, layer2);
        graphModel.insertEdge(v2_3, v2_4, layer2);
        graphModel.insertEdge(v2_4, v2_7, layer2);
        graphModel.insertEdge(v2_7, v2_8, layer2);
        graphModel.insertEdge(v2_2, v2_6, layer2);
        graphModel.insertEdge(v2_5, v2_6, layer2);

        return graphModel;
    }

    public static GraphModel graphWithInvalidCoordinatesSameMiddle() {
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
        Vertex v2_4 = graphModel.insertVertex("E2_4", new Coordinates(100, 90, 200), layer2).get();
        Vertex v2_5 = graphModel.insertVertex("E2_5", new Coordinates(100, 100, 200), layer2).get();
        Vertex v2_6 = graphModel.insertVertex("E2_6", new Coordinates(50, 50, 200), layer2).get();
        Vertex v2_7 = graphModel.insertVertex("E2_7", new Coordinates(50, 50, 200), layer2).get();
        Vertex v2_8 = graphModel.insertVertex("E2_8", new Coordinates(0, 10, 200), layer2).get();

        InteriorNode i2_1 = graphModel.insertInterior("I2_1", layer2, v2_1, v2_5, v2_6).get();
        InteriorNode i2_2 = graphModel.insertInterior("I2_2", layer2, v2_1, v2_2, v2_6).get();
        InteriorNode i2_3 = graphModel.insertInterior("I2_3", layer2, v2_8, v2_3, v2_7).get();
        InteriorNode i2_4 = graphModel.insertInterior("I2_4", layer2, v2_3, v2_4, v2_7).get();

        //edges between layer 1 and 2
        graphModel.insertEdge(i1_1, i2_1);
        graphModel.insertEdge(i1_1, i2_2);
        graphModel.insertEdge(i1_2, i2_3);
        graphModel.insertEdge(i1_2, i2_4);

        //edges in layer 2
        graphModel.insertEdge(v2_1, v2_2, layer2);
        graphModel.insertEdge(v2_1, v2_5, layer2);
        graphModel.insertEdge(v2_8, v2_3, layer2);
        graphModel.insertEdge(v2_3, v2_4, layer2);
        graphModel.insertEdge(v2_4, v2_7, layer2);
        graphModel.insertEdge(v2_7, v2_8, layer2);
        graphModel.insertEdge(v2_2, v2_6, layer2);
        graphModel.insertEdge(v2_5, v2_6, layer2);


        return graphModel;
    }

    public static GraphModel graphWithInvalidTag() {
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
        Vertex v2_8 = graphModel.insertVertex("E2_8", new Coordinates(0, 10, 200), layer2).get();

        Vertex i2_1 = graphModel.insertVertex("I2_1", new Coordinates(50, 50, 200), layer2).get();
        graphModel.insertEdge(i2_1, v2_1, layer2);
        graphModel.insertEdge(i2_1, v2_5, layer2);
        graphModel.insertEdge(i2_1, v2_6, layer2);

        InteriorNode i2_2 = graphModel.insertInterior("I2_2", layer2, v2_1, v2_2, v2_6).get();
        InteriorNode i2_3 = graphModel.insertInterior("I2_3", layer2, v2_8, v2_3, v2_7).get();
        InteriorNode i2_4 = graphModel.insertInterior("I2_4", layer2, v2_3, v2_4, v2_7).get();

        //edges between layer 1 and 2
        graphModel.insertEdge(i1_1, i2_2);
        graphModel.insertEdge(i1_2, i2_3);
        graphModel.insertEdge(i1_2, i2_4);

        //edges in layer 2
        graphModel.insertEdge(v2_1, v2_2, layer2);
        graphModel.insertEdge(v2_1, v2_5, layer2);
        graphModel.insertEdge(v2_8, v2_3, layer2);
        graphModel.insertEdge(v2_3, v2_4, layer2);
        graphModel.insertEdge(v2_4, v2_7, layer2);
        graphModel.insertEdge(v2_7, v2_8, layer2);
        graphModel.insertEdge(v2_2, v2_6, layer2);
        graphModel.insertEdge(v2_5, v2_6, layer2);

        return graphModel;
    }

    public static GraphModel transformedGraphAsSubgraph() {
        GraphModel graphModel = new GraphModel();

        //layer 0
        LayerDescriptor layer0 = new LayerDescriptor(0);
        StartingNode startingNode = graphModel.insertStartingInterior("e", layer0, new Coordinates(50, 50, 0));

        // Layer 1a
        LayerDescriptor layer1a = new LayerDescriptor(1);
        Vertex v1a_1 = graphModel.insertVertex("E1a_1", new Coordinates(0, 0, 50), layer1a).get();
        Vertex v1a_2 = graphModel.insertVertex("E1a_2", new Coordinates(100, 0, 50), layer1a).get();
        Vertex v1a_3 = graphModel.insertVertex("E1a_3", new Coordinates(0, 100, 50), layer1a).get();

        InteriorNode i1a_1 = graphModel.insertInterior("i1a_1", layer1a, v1a_1, v1a_2, v1a_3).get();
        graphModel.insertEdge(startingNode, i1a_1);

        //layer 1
        LayerDescriptor layer1 = new LayerDescriptor(2);
        Vertex v1_1 = graphModel.insertVertex("E1_1", new Coordinates(0, 100, 100), layer1).get();
        Vertex v1_2 = graphModel.insertVertex("E1_2", new Coordinates(0, 0, 100), layer1).get();
        Vertex v1_3 = graphModel.insertVertex("E1_2", new Coordinates(100, 0, 100), layer1).get();
        Vertex v1_4 = graphModel.insertVertex("E1_2", new Coordinates(100, 100, 100), layer1).get();

        InteriorNode i1_1 = graphModel.insertInterior("i1_1", layer1, v1_1, v1_2, v1_4).get();
        InteriorNode i1_2 = graphModel.insertInterior("i1_2", layer1, v1_2, v1_3, v1_4).get();

        //edges between layer 0 and 1
        graphModel.insertEdge(i1a_1, i1_1);
        graphModel.insertEdge(i1a_1, i1_2);

        //edges in layer 1
        graphModel.insertEdge(v1_1, v1_2, layer1);
        graphModel.insertEdge(v1_2, v1_3, layer1);
        graphModel.insertEdge(v1_3, v1_4, layer1);
        graphModel.insertEdge(v1_4, v1_1, layer1);
        graphModel.insertEdge(v1_2, v1_4, layer1);

        //layer 2
        LayerDescriptor layer2 = new LayerDescriptor(3);
        Vertex v2_1 = graphModel.insertVertex("E2_1", new Coordinates(0, 100, 200), layer2).get();
        Vertex v2_2 = graphModel.insertVertex("E2_2__merged", new Coordinates(0, 0, 200), layer2).get();
        Vertex v2_3 = graphModel.insertVertex("E2_3", new Coordinates(100, 0, 200), layer2).get();
        Vertex v2_5m = graphModel.insertVertex("E2_4__merged", new Coordinates(100, 100, 200), layer2).get();
        Vertex v2_6m = graphModel.insertVertex("E2_7__merged", new Coordinates(50, 50, 200), layer2).get();

        InteriorNode i2_1 = graphModel.insertInterior("I2_1", layer2, v2_1, v2_6m, v2_5m).get();
        InteriorNode i2_2 = graphModel.insertInterior("I2_2", layer2, v2_1, v2_2, v2_6m).get();
        InteriorNode i2_3 = graphModel.insertInterior("I2_3", layer2, v2_2, v2_3, v2_6m).get();
        InteriorNode i2_4 = graphModel.insertInterior("I2_4", layer2, v2_3, v2_6m, v2_5m).get();

        //edges between layer 1 and 2
        graphModel.insertEdge(i1_1, i2_1);
        graphModel.insertEdge(i1_1, i2_2);
        graphModel.insertEdge(i1_2, i2_3);
        graphModel.insertEdge(i1_2, i2_4);

        //edges in layer 2
        graphModel.insertEdge(v2_1, v2_2, layer2);
        graphModel.insertEdge(v2_1, v2_5m, layer2);
        graphModel.insertEdge(v2_2, v2_3, layer2);
        graphModel.insertEdge(v2_3, v2_5m, layer2);
        graphModel.insertEdge(v2_2, v2_6m, layer2);
        graphModel.insertEdge(v2_5m, v2_6m, layer2);

        return graphModel;
    }

    public static GraphModel validResult() {
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
        LayerDescriptor layer2 = new LayerDescriptor(3);
        Vertex v2_1 = graphModel.insertVertex("E2_1", new Coordinates(0, 100, 200), layer2).get();
        Vertex v2_2 = graphModel.insertVertex("E2_2__merged", new Coordinates(0, 0, 200), layer2).get();
        Vertex v2_3 = graphModel.insertVertex("E2_3", new Coordinates(100, 0, 200), layer2).get();
        Vertex v2_5m = graphModel.insertVertex("E2_4__merged", new Coordinates(100, 100, 200), layer2).get();
        Vertex v2_6m = graphModel.insertVertex("E2_7__merged", new Coordinates(50, 50, 200), layer2).get();

        InteriorNode i2_1 = graphModel.insertInterior("I2_1", layer2, v2_1, v2_6m, v2_5m).get();
        InteriorNode i2_2 = graphModel.insertInterior("I2_2", layer2, v2_1, v2_2, v2_6m).get();
        InteriorNode i2_3 = graphModel.insertInterior("I2_3", layer2, v2_2, v2_3, v2_6m).get();
        InteriorNode i2_4 = graphModel.insertInterior("I2_4", layer2, v2_3, v2_6m, v2_5m).get();

        //edges between layer 1 and 2
        graphModel.insertEdge(i1_1, i2_1);
        graphModel.insertEdge(i1_1, i2_2);
        graphModel.insertEdge(i1_2, i2_3);
        graphModel.insertEdge(i1_2, i2_4);

        //edges in layer 2
        graphModel.insertEdge(v2_1, v2_2, layer2);
        graphModel.insertEdge(v2_1, v2_5m, layer2);
        graphModel.insertEdge(v2_2, v2_3, layer2);
        graphModel.insertEdge(v2_3, v2_5m, layer2);
        graphModel.insertEdge(v2_2, v2_6m, layer2);
        graphModel.insertEdge(v2_5m, v2_6m, layer2);

        return graphModel;
    }
}