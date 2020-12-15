package transformations;

import com.google.common.base.Predicates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.*;
import pl.edu.agh.gg.transformations.TransformationP1;
import utils.GraphTestUtils;
import pl.edu.agh.gg.model.GraphEdge.GraphEdgeType;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static pl.edu.agh.gg.transformations.utils.TransformationUtils.isUpper;

public class TransformationP1Test {

    private static final LayerDescriptor initialLayerDescriptor = new LayerDescriptor(0);

    private static final Coordinates initialCoordinates = new Coordinates(0, 0, 0);

    private TransformationP1 transformation;

    private GraphModel graphModel;

    private InteriorNode initialInteriorNode;

    @BeforeEach
    private void initializeTestCase(){
        transformation = new TransformationP1();
        graphModel = createLeftSideGraph();
        initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);
    }

    private GraphModel createLeftSideGraph(){
        final GraphModel graphModel = new GraphModel();
        graphModel.insertStartingInterior("S", initialLayerDescriptor, initialCoordinates);
        return graphModel;
    }

    @Test
    void transformationShouldExecuteForCorrectLeftSide() {
        // Then
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteForTwoStartingNodes() {
        // When
        graphModel.insertStartingInterior("S", new LayerDescriptor(0), new Coordinates(0, 0, 0));
        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    /**
     * Should work because
     * <ul>
     * <li> the label is upper-cased so it can be applied </li>
     * <li> the label is irrelevant for the starting node type</li>
     * </ul>
     */
    @Test
    void transformationShouldExecuteForDifferentLabel() {
        // When
        initialInteriorNode.setLabel("X");
        // Then
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteAfterRemovingVertexFromLeftSide() {
        // When
        graphModel.removeInterior(initialInteriorNode);
        // Then
        assertThrows(NoSuchElementException.class,
                () -> transformation.transform(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteAfterChangingLabelInLeftSide() {
        // When
        initialInteriorNode.setLabel("s");
        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldNotExecuteWhenLeftSideIsSubgraphOfBiggerGraph() {
        //Given
        LayerDescriptor layerDescriptor = new LayerDescriptor(1);
        // When
        Vertex v1 = graphModel.insertVertex("V1", new Coordinates(0, 0, 0), layerDescriptor).get();
        Vertex v2 = graphModel.insertVertex("V2", new Coordinates(2, 0, 0), layerDescriptor).get();
        Vertex v3 = graphModel.insertVertex("V3", new Coordinates(1, 2, 0), layerDescriptor).get();
        InteriorNode i = graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        graphModel.insertEdge(i, initialInteriorNode);
        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }


    @Test
    void transformationShouldReturnAllElementsForCorrectLeftSide() {
        // When
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
        transformation.transform(graphModel, initialInteriorNode);
        // Then
        InteriorNode[] interiors = graphModel.getInteriors().toArray(new InteriorNode[0]);
        assertEquals(3, graphModel.getInteriors().size(), "Should have 3 interior nodes");
        assertEquals(1, graphModel.getInteriors().stream()
                .filter(StartingNode.class::isInstance)
                .count());

        assertTrue(graphModel.getInteriors().stream()
                .filter(StartingNode.class::isInstance)
                .noneMatch(interiorNode -> isUpper(interiorNode.getLabel())));

        assertEquals(2, graphModel.getInteriors().stream()
                .filter(Predicates.not(StartingNode.class::isInstance))
                .count());

        assertTrue(graphModel.getInteriors().stream()
                .filter(Predicates.not(StartingNode.class::isInstance))
                .allMatch(interiorNode -> isUpper(interiorNode.getLabel())));

        assertEquals(4, graphModel.getVertices().size());
        assertTrue(graphModel.getVertices().stream()
                .allMatch(vertex -> isUpper(vertex.getLabel())));

        assertEquals(13, graphModel.getEdges().size()); // 5 for shape, 6 between vertices and interiors, 2 between interiors
    }

    @Test
    void transformationShouldReturnVerticesWithCorrectCoordinates() {
        // When
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
        transformation.transform(graphModel, initialInteriorNode);
        // Then
        List<Coordinates> expectedVerticesCoords = Arrays.asList(new Coordinates(-1, -1, 0),
                new Coordinates(-1, 1, 0),
                new Coordinates(1, -1, 0),
                new Coordinates(1, 1, 0));
        assertTrue(expectedVerticesCoords.stream()
                .allMatch(coord -> graphModel.getVertices().stream()
                        .anyMatch(actual -> Objects.equals(actual.getCoordinates(),  coord))));
    }

    @Test
    void transformationShouldReturnResultAtCorrectLevel() {
        // Given
        LayerDescriptor expectedLayer = initialLayerDescriptor.getNextLayerDescriptor();
        // When
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
        transformation.transform(graphModel, initialInteriorNode);
        // Then
        assertTrue(graphModel.getVertices().stream()
                .allMatch(vertex -> Objects.equals(graphModel.resolveVertexLayer(vertex.getUUID()).get(), expectedLayer)));

        assertTrue(graphModel.getInteriors().stream()
                .filter(Predicates.not(StartingNode.class::isInstance))
                .allMatch(interiorNode -> Objects.equals(graphModel.resolveInteriorLayer(interiorNode.getUUID()).get(), expectedLayer)));

        assertTrue(graphModel.getInteriors().stream()
                .filter(StartingNode.class::isInstance)
                .allMatch(startingNode -> Objects.equals(graphModel.resolveInteriorLayer(startingNode.getUUID()).get(), initialLayerDescriptor)));

        assertTrue(graphModel.getEdges().stream()
                .filter(graphEdge -> graphEdge.getType() == GraphEdgeType.VERTEX_VERTEX || graphEdge.getType() == GraphEdgeType.VERTEX_INTERIOR)
                .allMatch(graphEdge -> Objects.equals(graphModel.resolveEdgeLayer(graphEdge.getUUID()).get(), expectedLayer)));
    }
}
