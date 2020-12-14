package pl.edu.agh.gg.model;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.javatuples.Pair;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.ElementAttributes;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.api.Identifiable;
import pl.edu.agh.gg.transformations.utils.TransformationUtils;
import pl.edu.agh.gg.visualization.DisplayableGraph;

import java.util.*;
import java.util.stream.Collectors;

public class GraphModel implements DisplayableGraph, Identifiable {

    private static final String DEFAULT_GRAPH_LABEL = "Graph model";

    private final UUID uuid;

    private final String label;

    private final Multimap<LayerDescriptor, UUID> layerVerticesIds;

    private final Multimap<LayerDescriptor, UUID> layerInteriorIds;

    private final Multimap<LayerDescriptor, UUID> layerEdgeIds;

    private final Map<UUID, GraphEdge> edges;

    private final Map<UUID, Vertex> vertices;

    private final Map<UUID, InteriorNode> interiors;

    public GraphModel() {
        this(UUID.randomUUID());
    }

    public GraphModel(UUID id) {
        this.uuid = id;
        this.label = DEFAULT_GRAPH_LABEL;

        layerVerticesIds = HashMultimap.create();
        layerInteriorIds = HashMultimap.create();
        layerEdgeIds = HashMultimap.create();

        edges = Maps.newHashMap();
        vertices = Maps.newHashMap();
        interiors = Maps.newHashMap();
    }

    public GraphModel(GraphModel graph) {
        this(UUID.randomUUID());
        graph.vertices.values().forEach(vertex -> graph.resolveVertexLayer(vertex.getUUID())
                .ifPresent(layerDescriptor ->
                        insertVertex(vertex.getUUID(), vertex.getLabel(), vertex.getCoordinates(), layerDescriptor)
                ));

        graph.interiors.values().forEach(interior -> graph.resolveInteriorLayer(interior.getUUID())
                .flatMap(layerDescriptor -> insertInterior(label, layerDescriptor, interior.getAdjacentVertices()))
                .ifPresent(newInterior -> interior.getAdjacentInteriors()
                        .forEach(newInterior::addAdjacentInteriorNode)));

        graph.edges.values()
                .stream()
                .filter(edge -> edge.getType() == GraphEdge.GraphEdgeType.VERTEX_VERTEX)
                .forEach(edge -> {
                    graph.resolveEdgeLayer(edge.getUUID()).ifPresent(layerDescriptor -> {
                                final Pair<GraphNode, GraphNode> edgeNode = edge.getEdgeNodes();
                                insertEdge((Vertex) edgeNode.getValue0(), (Vertex) edgeNode.getValue1(), layerDescriptor);
                            }
                    );
                });

        graph.edges.values()
                .stream()
                .filter(edge -> edge.getType() == GraphEdge.GraphEdgeType.INTERIOR_INTERIOR)
                .forEach(edge -> {
                    graph.resolveEdgeLayer(edge.getUUID()).ifPresent(layerDescriptor -> {
                                final Pair<GraphNode, GraphNode> edgeNode = edge.getEdgeNodes();
                                insertEdge((InteriorNode) edgeNode.getValue0(), (InteriorNode) edgeNode.getValue1());
                            }
                    );
                });
    }

    public Optional<Vertex> insertVertex(String label, Coordinates coordinates, LayerDescriptor layer) {
        return insertVertex(UUID.randomUUID(), label, coordinates, layer);
    }

    public Optional<Vertex> insertVertex(UUID id, String label, Coordinates coordinates, LayerDescriptor layer) {
        return Vertex.builder(id)
                .setCoordinates(coordinates)
                .setLabel(label)
                .build()
                .map(vertex -> {
                    layerVerticesIds.put(layer, vertex.getUUID());
                    vertices.put(vertex.getUUID(), vertex);
                    return vertex;
                });
    }

    public Optional<Vertex> removeVertex(Vertex vertex) {
        return removeVertex(vertex.getUUID());
    }

    public Optional<Vertex> removeVertex(UUID id) {
        final Vertex vertex = vertices.remove(id);
        if (vertex != null) {
            layerVerticesIds.keySet().forEach(layerDescriptor -> {
                layerVerticesIds.remove(layerDescriptor, vertex.getUUID());
            });
            final Set<UUID> interiorsToRemove = interiors.values().stream()
                    .filter(interior -> interior.getAdjacentVertices().contains(vertex))
                    .map(Identifiable::getUUID)
                    .collect(Collectors.toSet());
            interiorsToRemove.forEach(this::removeInterior);

            final Set<UUID> edgesToRemove = edges.values().stream()
                    .filter(graphEdge -> graphEdge.getEdgeNodes().contains(vertex))
                    .map(Identifiable::getUUID)
                    .collect(Collectors.toSet());
            edgesToRemove.forEach(this::removeEdge);
            return Optional.of(vertex);
        }
        return Optional.empty();
    }

