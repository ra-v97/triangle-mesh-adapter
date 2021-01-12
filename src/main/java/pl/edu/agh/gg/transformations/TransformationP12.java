package pl.edu.agh.gg.transformations;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.javatuples.Pair;
import org.javatuples.Tuple;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.GraphNode;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;

import java.util.*;
import java.util.stream.Collectors;

import static pl.edu.agh.gg.transformations.utils.TransformationUtils.isUpper;

public class TransformationP12 implements DoubleInteriorTransformation {

    @Override
    public boolean isApplicable(GraphModel graph, InteriorNode first, InteriorNode second) {
        if (!isUpper(first.getLabel()) || !isUpper(second.getLabel())) {
            return false;
        }

        LayerDescriptor bottomLayer = graph.resolveInteriorLayer(first.getUUID()).get();
        if (!graph.resolveInteriorLayer(second.getUUID()).get().equals(bottomLayer)) {
            return false;
        }

        InteriorNode firstParent = first.getAdjacentInteriors().stream().filter(i -> graph.resolveInteriorLayer(i.getUUID()).get().getNextLayerDescriptor().equals(bottomLayer)).findFirst().get();
        InteriorNode secondParent = second.getAdjacentInteriors().stream().filter(i -> graph.resolveInteriorLayer(i.getUUID()).get().getNextLayerDescriptor().equals(bottomLayer)).findFirst().get();

        Set<Vertex> vertices1 = first.getAdjacentVertices();
        Set<Vertex> vertices2 = second.getAdjacentVertices();
        Set<Pair<Double, Double>> vertices1Coords = vertices1.stream().map(v -> new Pair<>(v.getXCoordinate(), v.getYCoordinate())).collect(Collectors.toSet());
        Set<Pair<Double, Double>> vertices2Coords = vertices2.stream().map(v -> new Pair<>(v.getXCoordinate(), v.getYCoordinate())).collect(Collectors.toSet());

        Set<Vertex> sameVertices1 = vertices1.stream().filter(v -> vertices2Coords.contains(new Pair<>(v.getXCoordinate(), v.getYCoordinate()))).collect(Collectors.toSet());
        Set<Vertex> sameVertices2 = vertices2.stream().filter(v -> vertices1Coords.contains(new Pair<>(v.getXCoordinate(), v.getYCoordinate()))).collect(Collectors.toSet());

        if (sameVertices1.size() != 2 || sameVertices2.size() != 2) {
            return false;
        }

        Set<Vertex> commonParentVertices = Sets.intersection(firstParent.getAdjacentVertices(), secondParent.getAdjacentVertices());

        if (commonParentVertices.size() != 2) {
            return false;
        }

        return commonParentVertices.stream().map(v -> new Pair<>(v.getXCoordinate(), v.getYCoordinate())).collect(Collectors.toSet()).equals(sameVertices2.stream().map(v -> new Pair<>(v.getXCoordinate(), v.getYCoordinate())).collect(Collectors.toSet()));
    }

    @Override
    public void transform(GraphModel graph, InteriorNode first, InteriorNode second) {
        LayerDescriptor layer = graph.resolveInteriorLayer(first.getUUID()).get();
        Set<Vertex> vertices1 = first.getAdjacentVertices();
        Set<Vertex> vertices2 = second.getAdjacentVertices();
        InteriorNode secondParent = second.getAdjacentInteriors().stream().filter(i -> graph.resolveInteriorLayer(i.getUUID()).get().getNextLayerDescriptor().equals(graph.resolveInteriorLayer(second.getUUID()).get())).findFirst().get();

        Set<Pair<Double, Double>> vertices1Coords = vertices1.stream().map(v -> new Pair<>(v.getXCoordinate(), v.getYCoordinate())).collect(Collectors.toSet());
        Set<Pair<Double, Double>> vertices2Coords = vertices2.stream().map(v -> new Pair<>(v.getXCoordinate(), v.getYCoordinate())).collect(Collectors.toSet());
        Vertex[] sameVertices1 = vertices1.stream().filter(v -> vertices2Coords.contains(new Pair<>(v.getXCoordinate(), v.getYCoordinate()))).collect(Collectors.toSet()).toArray(Vertex[]::new);
        Set<Vertex> sameVertices2Set = vertices2.stream().filter(v -> vertices1Coords.contains(new Pair<>(v.getXCoordinate(), v.getYCoordinate()))).collect(Collectors.toSet());
        Vertex[] sameVertices2 = sameVertices2Set.toArray(Vertex[]::new);
        Vertex singleVertex2 = Sets.difference(vertices2, sameVertices2Set).stream().findAny().get();

        List<Pair<Vertex, Vertex>> pairs = Lists.newLinkedList();

        if (sameVertices1[0].getXCoordinate() == sameVertices2[0].getXCoordinate() && sameVertices1[0].getYCoordinate() == sameVertices2[0].getYCoordinate()) {
            pairs.add(new Pair<>(sameVertices1[0], sameVertices2[0]));
            pairs.add(new Pair<>(sameVertices1[1], sameVertices2[1]));
        } else {
            pairs.add(new Pair<>(sameVertices1[0], sameVertices2[1]));
            pairs.add(new Pair<>(sameVertices1[1], sameVertices2[0]));
        }

        for (Pair<Vertex, Vertex> pair : pairs) {
            graph.removeEdge(graph.getEdgeBetweenNodes(singleVertex2, pair.getValue1()).get().getUUID());
            graph.insertEdge(pair.getValue0(), singleVertex2, layer);
        }

        graph.removeInterior(second);
        graph.removeEdge(graph.getEdgeBetweenNodes(pairs.get(0).getValue1(), pairs.get(1).getValue1()).get().getUUID());
        graph.removeVertex(pairs.get(0).getValue1());
        graph.removeVertex(pairs.get(1).getValue1());

        InteriorNode i = graph.insertInterior("I2", layer, pairs.get(0).getValue0(), pairs.get(1).getValue0(), singleVertex2).get();
        graph.insertEdge(i, secondParent);
    }
}
