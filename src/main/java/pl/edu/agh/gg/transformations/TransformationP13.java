package pl.edu.agh.gg.transformations;

import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.StartingNode;
import pl.edu.agh.gg.model.Vertex;

import java.util.*;

public class TransformationP13 implements DoubleInteriorTransformation {

    public class UpperLayerValidator {
        public boolean isValid(List<Vertex> commonAdjacents, GraphModel graph) {
            if (commonAdjacents.size() != 2
                    || graph.getEdgeBetweenNodes(commonAdjacents.get(0), commonAdjacents.get(1)).isEmpty())
                return false;

            return true;
        }
    }

    public class LowerLayerValidator {
        public boolean isValid(GraphModel graph, InteriorNode firstInterior, InteriorNode secondInterior) {
            List<Vertex> commonAdjacents = getCommonAdjacents(firstInterior, secondInterior);
            if (!isLowerApplicable(graph, firstInterior, commonAdjacents) || !isLowerApplicable(graph, secondInterior, commonAdjacents))
                return false;

            return true;
        }
    }

    @Override
    public boolean isApplicable(GraphModel graph, InteriorNode firstInterior, InteriorNode secondInterior) {
        List<Vertex> commonAdjacents = getCommonAdjacents(firstInterior, secondInterior);

        LayerDescriptor layer1 = graph.resolveInteriorLayer(firstInterior.getUUID()).get();
        LayerDescriptor layer2 = graph.resolveInteriorLayer(secondInterior.getUUID()).get();

        // Different layers
        if (!layer1.equals(layer2))
            return false;

        var upperLayerValidator = new UpperLayerValidator();
        if (!upperLayerValidator.isValid(commonAdjacents, graph))
            return false;

        // Validate lower layer
        var lowerLayerValidator = new LowerLayerValidator();
        if (!lowerLayerValidator.isValid(graph, firstInterior, secondInterior))
            return false;

        List<InteriorNode> firstValidAdjacentInteriors = getValidAdjacentInteriors(firstInterior, graph);
        List<InteriorNode> secondValidAdjacentInteriors = getValidAdjacentInteriors(secondInterior, graph);

        for (InteriorNode firstValidAdjacentInterior : firstValidAdjacentInteriors) {
            for (InteriorNode secondValidAdjacentInterior : secondValidAdjacentInteriors) {
                // No edge between common nodes

                List<Vertex> nextLayerCommonAdjacents = getNextLayerCommonAdjacents(firstInterior, secondInterior, graph);
                if (nextLayerCommonAdjacents.size() != 4)
                    continue;
                if (graph.getEdgeBetweenNodes(nextLayerCommonAdjacents.get(0), nextLayerCommonAdjacents.get(1)).isEmpty()
                        && nextLayerCommonAdjacents.get(2) == nextLayerCommonAdjacents.get(3))
                    return true;
                if (graph.getEdgeBetweenNodes(nextLayerCommonAdjacents.get(2), nextLayerCommonAdjacents.get(3)).isEmpty()
                        && nextLayerCommonAdjacents.get(0) == nextLayerCommonAdjacents.get(1))
                    return true;
            }
        }
        return false;
    }

    private boolean isLowerApplicable(GraphModel graph, InteriorNode interior, List<Vertex> commonAdjacents) {
//        Set<InteriorNode> adjInteriors = interior.getAdjacentInteriors();
//        ArrayList<InteriorNode> validAdj = new ArrayList<>();
//
//        for (InteriorNode adj : adjInteriors) {
//            LayerDescriptor layer1 = graph.resolveInteriorLayer(interior.getUUID()).get();
//            LayerDescriptor layer2 = graph.resolveInteriorLayer(adj.getUUID()).get();
//
//            if (layer1.getNextLayerDescriptor().equals(layer2))
//                validAdj.add(adj);
//        }

//        if (validAdj.size() != 1)
//            return false;

        if (commonAdjacents.size() != 2)
            return false;

        Vertex[] interiorAdjacentNodes = interior.getAdjacentVertices().toArray(new Vertex[0]);
        if (interiorAdjacentNodes.length < 2) {
            return false;
        }

        Optional<Vertex> v1 = Arrays.stream(interiorAdjacentNodes).filter(v -> v.equals(commonAdjacents.get(0))).findFirst();
        Optional<Vertex> v2 = Arrays.stream(interiorAdjacentNodes).filter(v -> v.equals(commonAdjacents.get(1))).findFirst();

        if (v1.isEmpty() || v2.isEmpty())
            return false;

        return graph.getEdgeBetweenNodes(v1.get(), v2.get()).isPresent();
    }

