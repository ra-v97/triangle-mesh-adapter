package pl.edu.agh.gg.transformations;

import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.StartingNode;
import pl.edu.agh.gg.model.Vertex;

import java.util.Optional;

import static pl.edu.agh.gg.transformations.utils.TransformationUtils.isUpper;

public class TransformationP1 implements Transformation {
    @Override
    public boolean isApplicable(GraphModel graph, InteriorNode interior) {
        Optional<InteriorNode> startingNode = graph.getInteriors().stream().findFirst();
        return graph.getVertices().isEmpty() &&
                graph.getEdges().isEmpty() &&
                graph.getInteriors().size() == 1 &&
                startingNode.get() instanceof StartingNode &&
                isUpper(startingNode.get().getLabel());
    }

    @Override
    public void transform(GraphModel graph, InteriorNode interior) {
        StartingNode startingNode = (StartingNode) graph.getInteriors().stream().findFirst().get();
        startingNode.setLabel(startingNode.getLabel().toLowerCase());
        LayerDescriptor nextLayerDescriptor = graph.resolveInteriorLayer(startingNode.getUUID()).get().getNextLayerDescriptor();
        Coordinates stNoCo = startingNode.getCoordinates();
        Coordinates v1Co = new Coordinates(stNoCo.getX() - 1, stNoCo.getY() - 1, stNoCo.getZ()); // TODO: those coordinates will probably change to accommodate the size of the map
        Coordinates v2Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() - 1, stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() - 1, stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v4Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() + 1, stNoCo.getZ());

        final Vertex v1 = graph.insertVertex("V1", v1Co, nextLayerDescriptor).get();
        final Vertex v2 = graph.insertVertex("V2", v2Co, nextLayerDescriptor).get();
        final Vertex v3 = graph.insertVertex("V3", v3Co, nextLayerDescriptor).get();
        final Vertex v4 = graph.insertVertex("V4", v4Co, nextLayerDescriptor).get();

        graph.insertEdge(v1, v2, nextLayerDescriptor);
        graph.insertEdge(v1, v3, nextLayerDescriptor);
        graph.insertEdge(v2, v4, nextLayerDescriptor);
        graph.insertEdge(v3, v4, nextLayerDescriptor);
        graph.insertEdge(v2, v3, nextLayerDescriptor);

        final InteriorNode i1 = graph.insertInterior("I", nextLayerDescriptor, v1, v2, v3).get();
        final InteriorNode i2 = graph.insertInterior("I", nextLayerDescriptor, v2, v3, v4).get();
        graph.insertEdge(i1, startingNode);
        graph.insertEdge(i2, startingNode);
    }
}
