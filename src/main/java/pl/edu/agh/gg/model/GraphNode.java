package pl.edu.agh.gg.model;

import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.ElementAttributes;
import pl.edu.agh.gg.model.api.Identifiable;
import pl.edu.agh.gg.model.api.Positionable;

import java.util.UUID;

public abstract class GraphNode extends SingleNode implements Identifiable, Positionable {

    private final UUID id;

    private final String label;

    private Coordinates coordinates;

    protected GraphNode(AbstractGraph graph, UUID id, String label, double xCoordinate, double yCoordinate, double zCoordinate) {
        this(graph, id, label, new Coordinates(xCoordinate, yCoordinate, zCoordinate));
    }

    protected GraphNode(AbstractGraph graph, UUID id, String label, Coordinates coordinates) {
        super(graph, id.toString());
        super.setAttribute(ElementAttributes.FROZEN_LAYOUT);
        this.id = id;
        this.label = label;
        this.coordinates = coordinates;
    }

    public void rotate() {
        coordinates = coordinates.getRotation();
    }

    @Override
    public double getXCoordinate() {
        return coordinates.getX();
    }

    @Override
    public double getYCoordinate() {
        return coordinates.getY();
    }

    @Override
    public double getZCoordinate() {
        return coordinates.getZ();
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
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
        return label;
    }
}
