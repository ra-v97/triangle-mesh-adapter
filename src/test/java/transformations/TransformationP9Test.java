package transformations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.*;
import pl.edu.agh.gg.model.GraphEdge.GraphEdgeType;
import pl.edu.agh.gg.transformations.TransformationP9;
import pl.edu.agh.gg.utils.PositionCalculator;
import utils.GraphTestUtils;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TransformationP9Test {

    private static final LayerDescriptor initialLayerDescriptor = new LayerDescriptor(0);

    private TransformationP9 transformation;

    private GraphModel graphModel;

    private InteriorNode initialInteriorNode;

    @BeforeEach
    private void initializeTestCase() {
        transformation = new TransformationP9();
        graphModel = createLeftSideGraph();
        initialInteriorNode = GraphTestUtils.resolveGraphInterior(graphModel)
                .orElseThrow(IllegalStateException::new);
    }

    private GraphModel createLeftSideGraph() {
        GraphModel graph = new GraphModel();

        Coordinates v1Co = new Coordinates(0, 0, 0);
        Coordinates v2Co = new Coordinates(0, 1, 0);
        Coordinates v3Co = new Coordinates(1, 0, 0);

        Vertex v1 = graph.insertVertex("V1", v1Co, initialLayerDescriptor).get();
        Vertex v2 = graph.insertVertex("V2", v2Co, initialLayerDescriptor).get();
        Vertex v3 = graph.insertVertex("V3", v3Co, initialLayerDescriptor).get();

        graph.insertEdge(v1, v2, initialLayerDescriptor);
        graph.insertEdge(v2, v3, initialLayerDescriptor);
        graph.insertEdge(v1, v3, initialLayerDescriptor);

        graph.insertInterior("I", initialLayerDescriptor, v1, v2, v3);
        return graph;
    }

    private GraphModel startingGraphAfterTransformation() {
        GraphModel graph = new GraphModel();
        Coordinates v1Co = new Coordinates(0, 0, 0);
        Coordinates v2Co = new Coordinates(0, 1, 0);
        Coordinates v3Co = new Coordinates(1, 0, 0);

        Vertex v1 = graph.insertVertex("V1", v1Co, initialLayerDescriptor).get();
        Vertex v2 = graph.insertVertex("V2", v2Co, initialLayerDescriptor).get();
        Vertex v3 = graph.insertVertex("V3", v3Co, initialLayerDescriptor).get();

        graph.insertEdge(v1, v2, initialLayerDescriptor);
        graph.insertEdge(v2, v3, initialLayerDescriptor);
        graph.insertEdge(v1, v3, initialLayerDescriptor);

        InteriorNode i = graph.insertInterior("i", initialLayerDescriptor, v1, v2, v3).get();

        // -----------------------------

        LayerDescriptor nextLayerDescriptor = initialLayerDescriptor.getNextLayerDescriptor();

        Coordinates midpointCoords = PositionCalculator.getMidpointCoordinates(v2, v3);
        final Vertex v4 = graph.insertVertex("V4", v1.getCoordinates(), nextLayerDescriptor).get();
        final Vertex v5 = graph.insertVertex("V5", v2.getCoordinates(), nextLayerDescriptor).get();
        final Vertex v6 = graph.insertVertex("V6", v3.getCoordinates(), nextLayerDescriptor).get();

        graph.insertEdge(v4, v5, nextLayerDescriptor);
        graph.insertEdge(v5, v6, nextLayerDescriptor);
        graph.insertEdge(v6, v4, nextLayerDescriptor);

        final InteriorNode i1 = graph.insertInterior("I1", nextLayerDescriptor, v4, v5, v6).get();
        graph.insertEdge(i1, i);

        return graph;
    }

    private GraphModel biggerGraph() {
        GraphModel graph = new GraphModel();
        Coordinates stNoCo = new Coordinates(0, 0, 0);
        StartingNode startingNode = graph.insertStartingInterior("s", initialLayerDescriptor, stNoCo);

        LayerDescriptor layerDescriptor = new LayerDescriptor(1);

        Coordinates v1Co = new Coordinates(stNoCo.getX() - 1, stNoCo.getY() - 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() - 1, stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() - 1, stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v4Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() + 1, stNoCo.getZ());

        Vertex v1 = graph.insertVertex("V1", v1Co, layerDescriptor).get();
        Vertex v2 = graph.insertVertex("V2", v2Co, layerDescriptor).get();
        Vertex v3 = graph.insertVertex("V3", v3Co, layerDescriptor).get();
        Vertex v4 = graph.insertVertex("V4", v4Co, layerDescriptor).get();

        graph.insertEdge(v1, v2, layerDescriptor);
        graph.insertEdge(v1, v3, layerDescriptor);
        graph.insertEdge(v2, v4, layerDescriptor);
        graph.insertEdge(v3, v4, layerDescriptor);
        graph.insertEdge(v2, v3, layerDescriptor);

        InteriorNode i1 = graph.insertInterior("I1", layerDescriptor, v1, v2, v3).get();
        InteriorNode i2 = graph.insertInterior("I2", layerDescriptor, v2, v3, v4).get();
        graph.insertEdge(i1, startingNode);
        graph.insertEdge(i2, startingNode);
        return graph;
    }

    private GraphModel biggerGraphAfterTransformingI1() {
        GraphModel graph = new GraphModel();
        Coordinates stNoCo = new Coordinates(0, 0, 0);
        StartingNode startingNode = graph.insertStartingInterior("s", initialLayerDescriptor, stNoCo);

        LayerDescriptor layerDescriptor = new LayerDescriptor(1);

        Coordinates v1Co = new Coordinates(stNoCo.getX() - 1, stNoCo.getY() - 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() - 1, stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() - 1, stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v4Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() + 1, stNoCo.getZ());

        Vertex v1 = graph.insertVertex("V1", v1Co, layerDescriptor).get();
        Vertex v2 = graph.insertVertex("V2", v2Co, layerDescriptor).get();
        Vertex v3 = graph.insertVertex("V3", v3Co, layerDescriptor).get();
        Vertex v4 = graph.insertVertex("V4", v4Co, layerDescriptor).get();

        graph.insertEdge(v1, v2, layerDescriptor);
        graph.insertEdge(v1, v3, layerDescriptor);
        graph.insertEdge(v2, v4, layerDescriptor);
        graph.insertEdge(v3, v4, layerDescriptor);
        graph.insertEdge(v2, v3, layerDescriptor);

        InteriorNode i1 = graph.insertInterior("i1", layerDescriptor, v1, v2, v3).get();
        InteriorNode i2 = graph.insertInterior("I2", layerDescriptor, v2, v3, v4).get();
        graph.insertEdge(i1, startingNode);
        graph.insertEdge(i2, startingNode);

        // --------------------------
        LayerDescriptor nextLayer = layerDescriptor.getNextLayerDescriptor();

        Coordinates v5Co = new Coordinates(v1Co.getX(), v1Co.getY(), v1Co.getZ());
        Coordinates v6Co = new Coordinates(v2Co.getX(), v2Co.getY(), v2Co.getZ());
        Coordinates v7Co = new Coordinates(v3Co.getX(), v3Co.getY(), v3Co.getZ());

        Vertex v5 = graph.insertVertex("V5", v5Co, nextLayer).get();
        Vertex v6 = graph.insertVertex("V6", v6Co, nextLayer).get();
        Vertex v7 = graph.insertVertex("V7", v7Co, nextLayer).get();

        graph.insertEdge(v5, v6, nextLayer);
        graph.insertEdge(v6, v7, nextLayer);
        graph.insertEdge(v7, v5, nextLayer);

        InteriorNode i3 = graph.insertInterior("I3", nextLayer, v5, v6, v7).get();

        graph.insertEdge(i3, i1);
        return graph;
    }

    @Test
    void transformationShouldExecuteForCorrectLeftSide() {
        // Then
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
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
    void transformationShouldNotExecuteAfterRemovingVertexFromLeftSide() {
        // When
        graphModel.removeVertex(graphModel.getVertices().stream().findFirst().get().getUUID());
        // Then
        assertThrows(NoSuchElementException.class, () -> transformation.transform(graphModel, initialInteriorNode)); // No interior present
    }

    @Test
    void transformationShouldNotExecuteAfterChangingLabelInLeftSide() {
        // When
        initialInteriorNode.setLabel("i");
        // Then
        assertFalse(transformation.isApplicable(graphModel, initialInteriorNode));
    }

    @Test
    void transformationShouldExecuteForDifferentLabel() {
        // When
        initialInteriorNode.setLabel("X");
        // Then
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNode));
    }


    @Test
    void transformationShouldNotExecuteForIncorrectCoordinatesInLeftSide() {
        GraphModel graph = new GraphModel();
        Coordinates c1 = new Coordinates(0, 0, 0);
        Coordinates c2 = new Coordinates(0, 0, 0);
        Coordinates c3 = new Coordinates(0, 5, 0);

        Vertex v1 = graph.insertVertex("v1", c1, initialLayerDescriptor).get();
        Vertex v2 = graph.insertVertex("v2", c2, initialLayerDescriptor).get();
        Vertex v3 = graph.insertVertex("v3", c3, initialLayerDescriptor).get();
        graph.insertEdge(v1, v2, initialLayerDescriptor);
        graph.insertEdge(v2, v3, initialLayerDescriptor);
        graph.insertEdge(v1, v3, initialLayerDescriptor);
        InteriorNode i = graph.insertInterior("I", initialLayerDescriptor, v1, v2, v3).get();

        assertFalse(transformation.isApplicable(graph, i));
    }

    @Test
    void transformationShouldNotExecuteForCollinearPointsInLeftSide() {
        GraphModel graph = new GraphModel();
        Coordinates c1 = new Coordinates(0, 0, 0);
        Coordinates c2 = new Coordinates(0, 2, 0);
        Coordinates c3 = new Coordinates(0, 5, 0);

        Vertex v1 = graph.insertVertex("v1", c1, initialLayerDescriptor).get();
        Vertex v2 = graph.insertVertex("v2", c2, initialLayerDescriptor).get();
        Vertex v3 = graph.insertVertex("v3", c3, initialLayerDescriptor).get();
        graph.insertEdge(v1, v2, initialLayerDescriptor);
        graph.insertEdge(v2, v3, initialLayerDescriptor);
        graph.insertEdge(v1, v3, initialLayerDescriptor);
        InteriorNode i = graph.insertInterior("I", initialLayerDescriptor, v1, v2, v3).get();

        assertFalse(transformation.isApplicable(graph, i));
    }

    @Test
    void transformationShouldWorkWhenLeftSideIsSubgraphOfBiggerGraph() {
        // Given
        GraphModel graph = biggerGraph();
        // Then
        assertTrue(graph.getInteriors().stream()
                .filter(interiorNode -> interiorNode.getLabel().equals("I1") || interiorNode.getLabel().equals("I2"))
                .allMatch(interiorNode -> transformation.isApplicable(graph, interiorNode)));
    }

    @Test
    void transformationShouldNotDamageStructureOfGraphWhenLeftSideIsSubgraph() {
        // Given
        GraphModel graph = biggerGraph();
        GraphModel correctGraph = biggerGraphAfterTransformingI1();
        InteriorNode i1 = graph.getInteriors().stream()
                .filter(interiorNode -> interiorNode.getLabel().equals("I1"))
                .findFirst()
                .get();
        // When
        transformation.transform(graph, i1);
        // Then
        assertEquals(correctGraph.getInteriors().size(), graph.getInteriors().size());
        assertEquals(correctGraph.getVertices().size(), graph.getVertices().size());
        assertEquals(correctGraph.getEdges().size(), graph.getEdges().size());
        assertEquals(correctGraph.getVertices().stream().map(GraphNode::getCoordinates).collect(Collectors.toSet()),
                graph.getVertices().stream().map(GraphNode::getCoordinates).collect(Collectors.toSet()));
        assertEquals(correctGraph.getInteriors().stream().map(GraphNode::getCoordinates).collect(Collectors.toSet()),
                graph.getInteriors().stream().map(GraphNode::getCoordinates).collect(Collectors.toSet()));
    }

    @Test
    void transformationShouldReturnCorrectResultForCorrectLeftSide() {
        // Given
        GraphModel correctGraph = startingGraphAfterTransformation();
        InteriorNode i1 = graphModel.getInteriors().stream().filter(interiorNode -> interiorNode.getLabel().equals("I")).findFirst().get();
        // When
        transformation.transform(graphModel, i1);
        // Then
        assertEquals(correctGraph.getInteriors().size(), graphModel.getInteriors().size());
        assertEquals(correctGraph.getVertices().size(), graphModel.getVertices().size());
        assertEquals(correctGraph.getEdges().size(), graphModel.getEdges().size());

        assertEquals(correctGraph.getVertices().stream()
                        .map(GraphNode::getCoordinates).collect(Collectors.toSet()),
                graphModel.getVertices().stream()
                        .map(GraphNode::getCoordinates)
                        .collect(Collectors.toSet()));

        assertEquals(correctGraph.getInteriors().stream()
                        .map(GraphNode::getCoordinates).collect(Collectors.toSet()),
                graphModel.getInteriors().stream()
                        .map(GraphNode::getCoordinates)
                        .collect(Collectors.toSet()));
    }

    @Test
    void transformationShouldReturnResultAtCorrectLevel() {
        // Given
        LayerDescriptor layer1 = initialLayerDescriptor.getNextLayerDescriptor();
        // When
        transformation.transform(graphModel, initialInteriorNode);
        // Then
        assertTrue(initialInteriorNode.getAdjacentVertices().stream()
                .allMatch(vertex -> graphModel.resolveVertexLayer(vertex.getUUID()).get().equals(initialLayerDescriptor)));
        assertTrue(initialInteriorNode.getAdjacentInteriors().stream()
                .map(InteriorNode::getAdjacentVertices)
                .flatMap(Collection::stream).allMatch(vertex -> graphModel.resolveVertexLayer(vertex.getUUID()).get().equals(layer1)));
        assertTrue(graphModel.getVertices().stream()
                .allMatch(vertex -> GraphTestUtils.getEdges(graphModel, vertex, GraphEdgeType.VERTEX_VERTEX).stream()
                        .allMatch(edge -> graphModel.resolveEdgeLayer(edge.getUUID()).equals(graphModel.resolveVertexLayer(vertex.getUUID()))))); // all vertex-vertex edges at correct level
        assertTrue(graphModel.getVertices().stream()
                .allMatch(vertex -> GraphTestUtils.getEdges(graphModel, vertex, GraphEdgeType.VERTEX_INTERIOR).stream()
                        .allMatch(edge -> graphModel.resolveEdgeLayer(edge.getUUID()).equals(graphModel.resolveVertexLayer(vertex.getUUID()))))); // all vertex-interior edges at correct level
        assertEquals(2, graphModel.getInteriors().stream()
                .map(interiorNode -> graphModel.resolveInteriorLayer(interiorNode.getUUID()))
                .distinct()
                .count()); // 2 layers
    }
}
