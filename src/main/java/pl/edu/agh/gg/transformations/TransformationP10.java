package pl.edu.agh.gg.transformations;

import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static pl.edu.agh.gg.transformations.utils.TransformationUtils.getCordsBetweenX;
import static pl.edu.agh.gg.transformations.utils.TransformationUtils.getCordsBetweenY;

public class TransformationP10 implements DoubleInteriorTransformation {

    @Override
    public boolean isApplicable(GraphModel graph, InteriorNode firstInterior, InteriorNode secondInterior) {
        Vertex[] firstInteriorAdjacents = firstInterior.getAdjacentVertices().toArray(new Vertex[0]);
        Vertex[] secondInteriorAdjacents = secondInterior.getAdjacentVertices().toArray(new Vertex[0]);
        ArrayList<Vertex> commonAdjacents = new ArrayList<>();

        LayerDescriptor layer1 = graph.resolveInteriorLayer(firstInterior.getUUID()).get();
        LayerDescriptor layer2 = graph.resolveInteriorLayer(secondInterior.getUUID()).get();

        // Different layers
        if (!layer1.equals(layer2))
            return false;

        for (Vertex v1 : firstInteriorAdjacents) {
            for (Vertex v2 : secondInteriorAdjacents) {
                if (v1.equals(v2)) commonAdjacents.add(v1);
            }
        }

        // Validate upper layer
        if (commonAdjacents.size() != 2 || graph.getEdgeBetweenNodes(commonAdjacents.get(0), commonAdjacents.get(1)).isEmpty())
            return false;

        // Validate lower layer
        if (!isLower1Applicable(graph, firstInterior) || !isLower2Applicable(graph, secondInterior))
            return false;

        ArrayList<Vertex> adjVerticesUnder1stInterior = getAdjVertexesOf1stInterior(graph, firstInterior);
        ArrayList<Vertex> adjVerticesUnder2ndInterior = getAdjVertexesOf2ndInterior(graph, secondInterior);

        ArrayList<Vertex> borderAdjVertices = new ArrayList<>();
        for (var vert : adjVerticesUnder1stInterior) {
            if (vert.getCoordinates().equals(commonAdjacents.get(0).getCoordinates()) || vert.getCoordinates().equals(commonAdjacents.get(1).getCoordinates())) {
                borderAdjVertices.add(vert);
            }
        }
        if (borderAdjVertices.size() != 2) return false;

        return adjVerticesUnder2ndInterior.stream().filter(vert -> vert.getCoordinates().equals(borderAdjVertices.get(0).getCoordinates()) || vert.getCoordinates().equals(borderAdjVertices.get(1).getCoordinates())).count() == 2;
    }

    @Override
    public void transform(GraphModel graph, InteriorNode firstInterior, InteriorNode secondInterior) {
        LayerDescriptor nextLayerDescriptor = graph.resolveInteriorLayer(firstInterior.getUUID()).get().getNextLayerDescriptor();

        // Validate lower layer
        ArrayList<Vertex> adjVerticesUnder1stInterior = getAdjVertexesOf1stInterior(graph, firstInterior);
        ArrayList<Vertex> adjVerticesUnder2ndInterior = getAdjVertexesOf2ndInterior(graph, secondInterior);

        ArrayList<Vertex> commonVerticesUnder1stInterior = new ArrayList<>();
        ArrayList<Vertex> commonVerticesUnder2ndInterior = new ArrayList<>();

        for (var v1 : adjVerticesUnder1stInterior) {
            for (var v2 : adjVerticesUnder2ndInterior) {
                if (v1.getCoordinates().equals(v2.getCoordinates())) {
                    commonVerticesUnder1stInterior.add(v1);
                    commonVerticesUnder2ndInterior.add(v2);
                }
            }
        }

        graph.removeEdge(commonVerticesUnder2ndInterior.get(0), commonVerticesUnder2ndInterior.get(1));

        for (int i = 0; i < commonVerticesUnder2ndInterior.size(); i++) {
            ArrayList<Vertex> toChangeEdges = new ArrayList<>();
            ArrayList<InteriorNode> toChangeInteriorNodes = new ArrayList<>();

            // Edge Nodes
            for (Vertex v : graph.getVertices()) {
                if (graph.getEdgeBetweenNodes(commonVerticesUnder2ndInterior.get(i), v).isPresent()) {
                    toChangeEdges.add(v);
                }
            }

            for (Vertex v : toChangeEdges) {
                graph.insertEdge(commonVerticesUnder1stInterior.get(i), v, nextLayerDescriptor);
                graph.removeEdge(commonVerticesUnder2ndInterior.get(i), v);
            }

            // Interiors
            for (InteriorNode n : graph.getInteriors()) {
                if (n.getAdjacentVertices().contains(commonVerticesUnder2ndInterior.get(i))) {
                    toChangeInteriorNodes.add(n);
                }
            }

            for (InteriorNode n : toChangeInteriorNodes) {
                graph.insertEdge(commonVerticesUnder1stInterior.get(i), n, nextLayerDescriptor);
                n.addAdjecentVertex(commonVerticesUnder1stInterior.get(i));
                graph.removeEdge(commonVerticesUnder2ndInterior.get(i), n);
                n.removeAdjacentVertex(commonVerticesUnder2ndInterior.get(i));
            }
        }

        for (Vertex vert : commonVerticesUnder2ndInterior) {
            graph.removeVertex(vert);
        }
    }

