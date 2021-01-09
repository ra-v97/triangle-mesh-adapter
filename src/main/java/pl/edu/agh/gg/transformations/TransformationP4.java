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

import static pl.edu.agh.gg.transformations.utils.TransformationUtils.*;

public class TransformationP4 implements Transformation {

    @Override
    public boolean isApplicable(GraphModel graph, InteriorNode interior) {
        Vertex[] iV = interior.getAdjacentVertices().toArray(new Vertex[0]);

        LayerDescriptor layer = graph.resolveInteriorLayer(interior.getUUID()).get();

        iV = rotateToProperOrder(iV, layer, graph, interior);

        Optional<Vertex> v01 = graph.getVerticesOnLayerWithCords(
                new Coordinates(getCordsBetweenX(iV[0], iV[1]), getCordsBetweenY(iV[0], iV[1]), iV[0].getZCoordinate()),
                layer
        );
        Optional<Vertex> v02 = graph.getVerticesOnLayerWithCords(
                new Coordinates(getCordsBetweenX(iV[0], iV[2]), getCordsBetweenY(iV[0], iV[2]), iV[0].getZCoordinate()),
                layer
        );
        Optional<Vertex> v21 = graph.getVerticesOnLayerWithCords(
                new Coordinates(getCordsBetweenX(iV[2], iV[1]), getCordsBetweenY(iV[2], iV[1]), iV[2].getZCoordinate()),
                layer
        );

        boolean are12connected = (
                v21.isPresent() &&
                        graph.getEdgeBetweenNodes(iV[1], v21.get()).isPresent() &&
                        graph.getEdgeBetweenNodes(v21.get(), iV[2]).isPresent()

        ) || (
                graph.getEdgeBetweenNodes(iV[1], iV[2]).isPresent()
        );

        return isUpper(interior.getLabel()) &&
                interior.getAdjacentVertices().size() == 3 &&
                v01.isPresent() &&
                v02.isPresent() &&
                graph.getEdgeBetweenNodes(iV[0], v01.get()).isPresent() &&
                graph.getEdgeBetweenNodes(v01.get(), iV[1]).isPresent() &&
                graph.getEdgeBetweenNodes(iV[2], v02.get()).isPresent() &&
                graph.getEdgeBetweenNodes(v02.get(), iV[0]).isPresent() &&
                are12connected;
    }

    @Override
    public void transform(GraphModel graph, InteriorNode interior) {
        interior.setLabel(interior.getLabel().toLowerCase());

        Vertex[] iV = interior.getAdjacentVertices().toArray(new Vertex[0]);
        LayerDescriptor layer = graph.resolveInteriorLayer(interior.getUUID()).get();

        iV = rotateToProperOrder(iV, layer, graph, interior);

        LayerDescriptor nextLayerDescriptor = graph.resolveInteriorLayer(interior.getUUID()).get().getNextLayerDescriptor();

        final Vertex v0 = graph.insertVertex("V1", iV[0].getCoordinates(), nextLayerDescriptor).get();
        final Vertex v1 = graph.insertVertex("V2", iV[1].getCoordinates(), nextLayerDescriptor).get();
        final Vertex v2 = graph.insertVertex("V3", iV[2].getCoordinates(), nextLayerDescriptor).get();


        final Vertex v01 = graph.insertVertex("V4",
                new Coordinates(getCordsBetweenX(iV[0], iV[1]), getCordsBetweenY(iV[0], iV[1]), iV[0].getZCoordinate()),
                nextLayerDescriptor
        ).get();
        final Vertex v02 = graph.insertVertex("V5",
                new Coordinates(getCordsBetweenX(iV[0], iV[2]), getCordsBetweenY(iV[0], iV[2]), iV[0].getZCoordinate()),
                nextLayerDescriptor
        ).get();

        graph.insertEdge(v0, v01, nextLayerDescriptor);
        graph.insertEdge(v01, v1, nextLayerDescriptor);
        graph.insertEdge(v1, v2, nextLayerDescriptor);
        graph.insertEdge(v2, v02, nextLayerDescriptor);
        graph.insertEdge(v02, v0, nextLayerDescriptor);

        graph.insertEdge(v02, v01, nextLayerDescriptor);
        graph.insertEdge(v1, v02, nextLayerDescriptor);

        final InteriorNode i1 = graph.insertInterior("I1", nextLayerDescriptor, v0, v01, v02).get();
        final InteriorNode i2 = graph.insertInterior("I2", nextLayerDescriptor, v02, v01, v1).get();
        final InteriorNode i3 = graph.insertInterior("I3", nextLayerDescriptor, v1, v02, v2).get();

        graph.insertEdge(i1, interior);
//        graph.insertEdge(i2, interior);
        graph.insertEdge(i3, interior);
    }

    private Vertex[] rotateToProperOrder(Vertex[] iV, LayerDescriptor layer, GraphModel graph, InteriorNode interior) {
        Set<Vertex> longestEdgeVertices = TransformationUtils.getLongestEdge(interior);
        Vertex[] longest = longestEdgeVertices.toArray(new Vertex[]{});
        Vertex other = Sets.difference(interior.getAdjacentVertices(), longestEdgeVertices).stream().findAny().get();

        Optional<Vertex> ol0 = graph.getVerticesOnLayerWithCords(
                new Coordinates(getCordsBetweenX(longest[0], other), getCordsBetweenY(longest[0], other), longest[0].getZCoordinate()),
                layer
        );
        Optional<Vertex> ol1 = graph.getVerticesOnLayerWithCords(
                new Coordinates(getCordsBetweenX(longest[1], other), getCordsBetweenY(longest[1], other), longest[1].getZCoordinate()),
                layer
        );
        Optional<Vertex> ll = graph.getVerticesOnLayerWithCords(
                new Coordinates(getCordsBetweenX(longest[1], longest[0]), getCordsBetweenY(longest[1], longest[0]), longest[1].getZCoordinate()),
                layer
        );

        Vertex[] iV2 = new Vertex[3];

        if (ll.isPresent()) {
            iV2[1] = other;
            if (!ol0.isPresent()) {
                iV2[0] = longest[1];
                iV2[2] = longest[0];
            }
            else {
                iV2[0] = longest[0];
                iV2[2] = longest[1];
            }
        }
        else {
            iV2[0] = other;
            if (Double.compare(TransformationUtils.get2DDistance(longest[0], other), TransformationUtils.get2DDistance(longest[1], other)) <= 0) {
                iV2[1] = longest[0];
                iV2[2] = longest[1];
            }
            else {
                iV2[1] = longest[1];
                iV2[2] = longest[0];
            }
        }

        return iV2;
    }

}