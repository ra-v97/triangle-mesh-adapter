package pl.edu.agh.gg.model.api;

import pl.edu.agh.gg.common.Coordinates;

public interface Positionable {

    // Returns position of element in 3D space
    Coordinates getCoordinates();

    double getXCoordinate();

    double getYCoordinate();

    double getZCoordinate();
}