    public Optional<InteriorNode> insertInterior(String label, LayerDescriptor layerDescriptor, Set<Vertex> vertices) {
        if (vertices.size() != 3) {
            return Optional.empty();
        }
        final Iterator<Vertex> verticesIterator = vertices.iterator();
        return insertInterior(label, layerDescriptor, verticesIterator.next(), verticesIterator.next(),
                verticesIterator.next());
    }

    public Optional<InteriorNode> insertInterior(String label, LayerDescriptor layerDescriptor, Vertex v1, Vertex v2, Vertex v3) {
        return InteriorNode.builder()
                .setLabel(label)
                .putVertex(v1)
                .putVertex(v2)
                .putVertex(v3)
                .build()
                .map(interiorNode -> {
                    layerInteriorIds.put(layerDescriptor, interiorNode.getUUID());
                    interiors.put(interiorNode.getUUID(), interiorNode);
                    insertEdge(v1, interiorNode, layerDescriptor);
                    insertEdge(v2, interiorNode, layerDescriptor);
                    insertEdge(v3, interiorNode, layerDescriptor);
                    return interiorNode;
                });
    }

    public StartingNode insertStartingInterior(String label, LayerDescriptor layerDescriptor, Coordinates coordinates) {
        StartingNode startingNode = new StartingNode(UUID.randomUUID(), label, coordinates);
        layerInteriorIds.put(layerDescriptor, startingNode.getUUID());
        interiors.put(startingNode.getUUID(), startingNode);
        return startingNode;
    }

    public void removeInterior(InteriorNode interiorNode) {
        removeInterior(interiorNode.getUUID());
    }

    public void removeInterior(UUID id) {
        final Set<Pair<LayerDescriptor, UUID>> interiorsToRemove = layerInteriorIds.keySet()
                .stream()
                .map(layerDescriptor -> new Pair<>(layerDescriptor, id))
                .collect(Collectors.toSet());
        interiorsToRemove.forEach(interiorToRemove -> {
            layerInteriorIds.remove(interiorToRemove.getValue0(), interiorToRemove.getValue1());
        });
        final Set<UUID> edgesToRemove = edges.values().stream()
                .filter(graphEdge -> graphEdge.getEdgeNodes().contains(interiors.get(id)))
                .map(Identifiable::getUUID)
                .collect(Collectors.toSet());
        edgesToRemove.forEach(this::removeEdge);
        interiors.remove(id);
    }

    public Optional<GraphEdge> insertEdge(InteriorNode n1, InteriorNode n2) {
        n1.addAdjacentInteriorNode(n2);
        n2.addAdjacentInteriorNode(n1);
        return insertEdge(n1, n2, GraphEdge.GraphEdgeType.INTERIOR_INTERIOR, null, ElementAttributes.INTERIOR_CONNECTOR_CLASS);
    }

    public Optional<GraphEdge> insertEdge(Vertex n1, Vertex n2, LayerDescriptor layerDescriptor) {
        return insertEdge(n1, n2, GraphEdge.GraphEdgeType.VERTEX_VERTEX, layerDescriptor, ElementAttributes.BORDER_CLASS);
    }

    public Optional<GraphEdge> insertEdge(Vertex n1, InteriorNode n2, LayerDescriptor layerDescriptor) {
        return insertEdge(n1, n2, GraphEdge.GraphEdgeType.VERTEX_INTERIOR, layerDescriptor,
                ElementAttributes.INTERIOR_EDGE_CLASS);
    }

    private Optional<GraphEdge> insertEdge(GraphNode n1, GraphNode n2, GraphEdge.GraphEdgeType type,
                                           LayerDescriptor layerDescriptor, String uiClass) {
        return GraphEdge.builder()
                .setSourceNode(n1)
                .setTargetNode(n2)
                .setStyleClass(uiClass)
                .setType(type)
                .build()
                .map(graphEdge -> {
                    if (layerDescriptor != null) {
                        layerEdgeIds.put(layerDescriptor, graphEdge.getUUID());
                    }
                    edges.put(graphEdge.getUUID(), graphEdge);
                    return graphEdge;
                });
    }

    public void removeEdge(GraphNode n1, GraphNode n2) {
        final Set<GraphEdge> edgesToRemove = edges.values().stream()
                .filter(edge -> checkEdgeEndNodes(edge, n1, n2))
                .collect(Collectors.toSet());
        edgesToRemove.forEach(edgeToRemove -> removeEdge(edgeToRemove.getUUID()));
    }

