package pl.edu.agh.gg.transformations;

import com.google.common.collect.Sets;
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

        List<Coordinates> coordinatesBetweenNodes = new ArrayList<>();
        verticesBetweenAdjVertices = new ArrayList<>();

        for (int i = 0; i < 3; ++i) {
            for (int j = i + 1; j < 3; ++j) {
                coordinatesBetweenNodes.add(new Coordinates(getCordsBetweenX(adjVertices[i], adjVertices[j]), getCordsBetweenY(adjVertices[i], adjVertices[j]), adjVertices[0].getZCoordinate()));
                verticesBetweenAdjVertices.addAll(graph.getVerticesBetween(adjVertices[i], adjVertices[j]));
            }
        }

        return verticesBetweenAdjVertices.size() == 1 &&
                coordinatesBetweenNodes.contains(verticesBetweenAdjVertices.get(0).getCoordinates());
    }

    @Override
    public void transform(GraphModel graph, InteriorNode interior) {
        interior.setLabel(interior.getLabel().toLowerCase());
        LayerDescriptor nextLayerDescriptor = graph.resolveInteriorLayer(interior.getUUID()).get().getNextLayerDescriptor();

        Vertex[] adjVertices = interior.getAdjacentVertices().toArray(new Vertex[0]);
        Vertex vertexBetween = verticesBetweenAdjVertices.get(0);

        final Vertex v1 = graph.insertVertex("V1", adjVertices[0].getCoordinates(), nextLayerDescriptor).get();
        final Vertex v2 = graph.insertVertex("V2", adjVertices[1].getCoordinates(), nextLayerDescriptor).get();
        final Vertex v3 = graph.insertVertex("V3", adjVertices[2].getCoordinates(), nextLayerDescriptor).get();
        final Vertex v4 = graph.insertVertex("V4", vertexBetween.getCoordinates(), nextLayerDescriptor).get();

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

    List<Vertex> verticesBetweenAdjVertices = new ArrayList<>();
}
