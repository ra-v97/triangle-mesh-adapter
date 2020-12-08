package pl.edu.agh.gg.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.utils.PositionCalculator;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class InteriorNode extends GraphNode {

    private static final String INTERIOR_DEFAULT_SYMBOL = "I";

    private final ImmutableSet<Vertex> adjacentVertices;

    private final Set<InteriorNode> adjacentInteriors;

    public InteriorNode(UUID id, String label, Vertex v1, Vertex v2, Vertex v3) {
        super(id, label, PositionCalculator.getInteriorPosition(v1, v2, v3));
        adjacentVertices = ImmutableSet.of(v1, v2, v3);
        adjacentInteriors = Sets.newHashSet();
    }

    protected InteriorNode(UUID id, String label, Coordinates position) {
        super(id, label, position);
        adjacentVertices = ImmutableSet.of();
        adjacentInteriors = Sets.newHashSet();
    }

    public void addAdjacentInteriorNode(InteriorNode node) {
        adjacentInteriors.add(node);
    }

    public void removeAdjacentInteriorNode(InteriorNode node) {
        adjacentInteriors.remove(node);
    }

    public Set<Vertex> getAdjacentVertices() {
        return Sets.newHashSet(adjacentVertices);
    }

    public Set<InteriorNode> getAdjacentInteriors() {
        return Sets.newHashSet(adjacentInteriors);
    }

    public void setLabel(String label) { super.setLabel(label); }

    public static InteriorNodeBuilder builder(UUID id) {
        return new InteriorNodeBuilder(id);
    }

    public static InteriorNodeBuilder builder(UUID id, String label) {
        return new InteriorNodeBuilder(id, label);
    }

    public static InteriorNodeBuilder builder() {
        return new InteriorNodeBuilder();
    }

    public static class InteriorNodeBuilder {

        private final UUID id;

        private String label;

        private Set<Vertex> tmpAdjacentVertices;

        public InteriorNodeBuilder() {
            this(UUID.randomUUID());
        }

        public InteriorNodeBuilder(UUID id, String label) {
            this(id);
            this.label = label;
        }

        public InteriorNodeBuilder(UUID id) {
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
                return Optional.of(new InteriorNode(id, finalLabel, v1, v2, v3));
            }
            return Optional.empty();
        }
    }
}