    private boolean isLower1Applicable(GraphModel graph, InteriorNode firstInterior) {
        Set<InteriorNode> adjInteriors = firstInterior.getAdjacentInteriors();
        ArrayList<InteriorNode> validAdj = new ArrayList<>();

        for (InteriorNode adj : adjInteriors) {
            LayerDescriptor layer1 = graph.resolveInteriorLayer(firstInterior.getUUID()).get();
            LayerDescriptor layer2 = graph.resolveInteriorLayer(adj.getUUID()).get();

            if (layer1.getNextLayerDescriptor().equals(layer2))
                validAdj.add(adj);
        }

        if (validAdj.size() != 2) return false;

        Vertex[] firstInteriorAdjacents = validAdj.get(0).getAdjacentVertices().toArray(new Vertex[0]);
        Vertex[] secondInteriorAdjacents = validAdj.get(1).getAdjacentVertices().toArray(new Vertex[0]);

        ArrayList<Vertex> uncommonInterior1 = new ArrayList<>();
        ArrayList<Vertex> uncommonInterior2 = new ArrayList<>();
        ArrayList<Vertex> common = new ArrayList<>();

        for (Vertex v1 : firstInteriorAdjacents) {
            boolean comm = false;
            for (Vertex v2 : secondInteriorAdjacents) {
                if (v1.equals(v2)) {
                    common.add(v1);
                    comm = true;
                }
            }

            if (!comm) uncommonInterior1.add(v1);
        }

        for (Vertex v2 : secondInteriorAdjacents) {
            boolean comm = false;
            for (Vertex v3 : common) {
                if (v3.equals(v2)) {
                    comm = true;
                }
            }
            if (!comm) uncommonInterior2.add(v2);
        }

        for (Vertex v1 : uncommonInterior1) {
            for (Vertex v2 : uncommonInterior2) {
                for (Vertex v3 : common) {
                    if (v3.getXCoordinate() == getCordsBetweenX(v1, v2) &&
                            v3.getYCoordinate() == getCordsBetweenY(v1, v2) &&
                            graph.getEdgeBetweenNodes(v1, v3).isPresent() &&
                            graph.getEdgeBetweenNodes(v2, v3).isPresent()
                    ) return true;
                }
            }
        }

        return false;
    }

