package pl.edu.agh.gg.transformations;

import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;

import java.util.ArrayList;
import java.util.Set;

public class TransformationP6 implements DoubleInteriorTransformation {

    private double getCordsBetweenX(Vertex v0, Vertex v1){
        return (v0.getXCoordinate() + v1.getXCoordinate())/2;
    }

    private double getCordsBetweenY(Vertex v0, Vertex v1){
        return (v0.getYCoordinate() + v1.getYCoordinate())/2;
    }



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
        if (commonAdjacents.size() != 2 || !graph.getEdgeBetweenNodes(commonAdjacents.get(0), commonAdjacents.get(1)).isPresent())
            return false;

        // Validate lower layer
        if (!isLowerApplicable(graph, firstInterior) || !isLowerApplicable(graph, secondInterior))
            return false;

        ArrayList<Vertex> first3 = getVertexes(graph, firstInterior);
        ArrayList<Vertex> second3 = getVertexes(graph, secondInterior);

        for (int i=0; i < 3; i++) {
            if (first3.get(i).equals(second3.get(i)))
                return false;
        }

        return true;
    }

    private boolean isLowerApplicable(GraphModel graph, InteriorNode interior) {
        Set<InteriorNode> adjInteriors = interior.getAdjacentInteriors();
        ArrayList<InteriorNode> validAdj = new ArrayList<>();

        for (InteriorNode adj : adjInteriors) {
            LayerDescriptor layer1 = graph.resolveInteriorLayer(interior.getUUID()).get();
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

        for(Vertex v1: uncommonInterior1) {
            for (Vertex v2: uncommonInterior2) {
                for (Vertex v3: common) {
                    if(v3.getXCoordinate() == getCordsBetweenX(v1, v2) &&
                            v3.getYCoordinate() == getCordsBetweenY(v1, v2) &&
                            graph.getEdgeBetweenNodes(v1, v3).isPresent() &&
                            graph.getEdgeBetweenNodes(v2, v3).isPresent()
                            ) return true;
                }
            }
        }

        return false;
    }

    private ArrayList<Vertex> getVertexes(GraphModel graph, InteriorNode interior) {
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

        for(Vertex v1: uncommonInterior1) {
            for (Vertex v2: uncommonInterior2) {
                for (Vertex vMiddle: common) {
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

        //TODO: Check x1 and x2!!
        return finalVerts;
    }

    @Override
    public void transform(GraphModel graph, InteriorNode firstInterior, InteriorNode secondInterior) {
        LayerDescriptor nextLayerDescriptor = graph.resolveInteriorLayer(firstInterior.getUUID()).get().getNextLayerDescriptor();

        Vertex[] firstInteriorAdjacents = firstInterior.getAdjacentVertices().toArray(new Vertex[0]);
        Vertex[] secondInteriorAdjacents = secondInterior.getAdjacentVertices().toArray(new Vertex[0]);
        ArrayList<Vertex> commonAdjacents = new ArrayList<>();

        LayerDescriptor layer1 = graph.resolveInteriorLayer(firstInterior.getUUID()).get();
        LayerDescriptor layer2 = graph.resolveInteriorLayer(secondInterior.getUUID()).get();


        for (Vertex v1 : firstInteriorAdjacents) {
            for (Vertex v2 : secondInteriorAdjacents) {
                if (v1.equals(v2)) commonAdjacents.add(v1);
            }
        }

        // Validate lower layer
        ArrayList<Vertex> verts1 = getVertexes(graph, firstInterior);
        ArrayList<Vertex> verts2 = getVertexes(graph, secondInterior);

        graph.removeEdge(verts2.get(0), verts2.get(1));
        graph.removeEdge(verts2.get(1), verts2.get(2));

        for(int i=0; i<3; i++) {
            ArrayList<Vertex> toChangeEdges = new ArrayList<>();
            ArrayList<InteriorNode> toChangeEdges1 = new ArrayList<>();


            // Edge Nodes

            for (Vertex v : graph.getVertices()) {
                if (graph.getEdgeBetweenNodes(verts2.get(i), v).isPresent()) {
                    toChangeEdges.add(v);
                }
            }

            for (Vertex v : toChangeEdges) {
                graph.insertEdge(verts1.get(i), v, nextLayerDescriptor);
                graph.removeEdge(verts2.get(i), v);
            }

            // Interiors

            for (InteriorNode n : graph.getInteriors()) {
                if (n.getAdjacentVertices().contains(verts2.get(i))) {
                    toChangeEdges1.add(n);
                }
            }

            for (InteriorNode n : toChangeEdges1) {
                graph.insertEdge(verts1.get(i), n, nextLayerDescriptor);
                n.addAdjecentVertex(verts1.get(i));
                graph.removeEdge(verts2.get(i), n);
                n.removeAdjacentVertex(verts2.get(i));
            }
        }

        for(Vertex vert : verts2) {
            graph.removeVertex(vert);
        }
    }
}