    private List<Vertex> getVertexes(GraphModel graph, InteriorNode interior, List<Vertex> toFind) {
        Set<InteriorNode> adjInteriors = interior.getAdjacentInteriors();
        ArrayList<InteriorNode> validAdj = new ArrayList<>();

        for (InteriorNode adj : adjInteriors) {
            LayerDescriptor layer1 = graph.resolveInteriorLayer(interior.getUUID()).get();
            LayerDescriptor layer2 = graph.resolveInteriorLayer(adj.getUUID()).get();

            if (layer1.getNextLayerDescriptor().equals(layer2))
                validAdj.add(adj);
        }

        Vertex[] firstInteriorAdjacents = validAdj.get(0).getAdjacentVertices().toArray(new Vertex[0]);

        List<Vertex> result = new LinkedList<>();
        for (Vertex v1 : firstInteriorAdjacents) {
            for (Vertex v2 : toFind) {
                if (v1.equals(v2)) {
                    result.add(v1);
                }
            }
        }
        return result;
    }

    @Override
    public void transform(GraphModel graph, InteriorNode firstInterior, InteriorNode secondInterior) {
        LayerDescriptor nextLayerDescriptor = graph.resolveInteriorLayer(firstInterior.getUUID()).get().getNextLayerDescriptor();
        List<Vertex> common = getNextLayerCommonAdjacents(firstInterior, secondInterior, graph);

        List<Vertex> verts1 = Arrays.asList(common.get(0), common.get(2));
        List<Vertex> verts2 = Arrays.asList(common.get(1), common.get(3));

        int index = verts1.get(1) == verts2.get(1) ? 0 : 1;
        Vertex merged = verts1.get(index);
//        merged.setLabel(merged.getLabel() + "__merged");
        ArrayList<Vertex> toChangeEdges = new ArrayList<>();
        ArrayList<InteriorNode> toChangeEdges1 = new ArrayList<>();


        // Edge Nodes
        for (Vertex v : graph.getVertices()) {
            if (graph.getEdgeBetweenNodes(verts2.get(index), v).isPresent()) {
                toChangeEdges.add(v);
            }
        }

        for (Vertex v : toChangeEdges) {
            if (!graph.getEdgeBetweenNodes(verts1.get(index), v).isPresent()) {
                graph.insertEdge(verts1.get(index), v, nextLayerDescriptor);
                graph.removeEdge(verts2.get(index), v);
            }
        }

        // Interiors
        for (InteriorNode n : graph.getInteriors()) {
            if (n.getAdjacentVertices().contains(verts2.get(index))) {
                toChangeEdges1.add(n);
            }
        }
        for (InteriorNode n : toChangeEdges1) {
            graph.insertEdge(verts1.get(index), n, nextLayerDescriptor);
            n.addAdjecentVertex(verts1.get(index));
            graph.removeEdge(verts2.get(index), n);
            n.removeAdjacentVertex(verts2.get(index));
        }

        graph.removeVertex(verts2.get(index));
    }

    private List<Vertex> getCommonAdjacents(InteriorNode firstInterior, InteriorNode secondInterior) {
        Vertex[] firstInteriorAdjacents = firstInterior.getAdjacentVertices().toArray(new Vertex[0]);
        Vertex[] secondInteriorAdjacents = secondInterior.getAdjacentVertices().toArray(new Vertex[0]);
        List<Vertex> common = new LinkedList<>();
        for (Vertex v1 : firstInteriorAdjacents) {
            for (Vertex v2 : secondInteriorAdjacents) {
                if (v1.equals(v2)) common.add(v1);
            }
        }
        return common;
    }

    private List<Vertex> getNextLayerCommonAdjacents(InteriorNode firstInterior, InteriorNode secondInterior, GraphModel graph) {
        List<InteriorNode> firstAdjacentInteriors = getValidAdjacentInteriors(firstInterior, graph);
        List<InteriorNode> secondAdjacentInteriors = getValidAdjacentInteriors(secondInterior, graph);
        for (InteriorNode firstValidAdjacentInterior : firstAdjacentInteriors) {
            for (InteriorNode secondValidAdjacentInterior : secondAdjacentInteriors) {
                Vertex[] firstInteriorAdjacents = firstValidAdjacentInterior.getAdjacentVertices().toArray(new Vertex[0]);
                Vertex[] secondInteriorAdjacents = secondValidAdjacentInterior.getAdjacentVertices().toArray(new Vertex[0]);
                List<Vertex> common = new LinkedList<>();
                for (Vertex v1 : firstInteriorAdjacents) {
                    for (Vertex v2 : secondInteriorAdjacents) {
                        if (v1.equals(v2)) common.add(v1);
                    }
                }
                if (common.size() != 2)
                    continue;
                Vertex v1_1 = Arrays.stream(firstInteriorAdjacents).filter(v -> v.equals(common.get(0))).findFirst().get();
                Vertex v2_1 = Arrays.stream(secondInteriorAdjacents).filter(v -> v.equals(v1_1)).findFirst().get();
                Vertex v1_2 = Arrays.stream(secondInteriorAdjacents).filter(v -> v.equals(common.get(1))).findFirst().get();
                Vertex v2_2 = Arrays.stream(firstInteriorAdjacents).filter(v -> v.equals(v1_2)).findFirst().get();
                if (v2_1 == null || v2_2 == null)
                    continue;
                return Arrays.asList(v1_1, v2_1, v1_2, v2_2);
            }
        }
        return Collections.emptyList();
    }

