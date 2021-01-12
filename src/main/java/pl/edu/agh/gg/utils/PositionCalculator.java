package pl.edu.agh.gg.utils;

import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.model.Vertex;

public final class PositionCalculator {

    public static Coordinates getInteriorPosition(Vertex v1, Vertex v2, Vertex v3) {
        return new Coordinates(getInteriorXCoordinate(v1, v2, v3), getInteriorYCoordinate(v1, v2, v3), getInteriorZCoordinate(v1, v2, v3));
    }

    public static Coordinates getMidpointCoordinates(Vertex v1, Vertex v2) {
        return new Coordinates(
                (v1.getXCoordinate() + v2.getXCoordinate()) / 2d,
                (v1.getYCoordinate() + v2.getYCoordinate()) / 2d,
                (v1.getZCoordinate() + v2.getZCoordinate()) / 2d);
    }

    public static Coordinates getInteriorPosition(Vertex v1, Vertex v2) {
        return new Coordinates(
                v2.getXCoordinate(),
                v1.getYCoordinate(),
                v1.getZCoordinate());
    }

    private static double getInteriorXCoordinate(Vertex v1, Vertex v2, Vertex v3) {
        return (v1.getXCoordinate() + v2.getXCoordinate() + v3.getXCoordinate()) / 3d;
    }

    private static double getInteriorYCoordinate(Vertex v1, Vertex v2, Vertex v3) {
        return (v1.getYCoordinate() + v2.getYCoordinate() + v3.getYCoordinate()) / 3d;
    }

    private static double getInteriorZCoordinate(Vertex v1, Vertex v2, Vertex v3) {
        return (v1.getZCoordinate() + v2.getZCoordinate() + v3.getZCoordinate()) / 3d;
    }

    public static boolean checkTriangleInequality(Coordinates v1, Coordinates v2, Coordinates v3) {
        double a = v1.distance(v2);
        double b = v1.distance(v3);
        double c = v2.distance(v3);

        return (a + b) > c && (a + c) > b && (b + c) > a;
    }
}
