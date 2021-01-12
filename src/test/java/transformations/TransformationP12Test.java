package transformations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphEdge.GraphEdgeType;
import pl.edu.agh.gg.model.*;
import pl.edu.agh.gg.transformations.TransformationP12;
import pl.edu.agh.gg.utils.PositionCalculator;
import utils.GraphTestUtils;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static pl.edu.agh.gg.transformations.utils.TransformationUtils.isUpper;

public class TransformationP12Test {

    private static final LayerDescriptor initialLayerDescriptor = new LayerDescriptor(0);

    private TransformationP12 transformation;

    private GraphModel graphModel;

    private InteriorNode[] initialInteriorNodes;

    @BeforeEach
    private void initializeTestCase() {
        transformation = new TransformationP12();
        graphModel = createLeftSideGraph();
        initialInteriorNodes = graphModel.getInteriors()
                .stream()
                .filter(p -> !p.getLabel().equals("s") && isUpper(p.getLabel()))
                .collect(Collectors.toSet()).toArray(InteriorNode[]::new);
    }

    private GraphModel createLeftSideGraph() {
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
        InteriorNode i2 = graph.insertInterior("i2", layerDescriptor, v2, v3, v4).get();
        graph.insertEdge(i1, startingNode);
        graph.insertEdge(i2, startingNode);

        LayerDescriptor nextLayer = layerDescriptor.getNextLayerDescriptor();

        Coordinates v5Co = new Coordinates(v1Co.getX(), v1Co.getY(), v1Co.getZ());
        Coordinates v6Co = new Coordinates(v2Co.getX(), v2Co.getY(), v2Co.getZ());
        Coordinates v7Co = new Coordinates(v3Co.getX(), v3Co.getY(), v3Co.getZ());
        Coordinates v8Co = new Coordinates(v2Co.getX(), v2Co.getY(), v2Co.getZ());
        Coordinates v9Co = new Coordinates(v3Co.getX(), v3Co.getY(), v3Co.getZ());
        Coordinates v10Co = new Coordinates(v4Co.getX(), v4Co.getY(), v4Co.getZ());


        Vertex v5 = graph.insertVertex("V5", v5Co, nextLayer).get();
        Vertex v6 = graph.insertVertex("V6", v6Co, nextLayer).get();
        Vertex v7 = graph.insertVertex("V7", v7Co, nextLayer).get();
        Vertex v8 = graph.insertVertex("V8", v8Co, nextLayer).get();
        Vertex v9 = graph.insertVertex("V9", v9Co, nextLayer).get();
        Vertex v10 = graph.insertVertex("V10", v10Co, nextLayer).get();


        graph.insertEdge(v5, v6, nextLayer);
        graph.insertEdge(v6, v7, nextLayer);
        graph.insertEdge(v7, v5, nextLayer);

        graph.insertEdge(v8, v9, nextLayer);
        graph.insertEdge(v9, v10, nextLayer);
        graph.insertEdge(v10, v8, nextLayer);

        InteriorNode i3 = graph.insertInterior("I3", nextLayer, v5, v6, v7).get();
        InteriorNode i4 = graph.insertInterior("I4", nextLayer, v8, v9, v10).get();

        graph.insertEdge(i3, i1);
        graph.insertEdge(i4, i2);
        return graph;
    }

    private GraphModel startingGraphAfterTransformation() {
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
        InteriorNode i2 = graph.insertInterior("i2", layerDescriptor, v2, v3, v4).get();
        graph.insertEdge(i1, startingNode);
        graph.insertEdge(i2, startingNode);

        LayerDescriptor nextLayer = layerDescriptor.getNextLayerDescriptor();

        Coordinates v5Co = new Coordinates(v1Co.getX(), v1Co.getY(), v1Co.getZ());
        Coordinates v6Co = new Coordinates(v2Co.getX(), v2Co.getY(), v2Co.getZ());
        Coordinates v7Co = new Coordinates(v3Co.getX(), v3Co.getY(), v3Co.getZ());
        Coordinates v10Co = new Coordinates(v4Co.getX(), v4Co.getY(), v4Co.getZ());


        Vertex v5 = graph.insertVertex("V5", v5Co, nextLayer).get();
        Vertex v6 = graph.insertVertex("V6", v6Co, nextLayer).get();
        Vertex v7 = graph.insertVertex("V7", v7Co, nextLayer).get();
        Vertex v10 = graph.insertVertex("V10", v10Co, nextLayer).get();


        graph.insertEdge(v5, v6, nextLayer);
        graph.insertEdge(v6, v7, nextLayer);
        graph.insertEdge(v7, v5, nextLayer);
        graph.insertEdge(v6, v10, nextLayer);
        graph.insertEdge(v7, v10, nextLayer);

        InteriorNode i3 = graph.insertInterior("I3", nextLayer, v5, v6, v7).get();
        InteriorNode i4 = graph.insertInterior("I4", nextLayer, v6, v7, v10).get();

        graph.insertEdge(i3, i1);
        graph.insertEdge(i4, i2);

        return graph;
    }

