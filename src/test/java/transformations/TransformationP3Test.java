package transformations;

import org.junit.jupiter.api.Test;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.model.GraphEdge;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;
import pl.edu.agh.gg.transformations.TransformationP3;
import pl.edu.agh.gg.transformations.fixtures.TransformationP3Fixtures;
import utils.GraphTestUtils;

import java.util.Collection;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TransformationP3Test {

    @Test
    void transformationShouldExecuteForCorrectLeftSide() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldExecuteWhenDifferentVerticesOrder() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createLeftSideGraph(
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
        var graphModel = TransformationP3Fixtures.createValidLeftSideGraph();
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
        var graphModel = TransformationP3Fixtures.createValidLeftSideGraph();
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
        var graphModel = TransformationP3Fixtures.createValidLeftSideGraph();
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
        var graphModel = TransformationP3Fixtures.createValidLeftSideGraph();
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
        var graphModel = TransformationP3Fixtures.createLeftSideGraphWithAdditionalVertexOnWrongEdge();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenAdditionalVertexIsNotInTheMiddle() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createLeftSideGraph(new Coordinates(1, 1, 0),
                new Coordinates(0, 0, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(1, 0.5, 0));
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenAdditionalVertexOverlapsWithAdjVertex() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createLeftSideGraphWithThreeVertices(new Coordinates(1, 1, 0),
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
        var graphModel = TransformationP3Fixtures.createLeftSideGraphWithTwoAdditionalVertices(new Coordinates(1, 1, 0),
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
        var graphModel = TransformationP3Fixtures.createLeftSideGraph(
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
        var graphModel = TransformationP3Fixtures.createLeftSideGraphWithFiveVertices(new Coordinates(1, 1, 0),
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
    void transformationShouldExecuteWhenGraphIsMuchBiggerButValid() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createValidLeftSideBigGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void baseTransformationNumberOfEdgesBeforeTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertEquals(7, graphModel.getEdges().toArray().length);
    }

    @Test
    void baseTransformationNumberOfEdgesAfterTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createValidLeftSideGraph();
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
        var graphModel = TransformationP3Fixtures.createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertEquals(1, graphModel.getInteriors().toArray().length);
    }

    @Test
    void baseTransformationNumberOfInteriorsAfterTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createValidLeftSideGraph();
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
        var graphModel = TransformationP3Fixtures.createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertEquals(4, graphModel.getVertices().toArray().length);
    }

    @Test
    void baseTransformationNumberOfNodesAfterTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // When
        transformation.transform(graphModel, initialInteriorNode);

        // Then
        assertEquals(8, graphModel.getVertices().toArray().length);
    }

    @Test
    void baseTransformationNumberOfValidResult() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createValidLeftSideGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // When
        transformation.transform(graphModel, initialInteriorNode);

        // Then
        assertTrue(isEqual(graphModel, TransformationP3Fixtures.createValidRightSideGraph()));
    }

    @Test
    void bigTransformationNumberOfEdgesBeforeTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createValidLeftSideBigGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertEquals(12, graphModel.getEdges().toArray().length);
    }

    @Test
    void bigTransformationNumberOfEdgesAfterTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createValidLeftSideBigGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // When
        transformation.transform(graphModel, initialInteriorNode);

        // Then
        assertEquals(25, graphModel.getEdges().toArray().length);
    }

    @Test
    void bigTransformationNumberOfInteriorsAfterTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createValidLeftSideBigGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // When
        transformation.transform(graphModel, initialInteriorNode);

        // Then
        assertEquals(3, graphModel.getInteriors().toArray().length);
    }

    @Test
    void bigTransformationNumberOfNodesBeforeTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createValidLeftSideBigGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // Then
        assertEquals(6, graphModel.getVertices().toArray().length);
    }

    @Test
    void bigTransformationNumberOfNodesAfterTransformation() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createValidLeftSideBigGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // When
        transformation.transform(graphModel, initialInteriorNode);

        // Then
        assertEquals(10, graphModel.getVertices().toArray().length);
    }

    @Test
    void bigTransformationNumberOfValidResult() {
        // Given
        var transformation = new TransformationP3();
        var graphModel = TransformationP3Fixtures.createValidLeftSideBigGraph();
        var initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);

        // When
        transformation.transform(graphModel, initialInteriorNode);

        // Then
        assertTrue(isEqual(graphModel, TransformationP3Fixtures.createValidRightSideBigGraph()));
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