    public boolean checkEdgeEndNodes(GraphEdge edge, GraphNode n1, GraphNode n2) {
        final Pair<GraphNode, GraphNode> edgeNodes = edge.getEdgeNodes();
        return (edgeNodes.getValue0() == n1 && edgeNodes.getValue1() == n2)
                || (edgeNodes.getValue0() == n2 && edgeNodes.getValue1() == n1);
    }

    public void removeEdge(UUID edgeId) {
        Optional.ofNullable(edges.get(edgeId))
                .ifPresent(this::removeEdge);
    }

    public void removeEdge(GraphEdge graphEdge) {
        edges.remove(graphEdge.getUUID());
        layerEdgeIds.keySet().forEach(layerDescriptor -> {
            layerEdgeIds.remove(layerDescriptor, graphEdge.getUUID());
        });
    }

    public Optional<GraphEdge> getEdge(UUID edgeId) {
        return Optional.ofNullable(edges.get(edgeId));
    }

    public Optional<Vertex> getVertex(UUID vertexId) {
        return Optional.ofNullable(vertices.get(vertexId));
    }

    public Collection<Vertex> getVertices() {
        return vertices.values();
    }

    public Optional<GraphEdge> getEdgeBetweenNodes(Vertex v1, Vertex v2) {
        return edges.values().stream()
                .filter(edge -> checkEdgeEndNodes(edge, v1, v2))
                .findFirst();
    }

    public Collection<GraphEdge> getEdges() {
        return edges.values();
    }

    public Optional<InteriorNode> getInterior(UUID interiorId) {
        return Optional.ofNullable(interiors.get(interiorId));
    }

    public Collection<InteriorNode> getInteriors() {
        return interiors.values();
    }

    public Optional<LayerDescriptor> resolveVertexLayer(UUID vertexId) {
        return resolveElementLayer(vertexId, layerVerticesIds);
    }

    public Optional<LayerDescriptor> resolveEdgeLayer(UUID edgeId) {
        return resolveElementLayer(edgeId, layerEdgeIds);
    }

    public Optional<LayerDescriptor> resolveInteriorLayer(UUID interiorId) {
        return resolveElementLayer(interiorId, layerInteriorIds);
    }

    private Optional<LayerDescriptor> resolveElementLayer(UUID elementId, Multimap<LayerDescriptor, UUID> layerElementIds) {
        return layerElementIds.keySet().stream()
                .filter(descriptor -> Optional.ofNullable(layerElementIds.get(descriptor))
                        .filter(collection -> collection.contains(elementId))
                        .isPresent())
                .findFirst();
    }

    public List<Vertex> getVerticesBetween(Vertex beginning, Vertex end) {
        return vertices.values().stream()
                .filter(vertex -> isVertexBetween(vertex, beginning, end))
                .filter(vertex -> isVertexLinkedWith(vertex, beginning, end))
                .collect(Collectors.toList());
    }

    public Optional<Vertex> getVertexBetween(Vertex beginning, Vertex end) {
        // TODO Create if needed
        return Optional.empty();
    }

    private boolean isVertexLinkedWith(Vertex v, Vertex beginning, Vertex end) {
        return getEdgeBetweenNodes(v, beginning).isPresent() && getEdgeBetweenNodes(v, end).isPresent();
    }

    private boolean isVertexBetween(Vertex v, Vertex beginning, Vertex end) {
        double epsilon = .001;
        double xd = Math.abs(calculateInlineMatrixDeterminant(v, beginning, end));
        if (isVertexSameAs(v, beginning) || isVertexSameAs(v, end)) {
            return false;
        } else return areCoordinatesMatching(v, beginning, end)
                && Math.abs(calculateInlineMatrixDeterminant(v, beginning, end)) < epsilon;
    }

    private boolean isVertexSameAs(Vertex a, Vertex b) {
        return a.getCoordinates().equals(b.getCoordinates());
    }

    private boolean areCoordinatesMatching(Vertex v, Vertex beginning, Vertex end) {
        return v.getXCoordinate() <= Math.max(beginning.getXCoordinate(), end.getXCoordinate())
                && v.getXCoordinate() >= Math.min(beginning.getXCoordinate(), end.getXCoordinate())
                && v.getYCoordinate() <= Math.max(beginning.getYCoordinate(), end.getYCoordinate())
                && v.getYCoordinate() >= Math.min(beginning.getYCoordinate(), end.getYCoordinate());
    }

