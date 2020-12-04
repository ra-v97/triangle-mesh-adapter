package pl.edu.agh.gg.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.graphstream.graph.implementations.AbstractGraph;
import pl.edu.agh.gg.utils.PositionCalculator;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class InteriorNode extends GraphNode {

    private static final String INTERIOR_DEFAULT_SYMBOL = "I";

    private final ImmutableSet<Vertex> adjacentVertices;

    private final Set<InteriorNode> adjacentInteriors;

    public InteriorNode(AbstractGraph graph, UUID id, String label, Vertex v1, Vertex v2, Vertex v3) {
        super(graph, id, label, PositionCalculator.getInteriorPosition(v1, v2, v3));
        adjacentVertices = ImmutableSet.of(v1, v2, v3);
        adjacentInteriors = Sets.newHashSet();
    }

    public void addAdjacentInteriorNode(InteriorNode node) {
        adjacentInteriors.add(node);
    }

    public void removeAdjacentInteriorNode(InteriorNode node) {
        adjacentInteriors.remove(node);
    }

    public Set<Vertex> getAdjacentVertices() {
        return Set.copyOf(adjacentVertices);
    }

    public Set<InteriorNode> getAdjacentInteriors() {
        return Set.copyOf(adjacentInteriors);
    }

    public static InteriorNodeBuilder builder(AbstractGraph graph, UUID id){
        return new InteriorNodeBuilder(graph, id);
    }

    public static InteriorNodeBuilder builder(AbstractGraph graph, UUID id, String label){
        return new InteriorNodeBuilder(graph, id, label);
    }

    public static InteriorNodeBuilder builder(AbstractGraph graph){
        return new InteriorNodeBuilder(graph);
    }

    public static class InteriorNodeBuilder {

        private final AbstractGraph graph;

        private final UUID id;

        private String label;

        private Set<Vertex> tmpAdjacentVertices;

        public InteriorNodeBuilder(AbstractGraph graph) {
            this(graph, UUID.randomUUID());
        }

        public InteriorNodeBuilder(AbstractGraph graph, UUID id, String label) {
            this(graph, id);
            this.label = label;
        }

        public InteriorNodeBuilder(AbstractGraph graph, UUID id) {
            this.graph = graph;
            this.id = id;
            tmpAdjacentVertices = Sets.newHashSet();
        }

        public InteriorNodeBuilder setLabel(String label) {
            this.label = label;
            return this;
        }

        public InteriorNodeBuilder putVertex(Vertex v) {
            this.tmpAdjacentVertices.add(v);
            return this;
        }

        public InteriorNodeBuilder clearVertices() {
            this.tmpAdjacentVertices.clear();
            return this;
        }

        public Optional<InteriorNode> build() {
            final String finalLabel = label != null ? label : INTERIOR_DEFAULT_SYMBOL;
            if (tmpAdjacentVertices.size() == 3) {
                final Iterator<Vertex> iterator = tmpAdjacentVertices.iterator();
                final Vertex v1 = iterator.next();
                final Vertex v2 = iterator.next();
                final Vertex v3 = iterator.next();
                return Optional.of(new InteriorNode(graph, id, finalLabel, v1, v2, v3));
            }
            return Optional.empty();
        }
    }
}
