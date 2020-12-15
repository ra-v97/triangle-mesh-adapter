package pl.edu.agh.gg.transformations;

import com.google.common.collect.Sets;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;
import pl.edu.agh.gg.transformations.utils.TransformationUtils;
import pl.edu.agh.gg.utils.PositionCalculator;
import scala.Array;

import java.util.Optional;
import java.util.Set;

import static pl.edu.agh.gg.transformations.utils.TransformationUtils.*;

public class TransformationP5 implements Transformation {

    @Override
    public boolean isApplicable(GraphModel graph, InteriorNode interior) {
        Vertex[] iV = interior.getAdjacentVertices().toArray(new Vertex[0]);


        LayerDescriptor layer = graph.resolveInteriorLayer(interior.getUUID()).get();
        Optional<Vertex> v01 = graph.getVerticesOnLayerWithCords(
                new Coordinates(getCordsBetweenX(iV[0],iV[1]),getCordsBetweenY(iV[0],iV[1]), iV[0].getZCoordinate()),
                layer
        );
        Optional<Vertex> v02 = graph.getVerticesOnLayerWithCords(
                new Coordinates(getCordsBetweenX(iV[0],iV[2]),getCordsBetweenY(iV[0],iV[2]), iV[0].getZCoordinate()),
                layer
        );
        Optional<Vertex> v21 = graph.getVerticesOnLayerWithCords(
                new Coordinates(getCordsBetweenX(iV[2],iV[1]),getCordsBetweenY(iV[2],iV[1]), iV[2].getZCoordinate()),
                layer
        );
        // TODO TEST
        return isUpper(interior.getLabel()) &&
                interior.getAdjacentVertices().size() == 3 &&
                v01.isPresent() && v02.isPresent() && v21.isPresent() &&
                graph.getEdgeBetweenNodes(iV[0], v01.get()).isPresent() &&
                graph.getEdgeBetweenNodes(v01.get(), iV[1]).isPresent() &&
                graph.getEdgeBetweenNodes(iV[1], v21.get()).isPresent() &&
                graph.getEdgeBetweenNodes(v21.get(), iV[2]).isPresent() &&
                graph.getEdgeBetweenNodes(iV[2], v02.get()).isPresent() &&
                graph.getEdgeBetweenNodes(v02.get(), iV[0]).isPresent();
    }

    @Override
    public void transform(GraphModel graph, InteriorNode interior) {
        interior.setLabel(interior.getLabel().toLowerCase());

        LayerDescriptor nextLayerDescriptor = graph.resolveInteriorLayer(interior.getUUID()).get().getNextLayerDescriptor();
        Vertex[] iV = interior.getAdjacentVertices().toArray(new Vertex[0]);


        Set<Vertex> longestEdgeVertices = TransformationUtils.getLongestEdge(interior);
        Vertex[] longest = longestEdgeVertices.toArray(new Vertex[]{});
        Vertex other = Sets.difference(interior.getAdjacentVertices(), longestEdgeVertices).stream().findAny().get();


        final Vertex v0 = graph.insertVertex("V1", longest[0].getCoordinates(), nextLayerDescriptor).get();
        final Vertex v1 = graph.insertVertex("V2", longest[1].getCoordinates(), nextLayerDescriptor).get();
        final Vertex v2 = graph.insertVertex("V3", other.getCoordinates(), nextLayerDescriptor).get();



        final Vertex v01 = graph.insertVertex("V4",
                new Coordinates(getCordsBetweenX(longest[0],longest[1]),getCordsBetweenY(longest[0],longest[1]), longest[0].getZCoordinate()),
                nextLayerDescriptor
        ).get();
        final Vertex v02 = graph.insertVertex("V5",
                new Coordinates(getCordsBetweenX(longest[0],other),getCordsBetweenY(longest[0],other), longest[0].getZCoordinate()),
                nextLayerDescriptor
        ).get();
        final Vertex v21 = graph.insertVertex("V6",
                new Coordinates(getCordsBetweenX(other,longest[1]),getCordsBetweenY(other,longest[1]), other.getZCoordinate()),
                nextLayerDescriptor
        ).get();

        graph.insertEdge(v0, v01, nextLayerDescriptor);
        graph.insertEdge(v01, v1, nextLayerDescriptor);
        graph.insertEdge(v1, v21, nextLayerDescriptor);
        graph.insertEdge(v21, v2, nextLayerDescriptor);
        graph.insertEdge(v2, v02, nextLayerDescriptor);
        graph.insertEdge(v02, v0, nextLayerDescriptor);

        graph.insertEdge(v02, v01, nextLayerDescriptor);
        graph.insertEdge(v21, v01, nextLayerDescriptor);
        graph.insertEdge(v2, v01, nextLayerDescriptor);

        final InteriorNode i1 = graph.insertInterior("I", nextLayerDescriptor, v0, v01, v02).get();
        final InteriorNode i2 = graph.insertInterior("I", nextLayerDescriptor, v02, v01, v2).get();
        final InteriorNode i3 = graph.insertInterior("I", nextLayerDescriptor, v2, v01, v21).get();
        final InteriorNode i4 = graph.insertInterior("I", nextLayerDescriptor, v21, v01, v1).get();

        graph.insertEdge(i1, interior);
        graph.insertEdge(i2, interior);
        graph.insertEdge(i3, interior);
        graph.insertEdge(i4, interior);
    }
}
