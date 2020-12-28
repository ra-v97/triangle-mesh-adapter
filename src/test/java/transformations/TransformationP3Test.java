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

import java.util.Collection;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TransformationP3Test {

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
        // Given
        var transformation = new TransformationP3();
        var graphModel = createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldExecuteWhenDifferentVerticesOrder() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createLeftSideGraph(
                new Coordinates(1, -1, 0),
                new Coordinates(0, 0, 0),
                new Coordinates(1, 1, 0),
                new Coordinates(1, 0, 0));
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenInteriorLabelIsLower() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // When
        initialInteriorNode.setLabel("i");

        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteAfterRemovingAdjacentVertexFromLeftSide() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // When
        graphModel.removeVertex(graphModel.getVertices()
                .stream()
                .filter(vertex -> vertex.getCoordinates().equals(new Coordinates(1, 1, 0))).findFirst().get());
        // Then
        assertThrows(NoSuchElementException.class, () -> transformation.transform(graphModel, initialInteriorNode)); // No interior present
    }

    @Test
    void transformationShouldNotExecuteAfterRemovingFourthVertexFromLeftSide() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // When
        graphModel.removeVertex(graphModel.getVertices()
                .stream()
                .filter(vertex -> vertex.getCoordinates().equals(new Coordinates(1, 0, 0))).findFirst().get());
        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteAfterRemovingEdgeFromLeftSide() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // When
        graphModel.removeEdge(graphModel.getEdges().stream()
                .filter(edge -> edge.getType() == GraphEdge.GraphEdgeType.VERTEX_VERTEX).findFirst().get().getUUID());
        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenAdditionalVertexIsNotOnTheLongestEdge() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createLeftSideGraphWithAdditionalVertexOnWrongEdge();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenAdditionalVertexIsNotInTheMiddle() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createLeftSideGraph(new Coordinates(1, 1, 0),
                new Coordinates(-2, 0, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(0.5, 0, 0));
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenAdditionalVertexOverlapsWithAdjVertex() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createLeftSideGraphWithThreeVertices(new Coordinates(1, 1, 0),
                new Coordinates(1, 0, 0),
                new Coordinates(1, -1, 0));
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenTwoAdditionalVerticesArePresent() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createLeftSideGraphWithTwoAdditionalVertices(new Coordinates(1, 1, 0),
                new Coordinates(0, 0, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(1, 0, 0),
                new Coordinates(0.5, 0.5, 0));
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenWrongZCord() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createLeftSideGraph(
                new Coordinates(1, -1, 1),
                new Coordinates(0, 0, 0),
                new Coordinates(1, 1, 0),
                new Coordinates(1, 0, 0));
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldExecuteWhenGraphIsBiggerButValid() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createLeftSideGraphWithFivelVertices(new Coordinates(1, 1, 0),
                new Coordinates(0, 0, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(1, 0, 0),
                new Coordinates(2, 0, 0));
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void baseTransformationNumberOfInteriors() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // When
        transformation.transform(graphModel, initialInteriorNode);

        // Then
        assertEquals(3, graphModel.getInteriors().toArray().length);
    }

    @Test
    void baseTransformationNumberOfEdgesBeforeTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertEquals(7, graphModel.getEdges().toArray().length);
    }

    @Test
    void baseTransformationNumberOfEdgesAfterTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // When
        transformation.transform(graphModel, initialInteriorNode);

        // Then
        assertEquals(20, graphModel.getEdges().toArray().length);
    }

    @Test
    void baseTransformationNumberOfInteriorsBeforeTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertEquals(1, graphModel.getInteriors().toArray().length);
    }

    @Test
    void baseTransformationNumberOfInteriorsAfterTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // When
        transformation.transform(graphModel, initialInteriorNode);

        // Then
        assertEquals(3, graphModel.getInteriors().toArray().length);
    }

    @Test
    void baseTransformationNumberOfNodesBeforeTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertEquals(4, graphModel.getVertices().toArray().length);
    }

    @Test
    void baseTransformationNumberOfNodesAfterTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // When
        transformation.transform(graphModel, initialInteriorNode);

        // Then
        assertEquals(8, graphModel.getVertices().toArray().length);
    }

    @Test
    void baseTransformationNumberOfNodesAfterTransformationXD() {
        var graphModel = createValidLeftSideGraph();
        assertTrue(isEqual(graphModel, graphModel));
    }

    static boolean isEqual(GraphModel g1, GraphModel g2) {
        if (g2 == null) {
            return false;
        }
        Collection<Vertex> vl1 = g1.getVertices();
        Collection<Vertex> vl2 = g2.getVertices();
        Collection<InteriorNode> il1 = g1.getInteriors();
        Collection<InteriorNode> il2 = g2.getInteriors();
        Collection<GraphEdge> el1 = g1.getEdges();
        Collection<GraphEdge> el2 = g2.getEdges();

        for (Vertex v1 : vl1) {
            boolean found = false;
            for (Vertex v2 : vl2) {
                if (v1.getCoordinates().equals(v2.getCoordinates())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        for (InteriorNode i1 : il1) {
            boolean found = false;
            for (InteriorNode i2 : il2) {
                if (i1.getCoordinates().equals(i2.getCoordinates())) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return false;
        }

        for (GraphEdge e1 : el1) {
            boolean found = false;
            for (GraphEdge e2 : el2) {
                var e1e = e1.getEdgeNodes();
                var e2e = e2.getEdgeNodes();
                if (e1e.getValue0().getCoordinates().equals(e2e.getValue1().getCoordinates()) || (e1e.getValue0().getCoordinates().equals(e2e.getValue0().getCoordinates()))) {
                    if (e1e.getValue1().getCoordinates().equals(e2e.getValue1().getCoordinates()) || (e1e.getValue1().getCoordinates().equals(e2e.getValue0().getCoordinates()))) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found)
                return false;
        }

        return true;
    }
}