    /*
    Basic matrix calculation to check if points are in line with each other
    The matrix looks like this:
    | a.x, a.y, a.z |
    | b.x, b.y, b.z |
    | c.x, c.y, c.z |

    so if we calculate det of that matrix and it is equal to 0 it means that all points are in straight line
     */
    private double calculateInlineMatrixDeterminant(Vertex v, Vertex beginning, Vertex end) {
        Coordinates a = v.getCoordinates();
        Coordinates b = beginning.getCoordinates();
        Coordinates c = end.getCoordinates();

        return a.getX() * b.getY() * c.getZ()
                + a.getY() * b.getZ() * c.getX()
                + a.getZ() * b.getX() * c.getY()
                - a.getZ() * b.getY() * c.getX()
                - a.getX() * b.getZ() * c.getY()
                - a.getY() * b.getX() * c.getZ();
    }

    public void rotate1() {
        vertices.values().forEach(GraphNode::rotate1);
    }

    public void rotate2() {
        vertices.values().forEach(GraphNode::rotate2);
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getStringId() {
        return uuid.toString();
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public MultiGraph getGraphVisualization() {
        final MultiGraph graphVisuals = new MultiGraph(getStringId());
        final Map<UUID, Node> visualNodesMap = Maps.newHashMap();

        vertices.values().stream()
                .map(vertex -> new Pair<>(vertex.getUUID(), addVisibleVertex(graphVisuals, vertex)))
                .forEach(pair -> visualNodesMap.put(pair.getValue0(), pair.getValue1()));

        interiors.values().stream()
                .map(interior -> new Pair<>(interior.getUUID(), addVisibleInterior(graphVisuals, interior)))
                .forEach(pair -> visualNodesMap.put(pair.getValue0(), pair.getValue1()));

        edges.values().forEach(edge -> addVisibleEdge(graphVisuals, visualNodesMap, edge));

        return graphVisuals;
    }

    @Override
    public MultiGraph getGraphVisualization(LayerDescriptor layerDescriptor) {
        final MultiGraph graphVisuals = new MultiGraph(getStringId());
        final Map<UUID, Node> visualNodesMap = Maps.newHashMap();

        layerVerticesIds.get(layerDescriptor)
                .stream()
                .map(vertices::get)
                .filter(Objects::nonNull)
                .map(vertex -> new Pair<>(vertex.getUUID(), addVisibleVertex(graphVisuals, vertex)))
                .forEach(pair -> visualNodesMap.put(pair.getValue0(), pair.getValue1()));

        layerInteriorIds.get(layerDescriptor)
                .stream()
                .map(interiors::get)
                .filter(Objects::nonNull)
                .map(interior -> new Pair<>(interior.getUUID(), addVisibleInterior(graphVisuals, interior)))
                .forEach(pair -> visualNodesMap.put(pair.getValue0(), pair.getValue1()));

        layerEdgeIds.get(layerDescriptor)
                .stream()
                .map(edges::get)
                .filter(Objects::nonNull)
                .filter(edge -> edge.getType() != GraphEdge.GraphEdgeType.INTERIOR_INTERIOR)
                .forEach(edge -> addVisibleEdge(graphVisuals, visualNodesMap, edge));

        return graphVisuals;
    }

    private static Node addVisibleVertex(MultiGraph graphVisuals, Vertex vertex) {
        final Node node = graphVisuals.addNode(vertex.getStringId());
        node.setAttribute(ElementAttributes.FROZEN_LAYOUT);
        node.setAttribute(ElementAttributes.LABEL, vertex.getLabel());
        node.setAttribute(ElementAttributes.XYZ, vertex.getXCoordinate(), vertex.getYCoordinate(), vertex.getZCoordinate());
        return node;
    }

    private static Node addVisibleInterior(MultiGraph graphVisuals, InteriorNode interiorNode) {
        final Node node = graphVisuals.addNode(interiorNode.getStringId());
        node.setAttribute(ElementAttributes.FROZEN_LAYOUT);
        node.setAttribute(ElementAttributes.LABEL, interiorNode.getLabel());
        node.setAttribute(ElementAttributes.XYZ, interiorNode.getXCoordinate(), interiorNode.getYCoordinate(),
                interiorNode.getZCoordinate());
        node.setAttribute(ElementAttributes.CLASS, ElementAttributes.INTERIOR_CLASS);
        return node;
    }

    private static Edge addVisibleEdge(MultiGraph graphVisuals, Map<UUID, Node> visualNodesMap, GraphEdge graphEdge) {
        final Node n1 = visualNodesMap.get(graphEdge.getEdgeNodes().getValue0().getUUID());
        final Node n2 = visualNodesMap.get(graphEdge.getEdgeNodes().getValue1().getUUID());

        final Edge edge = graphVisuals.addEdge(graphEdge.getStringId(), n1, n2);
        edge.setAttribute(ElementAttributes.FROZEN_LAYOUT);
        edge.setAttribute(ElementAttributes.LABEL, "");
        edge.setAttribute(ElementAttributes.CLASS, graphEdge.getStyleClass());
        return edge;
    }
}
