package utils;

import pl.edu.agh.gg.model.GraphEdge;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class GraphTestUtils {

    private GraphTestUtils() {
    }

    public static Optional<InteriorNode> resolveGraphInterior(GraphModel graphModel){
        return graphModel.getInteriors()
                .stream()
                .findFirst();
    }

    public static Set<GraphEdge> getEdges(GraphModel graph, Vertex vertex, GraphEdge.GraphEdgeType edgeType) {
        return graph.getEdges().stream()
                .filter(graphEdge -> graphEdge.getEdgeNodes().contains(vertex))
                .filter(graphEdge -> graphEdge.getType() == edgeType)
                .collect(Collectors.toSet());
    }
}