    private List<InteriorNode> getValidAdjacentInteriors(InteriorNode interior, GraphModel graph) {
        Set<InteriorNode> adjInteriors = interior.getAdjacentInteriors();
        ArrayList<InteriorNode> validAdj = new ArrayList<>();

        for (InteriorNode adj : adjInteriors) {
            LayerDescriptor layer1 = graph.resolveInteriorLayer(interior.getUUID()).get();
            LayerDescriptor layer2 = graph.resolveInteriorLayer(adj.getUUID()).get();

            if (layer1.getNextLayerDescriptor().equals(layer2))
                validAdj.add(adj);
        }

//        if (validAdj.size() != 1)
//            throw new IllegalArgumentException("There should be only one valid adjacent interior.");

        return validAdj;
    }

    public static GraphModel validGraph() {
        GraphModel graphModel = new GraphModel();

        int x1 = 0, y1 = 0;
        int x2 = 100, y2 = 100;

        //layer 0
        LayerDescriptor layer0 = new LayerDescriptor(0);
        StartingNode startingNode = graphModel.insertStartingInterior("e", layer0, new Coordinates(50, 50, 0));

        //layer 1
        LayerDescriptor layer1 = new LayerDescriptor(1);
        Vertex v1_1 = graphModel.insertVertex("E1_1", new Coordinates(0, 100, 100), layer1).get();
        Vertex v1_2 = graphModel.insertVertex("E1_2", new Coordinates(x1, y1, 100), layer1).get();
        Vertex v1_3 = graphModel.insertVertex("E1_2", new Coordinates(100, 0, 100), layer1).get();
        Vertex v1_4 = graphModel.insertVertex("E1_2", new Coordinates(x2, y2, 100), layer1).get();

        InteriorNode i1_1 = graphModel.insertInterior("i1_1", layer1, v1_1, v1_2, v1_4).get();
        InteriorNode i1_2 = graphModel.insertInterior("i1_2", layer1, v1_2, v1_3, v1_4).get();

        //edges between layer 0 and 1
        graphModel.insertEdge(startingNode, i1_1);
        graphModel.insertEdge(startingNode, i1_2);

        //edges in layer 1
        graphModel.insertEdge(v1_1, v1_2, layer1);
        graphModel.insertEdge(v1_2, v1_3, layer1);
        graphModel.insertEdge(v1_3, v1_4, layer1);
        graphModel.insertEdge(v1_4, v1_1, layer1);
        graphModel.insertEdge(v1_2, v1_4, layer1);

        //layer 2
        LayerDescriptor layer2 = new LayerDescriptor(2);
        Vertex v2_1 = graphModel.insertVertex("E2_1", new Coordinates(x1, y1, 200), layer2).get();
        Vertex v2_2 = graphModel.insertVertex("E2_2", new Coordinates(x2, y2, 200), layer2).get();
        Vertex v2_3 = graphModel.insertVertex("E2_3", new Coordinates(x1, y1, 200), layer2).get();
        Vertex v2_4 = graphModel.insertVertex("E2_4", new Coordinates(x2, y2, 200), layer2).get();

        InteriorNode i2_1 = graphModel.insertInterior("I2_1", layer2, v2_1, v2_2).get();
        InteriorNode i2_2 = graphModel.insertInterior("I2_2", layer2, v2_3, v2_4).get();

        //edges between layer 1 and 2
        graphModel.insertEdge(i1_1, i2_1);
        graphModel.insertEdge(i1_2, i2_2);

        //edges in layer 2
        graphModel.insertEdge(v2_1, v2_2, layer2);
        graphModel.insertEdge(v2_3, v2_4, layer2);

        return graphModel;
    }
}