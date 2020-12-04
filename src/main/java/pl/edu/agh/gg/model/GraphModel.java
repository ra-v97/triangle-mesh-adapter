package pl.edu.agh.gg.model;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.graphstream.graph.implementations.MultiGraph;
import org.javatuples.Pair;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.ElementAttributes;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.api.Identifiable;

import java.util.*;

public class GraphModel extends MultiGraph implements Identifiable {

    private static final String DEFAULT_GRAPH_LABEL = "Graph model";

    private final UUID uuid;

    private final String label;

    private final Multimap<LayerDescriptor, UUID> layerVerticesIds;

    private final Multimap<LayerDescriptor, UUID> layerInteriorIds;

    private final Multimap<LayerDescriptor, UUID> layerEdgeIds;

    private final Map<UUID, GraphEdge> edges;

    private final Map<UUID, Vertex> vertices;

    private final Map<UUID, InteriorNode> interiors;

    public GraphModel(UUID id) {
        super(id.toString());
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

    public Optional<Vertex> insertVertex(UUID id, String label, Coordinates coordinates, LayerDescriptor layer) {
        return Vertex.builder(this, id)
                .setCoordinates(coordinates)
                .setLabel(label)
                .build()
                .map(vertex -> {
                    layerVerticesIds.put(layer, vertex.getUUID());
                    vertices.put(vertex.getUUID(), vertex);
                    return vertex;
                });
    }

//    public void insertVertex(Vertex vertex, LayerDescriptor layer) {
//        final Node node = this.addNode(vertex.getStringId());
//        node.setAttribute(ElementAttributes.FROZEN_LAYOUT);
//        node.setAttribute(ElementAttributes.LABEL, vertex.getLabel());
//        node.setAttribute(ElementAttributes.XYZ, vertex.getXCoordinate(), vertex.getYCoordinate(), vertex.getZCoordinate());
//        layerVerticesIds.put(layer, vertex.getUUID());
//        vertices.put(vertex.getUUID(), vertex);
//    }

    public Optional<Vertex> removeVertex(UUID id) {
        final Vertex vertex = vertices.remove(id);
        if (vertex != null) {
            super.removeNode(vertex.getStringId());
            layerVerticesIds.keySet().forEach(layerDescriptor -> {
                layerVerticesIds.remove(layerDescriptor, vertex.getUUID());
            });
            interiors.values().stream()
                    .filter(interior -> interior.getAdjacentVertices().contains(vertex))
                    .map(Identifiable::getUUID)
                    .forEach(this::removeInterior);
            edges.values().stream()
                    .filter(graphEdge -> graphEdge.getEdgeNodes().contains(vertex))
                    .map(Identifiable::getUUID)
                    .forEach(this::deleteEdge);
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

    private Optional<InteriorNode> insertInterior(String label, LayerDescriptor layerDescriptor, Vertex v1, Vertex v2, Vertex v3) {
        return InteriorNode.builder(this)
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

//    private void insertInterior(InteriorNode interiorNode, LayerDescriptor layerDescriptor) {
//        final Node node = this.addNode(interiorNode.getStringId());
//        node.setAttribute(ElementAttributes.FROZEN_LAYOUT);
//        node.setAttribute(ElementAttributes.XYZ, interiorNode.getXCoordinate(), interiorNode.getYCoordinate(), interiorNode.getZCoordinate());
//        node.setAttribute(ElementAttributes.CLASS, ElementAttributes.INTERIOR_CLASS);
//        node.setAttribute(ElementAttributes.LABEL, interiorNode.getLabel());
//
//    }

    public void removeInterior(InteriorNode interiorNode) {
        removeInterior(interiorNode.getUUID());
    }

    public void removeInterior(UUID id) {
        layerInteriorIds.keySet().forEach(layerDescriptor -> {
            layerInteriorIds.remove(layerDescriptor, id);
        });
        edges.values().stream()
                .filter(graphEdge -> graphEdge.getEdgeNodes().contains(interiors.get(id)))
                .map(Identifiable::getUUID)
                .forEach(this::deleteEdge);
        Optional.ofNullable(interiors.remove(id))
                .ifPresent(interior -> this.removeNode(interior.getStringId()));
    }

    public Optional<GraphEdge> insertEdge(InteriorNode n1, InteriorNode n2) {
        n1.addAdjacentInteriorNode(n2);
        n2.addAdjacentInteriorNode(n1);
        return insertEdge(n1, n2, GraphEdge.GraphEdgeType.INTERIOR_INTERIOR, null, ElementAttributes.INTERIOR_CONNECTOR_CLASS);
    }

    public Optional<GraphEdge> insertEdge(Vertex n1, Vertex n2, LayerDescriptor layerDescriptor) {
        return insertEdge(n1, n2, GraphEdge.GraphEdgeType.VERTEX_VERTEX, layerDescriptor, ElementAttributes.BORDER_CLASS);
    }

    private Optional<GraphEdge> insertEdge(Vertex n1, InteriorNode n2, LayerDescriptor layerDescriptor) {
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
//                    final Edge edge = this.addEdge(graphEdge.getStringId(), n1, n2);
//                    if (uiClass != null) {
//                        edge.setAttribute(ElementAttributes.CLASS, uiClass);
//                    } else {
//                        edge.setAttribute(ElementAttributes.CLASS, ElementAttributes.BORDER_CLASS);
//                    }
                    return graphEdge;
                });
    }

    public void deleteEdge(GraphNode n1, GraphNode n2) {
        edges.values().stream()
                .filter(edge -> checkEdgeEndNodes(edge, n1, n2))
                .forEach(edgeToRemove -> deleteEdge(edgeToRemove.getUUID()));
    }

    public boolean checkEdgeEndNodes(GraphEdge edge, GraphNode n1, GraphNode n2) {
        final Pair<GraphNode, GraphNode> edgeNodes = edge.getEdgeNodes();
        return (edgeNodes.getValue0() == n1 && edgeNodes.getValue1() == n2)
                || (edgeNodes.getValue0() == n2 && edgeNodes.getValue1() == n1);
    }

    public void deleteEdge(UUID edgeId) {
        Optional.ofNullable(edges.get(edgeId))
                .ifPresent(this::deleteEdge);
    }

    public void deleteEdge(GraphEdge graphEdge) {
        edges.remove(graphEdge.getUUID());
        layerEdgeIds.keySet().forEach(layerDescriptor -> {
            layerInteriorIds.remove(layerDescriptor, graphEdge.getUUID());
        });
        this.removeEdge(graphEdge.getStringId());
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
        // TODO Create if needed
        return null;
    }

    public Optional<Vertex> getVertexBetween(Vertex beginning, Vertex end) {
        // TODO Create if needed
        return Optional.empty();
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

    public void rotate() {
        vertices.values().forEach(GraphNode::rotate);
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
}