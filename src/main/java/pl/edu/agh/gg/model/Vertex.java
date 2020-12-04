package pl.edu.agh.gg.model;

import pl.edu.agh.gg.common.Coordinates;

import java.util.Optional;
import java.util.UUID;

public class Vertex extends GraphNode {

    private static final String DEFAULT_VERTEX_LABEL = "V";

    public Vertex(UUID id, String label, Coordinates coordinates) {
        super(id, label, coordinates);
    }

    public static VertexBuilder builder(UUID id) {
        return new VertexBuilder(id);
    }

    public static VertexBuilder builder() {
        return new VertexBuilder();
    }

    public static class VertexBuilder {

        private final UUID id;

        private String label;

        private Double xCoordinate;

        private Double yCoordinate;

        private Double zCoordinate;

        public VertexBuilder() {
            this(UUID.randomUUID());
        }

        public VertexBuilder(UUID id) {
            this.id = id;
            this.xCoordinate = null;
            this.yCoordinate = null;
            this.zCoordinate = null;
        }

        public VertexBuilder(UUID id, String label) {
            this(id);
            this.label = label;
        }

        public VertexBuilder setCoordinates(Coordinates coordinates) {
            if(coordinates != null){
                this.xCoordinate = coordinates.getX();
                this.yCoordinate = coordinates.getY();
                this.zCoordinate = coordinates.getZ();
            }
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
            return Optional.of(new Vertex(id, finalLabel, new Coordinates(xCoordinate, yCoordinate, zCoordinate)));
        }
    }
}
