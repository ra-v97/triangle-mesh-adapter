package pl.edu.agh.gg.model;

import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.model.api.Identifiable;
import pl.edu.agh.gg.model.api.Positionable;

import java.util.UUID;

public abstract class GraphNode implements Identifiable, Positionable {

    private final UUID id;

    private String label;

    private Coordinates coordinates;

    protected GraphNode(UUID id, String label, double xCoordinate, double yCoordinate, double zCoordinate) {
        this(id, label, new Coordinates(xCoordinate, yCoordinate, zCoordinate));
    }

    protected GraphNode(UUID id, String label, Coordinates coordinates) {
        this.id = id;
        this.label = label;
        this.coordinates = coordinates;
    }

    public void rotate1() {
        coordinates = coordinates.getRotation1();
    }

    public void rotate2() {
        coordinates = coordinates.getRotation2();
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

    public void setLabel(String label) {
        this.label = label;
    }
}
