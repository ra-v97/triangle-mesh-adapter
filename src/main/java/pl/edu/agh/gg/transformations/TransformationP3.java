package pl.edu.agh.gg.transformations;

import com.google.common.collect.Sets;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;
import pl.edu.agh.gg.transformations.utils.TransformationUtils;

import java.util.Optional;
import java.util.Set;

import static pl.edu.agh.gg.transformations.utils.TransformationUtils.isUpper;

public class TransformationP3 implements Transformation {

    @Override
    public boolean isApplicable(GraphModel graph, InteriorNode interior) {
        Vertex[] aV = interior.getAdjacentVertices().toArray(new Vertex[0]);

        if (aV.length != 3) {
            return false;
        }

        Set<Vertex> longestEdgeVertices = TransformationUtils.getLongestEdge(interior);
        Vertex[] longest = longestEdgeVertices.toArray(new Vertex[]{});

        LayerDescriptor layer = graph.resolveInteriorLayer(interior.getUUID()).get();
//        TODO: this production should be applicable for graphs where the additional vertex is between v0 and v2
//         or rather between the vertices that have the longest edge?
        Optional<Vertex> v13 = graph.getVerticesOnLayerWithCords(
                new Coordinates(
                        TransformationUtils.getCordsBetweenX(longest[0], longest[1]),
                        TransformationUtils.getCordsBetweenY(longest[0], longest[1]),
                        aV[0].getZCoordinate()),
                layer
        );

        // TODO additional checks?
        return isUpper(interior.getLabel()) &&
                v13.isPresent() &&
                graph.getEdgeBetweenNodes(aV[2], v13.get()).isPresent() &&
                graph.getEdgeBetweenNodes(v13.get(), aV[0]).isPresent();
    }

    @Override
    public void transform(GraphModel graph, InteriorNode interior) {
        interior.setLabel(interior.getLabel().toLowerCase());

        LayerDescriptor nextLayerDescriptor = graph.resolveInteriorLayer(interior.getUUID()).get().getNextLayerDescriptor();
        Set<Vertex> longestEdgeVertices = TransformationUtils.getLongestEdge(interior);
        Vertex[] longest = longestEdgeVertices.toArray(new Vertex[]{});
        Vertex other = Sets.difference(interior.getAdjacentVertices(), longestEdgeVertices).stream().findAny().get();

//        TODO consistency of vertices naming - f.e. what if the longest edge were between V1 and V2, not V1 and V3?
        final Vertex v1 = graph.insertVertex("V1", longest[0].getCoordinates(), nextLayerDescriptor).get();
        final Vertex v2 = graph.insertVertex("V2", other.getCoordinates(), nextLayerDescriptor).get();
        final Vertex v3 = graph.insertVertex("V3", longest[1].getCoordinates(), nextLayerDescriptor).get();

        final Vertex v13 = graph.insertVertex("V4",
                new Coordinates(
                        TransformationUtils.getCordsBetweenX(longest[0], longest[1]),
                        TransformationUtils.getCordsBetweenY(longest[0], longest[1]),
                        longest[0].getZCoordinate()),
                nextLayerDescriptor
        ).get();

        graph.insertEdge(v1, v13, nextLayerDescriptor);
        graph.insertEdge(v1, v2, nextLayerDescriptor);
        graph.insertEdge(v2, v3, nextLayerDescriptor);
        graph.insertEdge(v2, v13, nextLayerDescriptor);
        graph.insertEdge(v3, v13, nextLayerDescriptor);

        final InteriorNode i1 = graph.insertInterior("I", nextLayerDescriptor, v1, v2, v13).get();
        final InteriorNode i2 = graph.insertInterior("I", nextLayerDescriptor, v2, v3, v13).get();

        graph.insertEdge(i1, interior);
        graph.insertEdge(i2, interior);
    }
}
