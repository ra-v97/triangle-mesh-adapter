package pl.edu.agh.gg.transformations.fixtures;

import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;

public class TransformationP3Fixtures {

    public static GraphModel createLeftSideGraph(Coordinates c1, Coordinates c2, Coordinates c3, Coordinates c4) {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);

        final Vertex v1 = graphModel.insertVertex("V1", c1, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", c2, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", c3, layerDescriptor).get();
        final Vertex v4 = graphModel.insertVertex("V4", c4, layerDescriptor).get();

        graphModel.insertEdge(v1, v2, layerDescriptor);
        graphModel.insertEdge(v1, v4, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);
        graphModel.insertEdge(v3, v4, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }

    public static GraphModel createRightSideGraph(Coordinates c1, Coordinates c2, Coordinates c3, Coordinates c4) {
        // create layer 0
        final GraphModel graphModel = createLeftSideGraph(c1, c2, c3, c4);

        // layer descriptors
        LayerDescriptor layerZeroDescriptor = new LayerDescriptor(0);
        LayerDescriptor layerOneDescriptor = new LayerDescriptor(1);

        // layer 1 vertices
        final Vertex v1 = graphModel.insertVertex("V1", c1, layerOneDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", c2, layerOneDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", c3, layerOneDescriptor).get();
        final Vertex v4 = graphModel.insertVertex("V4", c4, layerOneDescriptor).get();

        // layer 1 edges
        graphModel.insertEdge(v1, v2, layerOneDescriptor);
        graphModel.insertEdge(v1, v4, layerOneDescriptor);
        graphModel.insertEdge(v2, v3, layerOneDescriptor);
        graphModel.insertEdge(v3, v4, layerOneDescriptor);
        graphModel.insertEdge(v2, v4, layerOneDescriptor);

        // interiors
        var layerZeroInterior = (InteriorNode) graphModel.getInteriors().toArray()[0];
        InteriorNode interiorI = graphModel.insertInterior("I", layerOneDescriptor, v1, v2, v4).get();
        InteriorNode interiorII = graphModel.insertInterior("I", layerOneDescriptor, v2, v3, v4).get();

        // layer 0 and 1 edges
        graphModel.insertEdge(layerZeroInterior, interiorI);
        graphModel.insertEdge(layerZeroInterior, interiorII);

        return graphModel;
    }

    public static GraphModel createLeftSideBigGraph(Coordinates c1, Coordinates c2, Coordinates c3, Coordinates c4, Coordinates c5, Coordinates c6) {
        final GraphModel graphModel = new GraphModel();

        // layer descriptors
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);

        // layer 0 vertices
        final Vertex v1 = graphModel.insertVertex("V1", c1, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", c2, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", c3, layerDescriptor).get();
        final Vertex v4 = graphModel.insertVertex("V4", c4, layerDescriptor).get();
        final Vertex v5 = graphModel.insertVertex("V5", c5, layerDescriptor).get();
        final Vertex v6 = graphModel.insertVertex("V6", c6, layerDescriptor).get();

        // layer 0 edges
        graphModel.insertEdge(v1, v2, layerDescriptor);
        graphModel.insertEdge(v1, v4, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);
        graphModel.insertEdge(v3, v4, layerDescriptor);
        graphModel.insertEdge(v2, v5, layerDescriptor);
        graphModel.insertEdge(v3, v5, layerDescriptor);
        graphModel.insertEdge(v4, v6, layerDescriptor);
        graphModel.insertEdge(v1, v6, layerDescriptor);
        graphModel.insertEdge(v3, v6, layerDescriptor);

