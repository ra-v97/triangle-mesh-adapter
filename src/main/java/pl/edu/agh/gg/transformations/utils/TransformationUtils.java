package pl.edu.agh.gg.transformations.utils;

import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;

import java.util.*;

public class TransformationUtils {

    public static double getCordsBetweenX(Vertex a, Vertex b) {
        return (a.getXCoordinate() + b.getXCoordinate()) / 2;
    }

    public static double getCordsBetweenY(Vertex a, Vertex b) {
        return (a.getYCoordinate() + b.getYCoordinate()) / 2;
    }


    public static Set<Vertex> getLongestEdge(InteriorNode interior) {
        final Set<Vertex> vertices = interior.getAdjacentVertices();
        Vertex[][] cartesian = vertices.stream().flatMap(ai -> vertices.stream().map(bi -> new Vertex[]{ai, bi})).toArray(Vertex[][]::new);

        Optional<Vertex[]> max_dist = Arrays.stream(cartesian).max(Comparator.comparingDouble(v -> get2DDistance(v[0], v[1])));
        return new HashSet<>(Arrays.asList(max_dist.get()));
    }

    public static Double get2DDistance(Vertex a, Vertex b) {
        return Math.sqrt(Math.pow((a.getXCoordinate() - b.getXCoordinate()), 2) + Math.pow((a.getYCoordinate() - b.getYCoordinate()), 2));
    }

    public static double getCordsBetweenX(Vertex a, Vertex b) {
        return (a.getXCoordinate() + b.getXCoordinate()) / 2;
    }

    public static double getCordsBetweenY(Vertex a, Vertex b) {
        return (a.getYCoordinate() + b.getYCoordinate()) / 2;
    }

    public static boolean isUpper(String s) {
        return s.equals(s.toUpperCase());
    }

    public static boolean isLower(String s) {
        return s.equals(s.toLowerCase());
    }
}
