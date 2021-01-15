package pl.edu.agh.gg.transformations;

import com.google.common.collect.Sets;
import org.checkerframework.checker.nullness.Opt;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;
import pl.edu.agh.gg.transformations.utils.TransformationUtils;

import java.util.*;

import static pl.edu.agh.gg.transformations.utils.TransformationUtils.*;

public class TransformationP3 implements Transformation {

    @Override
    public boolean isApplicable(GraphModel graph, InteriorNode interior) {
        Vertex[] adjVertices = interior.getAdjacentVertices().toArray(new Vertex[0]);

        if (isLower(interior.getLabel()) || adjVertices.length != 3) {
            return false;
        }

        Set<Vertex> longestEdgeVertices = TransformationUtils.getLongestEdge(interior);
        Vertex other = Sets.difference(interior.getAdjacentVertices(), longestEdgeVertices).stream().findAny().get();
        Vertex[] longest = longestEdgeVertices.toArray(new Vertex[]{});
        Coordinates coordsBetweenLongest = new Coordinates(getCordsBetweenX(longest[0], longest[1]),
                getCordsBetweenY(longest[0], longest[1]),
                longest[0].getZCoordinate());

        LayerDescriptor layer = graph.resolveInteriorLayer(interior.getUUID()).get();
        Optional<Vertex> vertBetween = graph.getVerticesOnLayerWithCords(
                new Coordinates(getCordsBetweenX(longest[0], longest[1]), getCordsBetweenY(longest[0], longest[1]), longest[0].getZCoordinate()),
                layer
        );
//        Optional<Vertex> vertBetween = graph.getVerticesBetween(longest[0], longest[1]).stream().findFirst();
        return vertBetween.isPresent() &&
                vertBetween.get().getCoordinates().equals(coordsBetweenLongest) &&
                !vertBetween.get().equals(other) &&
                graph.getEdgeBetweenNodes(longest[0], other).isPresent() &&
                graph.getEdgeBetweenNodes(longest[1], other).isPresent() &&
                graph.getEdgeBetweenNodes(longest[0], vertBetween.get()).isPresent() &&
                graph.getEdgeBetweenNodes(longest[1], vertBetween.get()).isPresent();
    }

    @Override
    public void transform(GraphModel graph, InteriorNode interior) {
        interior.setLabel(interior.getLabel().toLowerCase());
        LayerDescriptor nextLayerDescriptor = graph.resolveInteriorLayer(interior.getUUID()).get().getNextLayerDescriptor();

        Set<Vertex> longestEdgeVertices = TransformationUtils.getLongestEdge(interior);
        Vertex other = Sets.difference(interior.getAdjacentVertices(), longestEdgeVertices).stream().findAny().get();
        Vertex[] longest = longestEdgeVertices.toArray(new Vertex[]{});

        LayerDescriptor layer = graph.resolveInteriorLayer(interior.getUUID()).get();
        Vertex vertBetween = graph.getVerticesOnLayerWithCords(
                new Coordinates(getCordsBetweenX(longest[0], longest[1]), getCordsBetweenY(longest[0], longest[1]), longest[0].getZCoordinate()),
                layer
        ).get();
//        Optional<Vertex> vertBetween = graph.getVerticesBetween(longest[0], longest[1]).stream().findFirst();

        final Vertex v1 = graph.insertVertex("V1", longest[0].getCoordinates(), nextLayerDescriptor).get();
        final Vertex v2 = graph.insertVertex("V2", other.getCoordinates(), nextLayerDescriptor).get();
        final Vertex v3 = graph.insertVertex("V3", longest[1].getCoordinates(), nextLayerDescriptor).get();
        final Vertex v4 = graph.insertVertex("V4", vertBetween.getCoordinates(), nextLayerDescriptor).get();

        graph.insertEdge(v1, v4, nextLayerDescriptor);
        graph.insertEdge(v1, v2, nextLayerDescriptor);
        graph.insertEdge(v2, v3, nextLayerDescriptor);
        graph.insertEdge(v2, v4, nextLayerDescriptor);
        graph.insertEdge(v3, v4, nextLayerDescriptor);

        final InteriorNode i1 = graph.insertInterior("I", nextLayerDescriptor, v1, v2, v4).get();
        final InteriorNode i2 = graph.insertInterior("I", nextLayerDescriptor, v2, v3, v4).get();

        graph.insertEdge(i1, interior);
        graph.insertEdge(i2, interior);
    }
}