    private boolean isLower2Applicable(GraphModel graph, InteriorNode secondInterior) {
        Set<InteriorNode> adjInteriors = secondInterior.getAdjacentInteriors();
        ArrayList<InteriorNode> validAdj = new ArrayList<>();

        for (InteriorNode adj : adjInteriors) {
            LayerDescriptor layer1 = graph.resolveInteriorLayer(secondInterior.getUUID()).get();
            LayerDescriptor layer2 = graph.resolveInteriorLayer(adj.getUUID()).get();

            if (layer1.getNextLayerDescriptor().equals(layer2))
                validAdj.add(adj);
        }

        if (validAdj.size() != 1) return false;

        Vertex[] lowerInteriorAdjacents = validAdj.get(0).getAdjacentVertices().toArray(new Vertex[0]);

        if (lowerInteriorAdjacents.length < 2) return false;

        return graph.getEdgeBetweenNodes(lowerInteriorAdjacents[0], lowerInteriorAdjacents[1]).isPresent();
    }

    private ArrayList<Vertex> getAdjVertexesOf1stInterior(GraphModel graph, InteriorNode interior) {
        Set<InteriorNode> adjInteriors = interior.getAdjacentInteriors();
        ArrayList<InteriorNode> validAdj = new ArrayList<>();

        for (InteriorNode adj : adjInteriors) {
            LayerDescriptor layer1 = graph.resolveInteriorLayer(interior.getUUID()).get();
            LayerDescriptor layer2 = graph.resolveInteriorLayer(adj.getUUID()).get();

            if (layer1.getNextLayerDescriptor().equals(layer2))
                validAdj.add(adj);
        }

        Vertex[] firstInteriorAdjacents = validAdj.get(0).getAdjacentVertices().toArray(new Vertex[0]);
        Vertex[] secondInteriorAdjacents = validAdj.get(1).getAdjacentVertices().toArray(new Vertex[0]);

        ArrayList<Vertex> uncommonInterior1 = new ArrayList<>();
        ArrayList<Vertex> uncommonInterior2 = new ArrayList<>();
        ArrayList<Vertex> common = new ArrayList<>();

        for (Vertex v1 : firstInteriorAdjacents) {
            boolean comm = false;
            for (Vertex v2 : secondInteriorAdjacents) {
                if (v1.equals(v2)) {
                    common.add(v1);
                    comm = true;
                }
            }

            if (!comm) uncommonInterior1.add(v1);
        }

        for (Vertex v2 : secondInteriorAdjacents) {
            boolean comm = false;
            for (Vertex v3 : common) {
                if (v3.equals(v2)) {
                    comm = true;
                }
            }
            if (!comm) uncommonInterior2.add(v2);
        }

        ArrayList<Vertex> finalVerts = new ArrayList<>();

        for (Vertex v1 : uncommonInterior1) {
            for (Vertex v2 : uncommonInterior2) {
                for (Vertex vMiddle : common) {
                    if (vMiddle.getXCoordinate() == getCordsBetweenX(v1, v2) &&
                            vMiddle.getYCoordinate() == getCordsBetweenY(v1, v2) &&
                            graph.getEdgeBetweenNodes(v1, vMiddle).isPresent() &&
                            graph.getEdgeBetweenNodes(v2, vMiddle).isPresent()
                    ) {
                        finalVerts.add(v1);
                        finalVerts.add(vMiddle);
                        finalVerts.add(v2);
                    }
                }
            }
        }

        return finalVerts;
    }

    private ArrayList<Vertex> getAdjVertexesOf2ndInterior(GraphModel graph, InteriorNode interior) {
        Set<InteriorNode> adjInteriors = interior.getAdjacentInteriors();
        ArrayList<InteriorNode> validAdj = new ArrayList<>();

        for (InteriorNode adj : adjInteriors) {
            LayerDescriptor layer1 = graph.resolveInteriorLayer(interior.getUUID()).get();
            LayerDescriptor layer2 = graph.resolveInteriorLayer(adj.getUUID()).get();

            if (layer1.getNextLayerDescriptor().equals(layer2))
                validAdj.add(adj);
        }

        Vertex[] firstInteriorAdjacents = validAdj.get(0).getAdjacentVertices().toArray(new Vertex[0]);

        ArrayList<Vertex> adjVerticesOfLowerInterior = new ArrayList<>();
        Collections.addAll(adjVerticesOfLowerInterior, firstInteriorAdjacents);

        return adjVerticesOfLowerInterior;
    }
}
