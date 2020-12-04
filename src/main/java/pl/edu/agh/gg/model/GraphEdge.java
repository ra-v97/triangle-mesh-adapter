package pl.edu.agh.gg.model;

import org.graphstream.graph.implementations.AbstractEdge;
import org.javatuples.Pair;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.ElementAttributes;
import pl.edu.agh.gg.model.api.Identifiable;
import pl.edu.agh.gg.model.api.Styleable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class GraphEdge extends AbstractEdge implements Identifiable, Styleable {

    private final UUID id;

    private final GraphEdgeType type;

    private final String styleClass;

    private final Pair<? extends GraphNode, ? extends GraphNode> edgeNodes;

    private GraphEdge(UUID id, GraphEdgeType type, String styleClass,
                      Pair<? extends GraphNode, ? extends GraphNode> edgeNodes) {
        super(id.toString(), edgeNodes.getValue0(), edgeNodes.getValue1(), false);
        this.id = id;
        this.type = type;
        this.styleClass = styleClass != null ? styleClass : ElementAttributes.BORDER_CLASS;
        this.edgeNodes = edgeNodes;
    }

    public GraphEdge(UUID id, Vertex node1, Vertex node2) {
        this(id, GraphEdgeType.VERTEX_VERTEX, null, new Pair<>(node1, node2));
    }

    public GraphEdge(Vertex node1, Vertex node2) {
        this(UUID.randomUUID(), GraphEdgeType.VERTEX_VERTEX, null, new Pair<>(node1, node2));
    }

    public GraphEdge(UUID id, InteriorNode node1, InteriorNode node2) {
        this(id, GraphEdgeType.INTERIOR_INTERIOR, null, new Pair<>(node1, node2));
    }

    public GraphEdge(InteriorNode node1, InteriorNode node2) {
        this(UUID.randomUUID(), GraphEdgeType.INTERIOR_INTERIOR, null, new Pair<>(node1, node2));
    }

    public GraphEdge(UUID id, Vertex node1, InteriorNode node2) {
        this(id, GraphEdgeType.VERTEX_INTERIOR, null, new Pair<>(node1, node2));
    }

    public GraphEdge(Vertex node1, InteriorNode node2) {
        this(UUID.randomUUID(), GraphEdgeType.VERTEX_INTERIOR, null, new Pair<>(node1, node2));
    }

    public Pair<GraphNode, GraphNode> getEdgeNodes() {
        return new Pair<>(edgeNodes.getValue0(), edgeNodes.getValue1());
    }

    public GraphEdgeType getType() {
        return type;
    }

    public double getLength() {
        return Coordinates.distance(edgeNodes.getValue0().getCoordinates(), edgeNodes.getValue1().getCoordinates());
    }

    public static GraphEdgeBuilder builder() {
        return new GraphEdgeBuilder();
    }

    public static GraphEdgeBuilder builder(GraphNode source, GraphNode target) {
        return new GraphEdgeBuilder(source, target);
    }

    @Override
    public UUID getUUID() {
        return id;
    }

    @Override
    public String getStringId() {
        return id.toString();
    }

    @Override
    public String getLabel() {
        return getStringId();
    }

    @Override
    public String getStyleClass() {
        return styleClass;
    }

    public static class GraphEdgeBuilder {

        private GraphNode sourceNode;

        private GraphNode targetNode;

        private final UUID tmpId;

        private String style;

        private GraphEdgeType type;

        public GraphEdgeBuilder(GraphNode source, GraphNode target) {
            this();
            this.sourceNode = source;
            this.targetNode = target;
        }

        public GraphEdgeBuilder() {
            this.tmpId = UUID.randomUUID();
        }

        public GraphEdgeBuilder setSourceNode(GraphNode source) {
            this.sourceNode = source;
            return this;
        }

        public GraphEdgeBuilder setTargetNode(GraphNode target) {
            this.targetNode = target;
            return this;
        }

        public GraphEdgeBuilder setStyleClass(String styleClass) {
            this.style = styleClass;
            return this;
        }

        public GraphEdgeBuilder setType(GraphEdgeType type) {
            this.type = type;
            return this;
        }

        public Optional<GraphEdge> build() {
            if (sourceNode != null && targetNode != null && !Objects.equals(sourceNode, targetNode)) {
                return Optional.of(new GraphEdge(tmpId, type, style, new Pair<>(sourceNode, targetNode)));
            }
            return Optional.empty();
        }
    }

    public enum GraphEdgeType {
        VERTEX_VERTEX, VERTEX_INTERIOR, INTERIOR_INTERIOR
    }
}