    @Test
    void transformationShouldExecuteForCorrectLeftSide() {
        // Then
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNodes[0], initialInteriorNodes[1]));
    }

    @Test
    void transformationShouldNotExecuteAfterRemovingVertexFromLeftSide() {
        // When
        graphModel.removeVertex(graphModel.getVertices().stream().findFirst().get().getUUID());
        // Then
        assertThrows(NoSuchElementException.class, () -> transformation.transform(graphModel, initialInteriorNodes[0], initialInteriorNodes[1])); // No interior present
    }


    @Test
    void transformationShouldExecuteForDifferentLabel() {
        // When
        initialInteriorNodes[0].setLabel("X");
        // Then
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNodes[0], initialInteriorNodes[1]));
    }

    @Test
    void transformationShouldHaveCorrectRightSide() {
        // When
        assertTrue(transformation.isApplicable(graphModel, initialInteriorNodes[0], initialInteriorNodes[1]));
        transformation.transform(graphModel, initialInteriorNodes[0], initialInteriorNodes[1]);
        // Then
        GraphModel correctGraph = startingGraphAfterTransformation();
        assertEquals(correctGraph.getVertices().size(), graphModel.getVertices().size());
        assertEquals(correctGraph.getEdges().size(), graphModel.getEdges().size());
        assertEquals(correctGraph.getInteriors().size(), graphModel.getInteriors().size());
        assertEquals(correctGraph.getVertices().stream().map(GraphNode::getCoordinates).collect(Collectors.toSet()),
                graphModel.getVertices().stream().map(GraphNode::getCoordinates).collect(Collectors.toSet()));
        assertEquals(correctGraph.getInteriors().stream().map(GraphNode::getCoordinates).collect(Collectors.toSet()),
                graphModel.getInteriors().stream().map(GraphNode::getCoordinates).collect(Collectors.toSet()));
    }

    @Test
    void transformationShouldReturnCorrectResultForCorrectLeftSide() {
        // Given
        GraphModel correctGraph = startingGraphAfterTransformation();
        // When
        transformation.transform(graphModel, initialInteriorNodes[0], initialInteriorNodes[1]);
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
        transformation.transform(graphModel, initialInteriorNodes[0], initialInteriorNodes[1]);
        // Then
//        assertTrue(initialInteriorNode.getAdjacentVertices().stream()
//                .allMatch(vertex -> graphModel.resolveVertexLayer(vertex.getUUID()).get().equals(initialLayerDescriptor)));
//        assertTrue(initialInteriorNode.getAdjacentInteriors().stream()
//                .map(InteriorNode::getAdjacentVertices)
//                .flatMap(Collection::stream).allMatch(vertex -> graphModel.resolveVertexLayer(vertex.getUUID()).get().equals(layer1)));
        assertTrue(graphModel.getVertices().stream()
                .allMatch(vertex -> GraphTestUtils.getEdges(graphModel, vertex, GraphEdgeType.VERTEX_VERTEX).stream()
                        .allMatch(edge -> graphModel.resolveEdgeLayer(edge.getUUID()).equals(graphModel.resolveVertexLayer(vertex.getUUID()))))); // all vertex-vertex edges at correct level
        assertTrue(graphModel.getVertices().stream()
                .allMatch(vertex -> GraphTestUtils.getEdges(graphModel, vertex, GraphEdgeType.VERTEX_INTERIOR).stream()
                        .allMatch(edge -> graphModel.resolveEdgeLayer(edge.getUUID()).equals(graphModel.resolveVertexLayer(vertex.getUUID()))))); // all vertex-interior edges at correct level
        assertEquals(3, graphModel.getInteriors().stream()
                .map(interiorNode -> graphModel.resolveInteriorLayer(interiorNode.getUUID()))
                .distinct()
                .count()); // 3 layers
    }
}
