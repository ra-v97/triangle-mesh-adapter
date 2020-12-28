package transformations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphEdge;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;
import pl.edu.agh.gg.transformations.TransformationP3;
import utils.GraphTestUtils;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TransformationP3Test {

    private TransformationP3 transformation;

    private GraphModel graphModel;

    private InteriorNode initialInteriorNode;

    @BeforeEach
    private void initializeTestCase() {
        transformation = new TransformationP3();
        graphModel = createValidLeftSideGraph();
        initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);
    }

    private void reloadGraphModel(GraphModel model) {
        graphModel = model;
        initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);
    }

    private static GraphModel createLeftSideGraph(Coordinates c1, Coordinates c2, Coordinates c3, Coordinates c4) {
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

    private static GraphModel createLeftSideGraphWithThreeVertices(Coordinates c1, Coordinates c2, Coordinates c3) {
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

    private static GraphModel createLeftSideGraphWithFivelVertices(Coordinates c1, Coordinates c2, Coordinates c3, Coordinates c4, Coordinates c5) {
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

    private static GraphModel createLeftSideGraphWithTwoAdditionalVertices(Coordinates c1, Coordinates c2, Coordinates c3, Coordinates c4, Coordinates c5) {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);

        final Vertex v1 = graphModel.insertVertex("V1", c1, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", c2, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", c3, layerDescriptor).get();
        final Vertex v4 = graphModel.insertVertex("V4", c4, layerDescriptor).get();
        final Vertex v5 = graphModel.insertVertex("V5", c5, layerDescriptor).get();

        graphModel.insertEdge(v1, v2, layerDescriptor);
        graphModel.insertEdge(v1, v4, layerDescriptor);
        graphModel.insertEdge(v2, v5, layerDescriptor);
        graphModel.insertEdge(v3, v5, layerDescriptor);
        graphModel.insertEdge(v3, v4, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }

    private static GraphModel createValidLeftSideGraph() {
        return createLeftSideGraph(new Coordinates(1, 1, 0),
                new Coordinates(0, 0, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(1, 0, 0));
    }

    private static GraphModel createLeftSideGraphWithAdditionalVertexOnWrongEdge() {
        return createLeftSideGraph(new Coordinates(1, 1, 0),
                new Coordinates(-2, 0, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(1, 0, 0));
    }

    @Test
    void transformationShouldExecuteForCorrectLeftSide() {
        // Then
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldExecuteWhenDifferentVerticesOrder() {
        // When
        reloadGraphModel(createLeftSideGraph(
                new Coordinates(1, -1, 0),
                new Coordinates(0, 0, 0),
                new Coordinates(1, 1, 0),
                new Coordinates(1, 0, 0)));
        // Then
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenInteriorLabelIsLower() {
        // When
        initialInteriorNode.setLabel("i");
        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteAfterRemovingAdjacentVertexFromLeftSide() {
        // When
        graphModel.removeVertex(graphModel.getVertices()
                .stream()
                .filter(vertex -> vertex.getCoordinates().equals(new Coordinates(1, 1, 0))).findFirst().get());
        // Then
        assertThrows(NoSuchElementException.class, () -> transformation.transform(graphModel, initialInteriorNode)); // No interior present
    }

    @Test
    void transformationShouldNotExecuteAfterRemovingFourthVertexFromLeftSide() {
        // When
        graphModel.removeVertex(graphModel.getVertices()
                .stream()
                .filter(vertex -> vertex.getCoordinates().equals(new Coordinates(1, 0, 0))).findFirst().get());
        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteAfterRemovingEdgeFromLeftSide() {
        // When
        graphModel.removeEdge(graphModel.getEdges().stream()
                .filter(edge -> edge.getType() == GraphEdge.GraphEdgeType.VERTEX_VERTEX).findFirst().get().getUUID());
        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenAdditionalVertexIsNotOnTheLongestEdge() {
        // When
        reloadGraphModel(createLeftSideGraphWithAdditionalVertexOnWrongEdge());
        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenAdditionalVertexIsNotInTheMiddle() {
        // When
        reloadGraphModel(createLeftSideGraph(new Coordinates(1, 1, 0),
                new Coordinates(-2, 0, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(0.5, 0, 0)));
        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenAdditionalVertexOverlapsWithAdjVertex() {
        // When
        reloadGraphModel(createLeftSideGraphWithThreeVertices(new Coordinates(1, 1, 0),
                new Coordinates(1, 0, 0),
                new Coordinates(1, -1, 0)));
        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenTwoAdditionalVerticesArePresent() {
        // When
        reloadGraphModel(createLeftSideGraphWithTwoAdditionalVertices(new Coordinates(1, 1, 0),
                new Coordinates(0, 0, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(1, 0, 0),
                new Coordinates(0.5, 0.5, 0)));
        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenWrongZCord() {
        // When
        reloadGraphModel(createLeftSideGraph(
                new Coordinates(1, -1, 1),
                new Coordinates(0, 0, 0),
                new Coordinates(1, 1, 0),
                new Coordinates(1, 0, 0)));
        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldExecuteWhenGraphIsBiggerButValid() {
        // When
        reloadGraphModel(createLeftSideGraphWithFivelVertices(new Coordinates(1, 1, 0),
                new Coordinates(0, 0, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(1, 0, 0),
                new Coordinates(2, 0, 0)));
        // Then
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
    }
}
