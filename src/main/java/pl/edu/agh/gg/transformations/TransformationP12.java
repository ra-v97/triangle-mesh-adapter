package pl.edu.agh.gg.transformations;

import org.javatuples.Pair;
import org.javatuples.Tuple;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.GraphNode;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;

import java.util.*;

public class TransformationP12 implements DoubleInteriorTransformation {

    private double getCordsBetweenX(Vertex v0, Vertex v1){
        return (v0.getXCoordinate() + v1.getXCoordinate())/2;
    }

    private double getCordsBetweenY(Vertex v0, Vertex v1){
        return (v0.getYCoordinate() + v1.getYCoordinate())/2;
    }


    public class UpperLayerValidator {
        public boolean isValid(List<Vertex> commonAdjacency, GraphModel graph) {
            if (commonAdjacency.size() != 2
                    || graph.getEdgeBetweenNodes(commonAdjacency.get(0), commonAdjacency.get(1)).isEmpty())
                return false;

            return true;
        }
    }


    public class LowerLayerValidator {
        public boolean isValid(GraphModel graph, InteriorNode firstInterior, InteriorNode secondInterior) {
            Set<InteriorNode> adjInteriors1 = firstInterior.getAdjacentInteriors();
            ArrayList<InteriorNode> validAdj1 = new ArrayList<>();
            Set<InteriorNode> adjInteriors2 = secondInterior.getAdjacentInteriors();
            ArrayList<InteriorNode> validAdj2 = new ArrayList<>();


            for (InteriorNode adj : adjInteriors1) {
                LayerDescriptor layer1 = graph.resolveInteriorLayer(firstInterior.getUUID()).get();
                LayerDescriptor layer2 = graph.resolveInteriorLayer(adj.getUUID()).get();

                if (layer1.getNextLayerDescriptor().equals(layer2))
                    validAdj1.add(adj);
            }

            if (validAdj1.size() < 1) return false;

            // ------
            for (InteriorNode adj : adjInteriors2) {
                LayerDescriptor layer1 = graph.resolveInteriorLayer(secondInterior.getUUID()).get();
                LayerDescriptor layer2 = graph.resolveInteriorLayer(adj.getUUID()).get();

                if (layer1.getNextLayerDescriptor().equals(layer2))
                    validAdj2.add(adj);
            }

            if (validAdj2.size() < 1) return false;

            // -------
            for (InteriorNode in1 : validAdj1) {
                for (InteriorNode in2 : validAdj2) {
                    // mamy 2 interior node
                    Vertex[] vertexes1 = in1.getAdjacentVertices().toArray(new Vertex[0]);
                    Vertex[] vertexes2 = in2.getAdjacentVertices().toArray(new Vertex[0]);

                    ArrayList<Vertex> uncommonInterior1 = new ArrayList<>();
                    ArrayList<Vertex> uncommonInterior2 = new ArrayList<>();
                    ArrayList<Vertex> common = new ArrayList<>();

                    for (Vertex v1 : vertexes1) {
                        boolean comm = false;
                        for (Vertex v2 : vertexes2) {
                            if (v1.getUUID().equals(v2.getUUID())) {
                                common.add(v1);
                                comm = true;
                            }
                        }

                        if (!comm) uncommonInterior1.add(v1);
                    }

                    for (Vertex v2 : vertexes2) {
                        boolean comm = false;
                        for (Vertex v1 : common) {
                            if (v1.getUUID().equals(v2.getUUID())) {
                                comm = true;
                            }
                        }

                        if (!comm) uncommonInterior2.add(v2);
                    }

                    ArrayList<Pair> pairs1 = new ArrayList<>();
                    ArrayList<Pair> pairs2 = new ArrayList<>();

                    for (Vertex v1 : uncommonInterior1) {
                        for (Vertex v2 : uncommonInterior1) {
                            if (!v1.equals(v2) && graph.getEdgeBetweenNodes(v1, v2).isPresent()) {
                                Pair<Vertex, Vertex> pair1 = Pair.with(v1, v2);
                                pairs1.add(pair1);
                            }
                        }
                    }

                    for (Vertex v1 : uncommonInterior2) {
                        for (Vertex v2 : uncommonInterior2) {
                            if (!v1.equals(v2) && graph.getEdgeBetweenNodes(v1, v2).isPresent()) {
                                Pair<Vertex, Vertex> pair2 = Pair.with(v1, v2);
                                pairs2.add(pair2);
                            }
                        }
                    }

                    for (Pair<Vertex, Vertex> p1 : pairs1) {
                        for (Pair<Vertex, Vertex> p2 : pairs2) {
                            Vertex v11 = p1.getValue0();
                            Vertex v12 = p1.getValue1();

                            Vertex v21 = p2.getValue0();
                            Vertex v22 = p2.getValue1();

                            if (v11.getXCoordinate() == v21.getXCoordinate() &&
                                    v11.getYCoordinate() == v21.getYCoordinate() &&
                                    v12.getXCoordinate() == v22.getXCoordinate() &&
                                    v12.getYCoordinate() == v22.getYCoordinate()) {
                                return true;
                            }

                            if (v11.getXCoordinate() == v22.getXCoordinate() &&
                                    v11.getYCoordinate() == v22.getYCoordinate() &&
                                    v12.getXCoordinate() == v21.getXCoordinate() &&
                                    v12.getYCoordinate() == v21.getYCoordinate()) {
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }
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

        var upperLayerValidator = new TransformationP12.UpperLayerValidator();
        if(!upperLayerValidator.isValid(commonAdjacents, graph))
            return false;


        // Validate lower layer
        var lowerLayerValidator = new TransformationP12.LowerLayerValidator();
        if (!lowerLayerValidator.isValid(graph, firstInterior, secondInterior))
            return false;


        return true;

    }


    @Override
    public void transform(GraphModel graph, InteriorNode firstInterior, InteriorNode secondInterior) {
        Set<InteriorNode> adjInteriors1 = firstInterior.getAdjacentInteriors();
        ArrayList<InteriorNode> validAdj1 = new ArrayList<>();
        Set<InteriorNode> adjInteriors2 = secondInterior.getAdjacentInteriors();
        ArrayList<InteriorNode> validAdj2 = new ArrayList<>();

        for (InteriorNode adj : adjInteriors1) {
            LayerDescriptor layer1 = graph.resolveInteriorLayer(firstInterior.getUUID()).get();
            LayerDescriptor layer2 = graph.resolveInteriorLayer(adj.getUUID()).get();

            if (layer1.getNextLayerDescriptor().equals(layer2))
                validAdj1.add(adj);
        }

        // ------
        for (InteriorNode adj : adjInteriors2) {
            LayerDescriptor layer1 = graph.resolveInteriorLayer(secondInterior.getUUID()).get();
            LayerDescriptor layer2 = graph.resolveInteriorLayer(adj.getUUID()).get();

            if (layer1.getNextLayerDescriptor().equals(layer2))
                validAdj2.add(adj);
        }

        for (InteriorNode in1 : validAdj1) {
            for (InteriorNode in2 : validAdj2) {
                Vertex[] vertexes1 = in1.getAdjacentVertices().toArray(new Vertex[0]);
                Vertex[] vertexes2 = in2.getAdjacentVertices().toArray(new Vertex[0]);

                ArrayList<Vertex> uncommonInterior1 = new ArrayList<>();
                ArrayList<Vertex> uncommonInterior2 = new ArrayList<>();
                ArrayList<Vertex> common = new ArrayList<>();

                for (Vertex v1 : vertexes1) {
                    boolean comm = false;
                    for (Vertex v2 : vertexes2) {
                        if (v1.getUUID().equals(v2.getUUID())) {
                            common.add(v1);
                            comm = true;
                        }
                    }

                    if (!comm) uncommonInterior1.add(v1);
                }

                for (Vertex v2 : vertexes2) {
                    boolean comm = false;
                    for (Vertex v1 : common) {
                        if (v1.getUUID().equals(v2.getUUID())) {
                            comm = true;
                        }
                    }

                    if (!comm) uncommonInterior2.add(v2);
                }

                ArrayList<Pair> pairs1 = new ArrayList<>();
                ArrayList<Pair> pairs2 = new ArrayList<>();

                for (Vertex v1 : uncommonInterior1) {
                    for (Vertex v2 : uncommonInterior1) {
                        if (!v1.equals(v2) && graph.getEdgeBetweenNodes(v1, v2).isPresent()) {
                            Pair<Vertex, Vertex> pair1 = Pair.with(v1, v2);
                            pairs1.add(pair1);
                        }
                    }
                }

                for (Vertex v1 : uncommonInterior2) {
                    for (Vertex v2 : uncommonInterior2) {
                        if (!v1.equals(v2) && graph.getEdgeBetweenNodes(v1, v2).isPresent()) {
                            Pair<Vertex, Vertex> pair2 = Pair.with(v1, v2);
                            pairs2.add(pair2);
                        }
                    }
                }

                // mamy dwie listy par polaczonych krawedziam i trzeba sprawdzic czy sie zgadzaja

                boolean hasFoundPair = false;
                Pair<Vertex, Vertex> pair1 = null;
                Pair<Vertex, Vertex> pair2 = null;

                for (Pair<Vertex, Vertex> p1 : pairs1) {
                    for (Pair<Vertex, Vertex> p2 : pairs2) {
                        Vertex v11 = p1.getValue0();
                        Vertex v12 = p1.getValue1();

                        Vertex v21 = p2.getValue0();
                        Vertex v22 = p2.getValue1();

                        if (!hasFoundPair && v11.getXCoordinate() == v21.getXCoordinate() &&
                                v11.getYCoordinate() == v21.getYCoordinate() &&
                                v12.getXCoordinate() == v22.getXCoordinate() &&
                                v12.getYCoordinate() == v22.getYCoordinate()) {
                            pair1 = Pair.with(v11, v12);
                            pair2 = Pair.with(v21, v22);
                            hasFoundPair = true;
                        }

                        if (!hasFoundPair && v11.getXCoordinate() == v22.getXCoordinate() &&
                                v11.getYCoordinate() == v22.getYCoordinate() &&
                                v12.getXCoordinate() == v21.getXCoordinate() &&
                                v12.getYCoordinate() == v21.getYCoordinate()) {
                            pair1 = Pair.with(v11, v12);
                            pair2 = Pair.with(v22, v21);
                            hasFoundPair = true;
                        }
                    }
                }

                if(hasFoundPair) {
                    LayerDescriptor layer_ = graph.resolveInteriorLayer(in1.getUUID()).get();

                    graph.removeEdge(pair2.getValue0(), pair2.getValue1());

                    ArrayList<Vertex> toChangeEdges1 = new ArrayList<>();
                    ArrayList<Vertex> toChangeEdges2 = new ArrayList<>();
                    ArrayList<InteriorNode> toChangeEdgesI1 = new ArrayList<>();
                    ArrayList<InteriorNode> toChangeEdgesI2 = new ArrayList<>();


                    // Edge Nodes

                    for (Vertex v : graph.getVertices()) {
                        if (graph.getEdgeBetweenNodes(pair2.getValue0(), v).isPresent()) {
                            toChangeEdges1.add(v);
                        }
                    }

                    for (Vertex v : graph.getVertices()) {
                        if (graph.getEdgeBetweenNodes(pair2.getValue1(), v).isPresent()) {
                            toChangeEdges2.add(v);
                        }
                    }

                    for (Vertex v : toChangeEdges1) {
                        graph.insertEdge(pair1.getValue0(), v, layer_);
                        graph.removeEdge(pair2.getValue0(), v);
                    }

                    for (Vertex v : toChangeEdges2) {
                        graph.insertEdge(pair1.getValue1(), v, layer_);
                        graph.removeEdge(pair2.getValue1(), v);
                    }

                    // Interiors

                    for (InteriorNode n : graph.getInteriors()) {
                        if (n.getAdjacentVertices().contains(pair2.getValue0())) {
                            toChangeEdgesI1.add(n);
                        }
                    }

                    for (InteriorNode n : graph.getInteriors()) {
                        if (n.getAdjacentVertices().contains(pair2.getValue1())) {
                            toChangeEdgesI2.add(n);
                        }
                    }

                    for (InteriorNode n : toChangeEdgesI1) {
                        graph.insertEdge(pair1.getValue0(), n, layer_);
                        n.addAdjecentVertex(pair1.getValue0());
                        graph.removeEdge(pair2.getValue0(), n);
                        n.removeAdjacentVertex(pair2.getValue0());
                    }

                    for (InteriorNode n : toChangeEdgesI2) {
                        graph.insertEdge(pair1.getValue1(), n, layer_);
                        n.addAdjecentVertex(pair1.getValue1());
                        graph.removeEdge(pair2.getValue1(), n);
                        n.removeAdjacentVertex(pair2.getValue1());
                    }

                    graph.removeVertex(pair2.getValue0());
                    graph.removeVertex(pair2.getValue1());

                    return;
                }
            }
        }
    }
}