        // layer 0 interior
        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }

    public static GraphModel createRightSideBigGraph(Coordinates c1, Coordinates c2, Coordinates c3, Coordinates c4, Coordinates c5, Coordinates c6) {
        // create layer 0
        final GraphModel graphModel = createLeftSideBigGraph(c1, c2, c3, c4, c5, c6);

        // layer descriptors
        LayerDescriptor layerZeroDescriptor = new LayerDescriptor(0);
        LayerDescriptor layerOneDescriptor = new LayerDescriptor(1);

        // layer 1 vertices
        final Vertex v1 = graphModel.insertVertex("V1", c1, layerOneDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", c2, layerOneDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", c3, layerOneDescriptor).get();
        final Vertex v4 = graphModel.insertVertex("V4", c4, layerOneDescriptor).get();

        // layer 1 edges
        graphModel.insertEdge(v1, v2, layerOneDescriptor);
        graphModel.insertEdge(v1, v4, layerOneDescriptor);
        graphModel.insertEdge(v2, v3, layerOneDescriptor);
        graphModel.insertEdge(v2, v4, layerOneDescriptor);
        graphModel.insertEdge(v3, v4, layerOneDescriptor);

        // interiors
        var layerZeroInterior = (InteriorNode) graphModel.getInteriors().toArray()[0];
        InteriorNode interiorI = graphModel.insertInterior("I", layerOneDescriptor, v1, v2, v4).get();
        InteriorNode interiorII = graphModel.insertInterior("I", layerOneDescriptor, v2, v3, v4).get();

        // layer 0 and 1 edges
        graphModel.insertEdge(layerZeroInterior, interiorI);
        graphModel.insertEdge(layerZeroInterior, interiorII);

        return graphModel;
    }

    public static GraphModel createLeftSideGraphWithThreeVertices(Coordinates c1, Coordinates c2, Coordinates c3) {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);

        final Vertex v1 = graphModel.insertVertex("V1", c1, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", c2, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", c3, layerDescriptor).get();

        graphModel.insertEdge(v1, v2, layerDescriptor);
        graphModel.insertEdge(v1, v3, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }

    public static GraphModel createLeftSideGraphWithFiveVertices(Coordinates c1, Coordinates c2, Coordinates c3, Coordinates c4, Coordinates c5) {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);

        final Vertex v1 = graphModel.insertVertex("V1", c1, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", c2, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", c3, layerDescriptor).get();
        final Vertex v4 = graphModel.insertVertex("V4", c4, layerDescriptor).get();
        final Vertex v5 = graphModel.insertVertex("V5", c5, layerDescriptor).get();

        graphModel.insertEdge(v1, v2, layerDescriptor);
        graphModel.insertEdge(v1, v4, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);
        graphModel.insertEdge(v3, v4, layerDescriptor);
        graphModel.insertEdge(v3, v5, layerDescriptor);
        graphModel.insertEdge(v1, v5, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }

    public static GraphModel createLeftSideGraphWithTwoAdditionalVertices(Coordinates c1, Coordinates c2, Coordinates c3, Coordinates c4, Coordinates c5) {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);

        final Vertex v1 = graphModel.insertVertex("V1", c1, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", c2, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", c3, layerDescriptor).get();
        final Vertex v4 = graphModel.insertVertex("V4", c4, layerDescriptor).get();
        final Vertex v5 = graphModel.insertVertex("V5", c5, layerDescriptor).get();

        graphModel.insertEdge(v1, v5, layerDescriptor);
        graphModel.insertEdge(v1, v4, layerDescriptor);
        graphModel.insertEdge(v2, v5, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);
        graphModel.insertEdge(v3, v4, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }

    public static GraphModel createValidLeftSideGraph() {
        return createLeftSideGraph(new Coordinates(1, 1, 0),
                new Coordinates(0, 0, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(1, 0, 0));
    }

    public static GraphModel createValidRightSideGraph() {
        return createRightSideGraph(new Coordinates(1, 1, 0),
                new Coordinates(0, 0, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(1, 0, 0));
    }

    public static GraphModel createLeftSideGraphWithAdditionalVertexOnWrongEdge() {
        return createLeftSideGraph(new Coordinates(1, 1, 0),
                new Coordinates(-2, 0, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(1, 0, 0));
    }

    public static GraphModel createValidLeftSideBigGraph() {
        return createLeftSideBigGraph(new Coordinates(1, 1, 0),
                new Coordinates(0, 0, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(1, 0, 0),
                new Coordinates(0, -1, 0),
                new Coordinates(2, 0, 0));
    }

    public static GraphModel createValidRightSideBigGraph() {
        return createRightSideBigGraph(new Coordinates(1, 1, 0),
                new Coordinates(0, 0, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(1, 0, 0),
                new Coordinates(0, -1, 0),
                new Coordinates(2, 0, 0));
    }

}
