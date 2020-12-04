package pl.edu.agh.gg.model;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.AbstractNode;
import pl.edu.agh.gg.common.Coordinates;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Vertex extends GraphNode {

    private static final String DEFAULT_VERTEX_LABEL = "V";

    public Vertex(AbstractGraph graph, UUID id, String label, Coordinates coordinates) {
        super(graph, id, label, coordinates);
    }

    /*
        I am kind of sorry for this but THE MIGHTY api that library exposes
        doesn't allow to override a default java hashcode method used to identify
        objects in neighborMap. So firstly we need to find and retrieve object
        with given properties and then run this function again with 'correct' reference.
    */
    @Override
    public Edge getEdgeBetween(Node node) {
        for (AbstractNode e : this.neighborMap.keySet()) {
            if (Objects.equals(e.getId(), node.getId())) {
                return super.getEdgeBetween(e);
            }
        }
        return null;
    }

    public static VertexBuilder builder(AbstractGraph graph, UUID id){
            return new VertexBuilder(graph, id);
    }

    public static VertexBuilder builder(AbstractGraph graph){
        return new VertexBuilder(graph);
    }

    public static class VertexBuilder {

        private final AbstractGraph graph;

        private final UUID id;

        private String label;

        private Double xCoordinate;

        private Double yCoordinate;

        private Double zCoordinate;

        public VertexBuilder(AbstractGraph graph) {
            this(graph, UUID.randomUUID());
        }

        public VertexBuilder(AbstractGraph graph, UUID id) {
            this.graph = graph;
            this.id = id;
            this.xCoordinate = null;
            this.yCoordinate = null;
            this.zCoordinate = null;
        }

        public VertexBuilder(AbstractGraph graph, UUID id, String label) {
            this(graph, id);
            this.label = label;
        }

        public VertexBuilder setCoordinates(Coordinates coordinates) {
            this.xCoordinate = coordinates.getX();
            this.yCoordinate = coordinates.getY();
            this.zCoordinate = coordinates.getZ();
            return this;
        }

        public VertexBuilder setXCoordinate(double xCoordinate) {
            this.xCoordinate = xCoordinate;
            return this;
        }

        public VertexBuilder setYCoordinate(double yCoordinate) {
            this.yCoordinate = yCoordinate;
            return this;
        }

        public VertexBuilder setZCoordinate(double zCoordinate) {
            this.zCoordinate = zCoordinate;
            return this;
        }

        public VertexBuilder setLabel(String label) {
            this.label = label;
            return this;
        }

        public Optional<Vertex> build() {
            if (xCoordinate == null || yCoordinate == null || zCoordinate == null) {
                return Optional.empty();
            }
            final String finalLabel = label != null ? label : DEFAULT_VERTEX_LABEL;
            return Optional.of(new Vertex(graph, id, finalLabel, new Coordinates(xCoordinate, yCoordinate, zCoordinate)));
        }
    }
}
