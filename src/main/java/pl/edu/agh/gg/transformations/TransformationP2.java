package pl.edu.agh.gg.transformations;

import com.google.common.collect.Sets;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;
import pl.edu.agh.gg.transformations.utils.TransformationUtils;
import pl.edu.agh.gg.utils.PositionCalculator;

import java.util.Set;

import static pl.edu.agh.gg.transformations.utils.TransformationUtils.isUpper;
import static pl.edu.agh.gg.utils.PositionCalculator.checkTriangleInequality;

public class TransformationP2 implements Transformation {
    @Override
    public boolean isApplicable(GraphModel graph, InteriorNode interior) {
        Vertex[] aV = interior.getAdjacentVertices().toArray(new Vertex[0]);
        return isUpper(interior.getLabel()) &&
                interior.getAdjacentVertices().size() == 3 &&
                graph.getEdgeBetweenNodes(aV[0], aV[1]).isPresent() &&
                graph.getEdgeBetweenNodes(aV[1], aV[2]).isPresent() &&
                graph.getEdgeBetweenNodes(aV[2], aV[0]).isPresent() &&
                checkTriangleInequality(aV[0].getCoordinates(), aV[1].getCoordinates(), aV[2].getCoordinates());
    }

    @Override
    public void transform(GraphModel graph, InteriorNode interior) {
        interior.setLabel(interior.getLabel().toLowerCase());

        LayerDescriptor nextLayerDescriptor = graph.resolveInteriorLayer(interior.getUUID()).get().getNextLayerDescriptor();
        Set<Vertex> longestEdgeVertices = TransformationUtils.getLongestEdge(interior);
        Vertex other = Sets.difference(interior.getAdjacentVertices(), longestEdgeVertices).stream().findAny().get();
        Vertex[] longest = longestEdgeVertices.toArray(new Vertex[]{});

        Coordinates midpointCoords = PositionCalculator.getMidpointCoordinates(longest[0], longest[1]);
        final Vertex v1 = graph.insertVertex("V1", longest[0].getCoordinates(), nextLayerDescriptor).get();
        final Vertex v2 = graph.insertVertex("V2", other.getCoordinates(), nextLayerDescriptor).get();
        final Vertex v3 = graph.insertVertex("V3", longest[1].getCoordinates(), nextLayerDescriptor).get();
        final Vertex v4 = graph.insertVertex("V4", midpointCoords, nextLayerDescriptor).get();

        graph.insertEdge(v1, v2, nextLayerDescriptor);
        graph.insertEdge(v2, v3, nextLayerDescriptor);
        graph.insertEdge(v3, v4, nextLayerDescriptor);
        graph.insertEdge(v4, v1, nextLayerDescriptor);
        graph.insertEdge(v2, v4, nextLayerDescriptor);

        final InteriorNode i1 = graph.insertInterior("I", nextLayerDescriptor, v1, v2, v4).get();
        final InteriorNode i2 = graph.insertInterior("I", nextLayerDescriptor, v2, v3, v4).get();
        graph.insertEdge(i1, interior);
        graph.insertEdge(i2, interior);
    }
}
