package pl.edu.agh.gg.transformations;

import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.GraphNode;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;

import java.util.*;

public class TransformationP13  implements DoubleInteriorTransformation{

    private double getCordsBetweenX(Vertex v0, Vertex v1){
        return (v0.getXCoordinate() + v1.getXCoordinate())/2;
    }

    private double getCordsBetweenY(Vertex v0, Vertex v1){
        return (v0.getYCoordinate() + v1.getYCoordinate())/2;
    }


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

            if (validAdj1.size() != 1) return false;

            // ------
            for (InteriorNode adj : adjInteriors2) {
                LayerDescriptor layer1 = graph.resolveInteriorLayer(secondInterior.getUUID()).get();
                LayerDescriptor layer2 = graph.resolveInteriorLayer(adj.getUUID()).get();

                if (layer1.getNextLayerDescriptor().equals(layer2))
                    validAdj2.add(adj);
            }

            if (validAdj2.size() != 1) return false;

            // -------
            InteriorNode in1  = validAdj1.get(0);
            InteriorNode in2  = validAdj2.get(0);

            Vertex[] vertexes1 = in1.getAdjacentVertices().toArray(new Vertex[0]);
            Vertex[] vertexes2 = in2.getAdjacentVertices().toArray(new Vertex[0]);

            Vertex isTheSame = null;
            for (Vertex v1 : vertexes1){
                for(Vertex v2: vertexes2){
                    if(v1.equalsIncludingId(v2)){
                        isTheSame = v1;
                    }
                }
            }
            if(isTheSame == null) return false;

            Vertex isWithCommonCords1 = null;
            for (Vertex v1 : vertexes1){
                for(Vertex v2: vertexes2){
                    if(v1.getCoordinates().equals(v2.getCoordinates()) && !v1.equalsIncludingId(v2)){
                        isWithCommonCords1 = v1;

                    }
                }
            }
            if(isWithCommonCords1 == null) return false;


            Vertex isWithCommonCords2 = null;
            for (Vertex v1 : vertexes1){
                for(Vertex v2: vertexes2){
                    if(v1.getCoordinates().equals(v2.getCoordinates()) && !v1.equalsIncludingId(v2)){
                        isWithCommonCords2 = v2;

                    }
                }
            }
            if(isWithCommonCords2 == null) return false;

            if(graph.getEdgeBetweenNodes(isWithCommonCords1, isTheSame).isEmpty()) return false;
            if(graph.getEdgeBetweenNodes(isWithCommonCords2, isTheSame).isEmpty()) return false;

            return true;
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

        var upperLayerValidator = new TransformationP13.UpperLayerValidator();
        if(!upperLayerValidator.isValid(commonAdjacents, graph))
            return false;


        // Validate lower layer
        var lowerLayerValidator = new TransformationP13.LowerLayerValidator();
        if (!lowerLayerValidator.isValid(graph, firstInterior, secondInterior))
            return false;


        return true;

    }


    @Override
    public void transform(GraphModel graph, InteriorNode firstInterior, InteriorNode secondInterior) {
        LayerDescriptor layer = graph.resolveInteriorLayer(firstInterior.getUUID()).get();

        InteriorNode[] firstInteriorLowerLayerL = firstInterior.getAdjacentInteriors().toArray(new InteriorNode[0]); // todo change to get by layer id
        InteriorNode firstInteriorLowerLayer = Arrays.stream(firstInteriorLowerLayerL).filter(x -> {
            LayerDescriptor layer1 = graph.resolveInteriorLayer(x.getUUID()).get();
            return layer1.equals(layer.getNextLayerDescriptor());
        }).findFirst().get();
        InteriorNode[] secondInteriorLowerLayerL = secondInterior.getAdjacentInteriors().toArray(new InteriorNode[0]);
        InteriorNode secondInteriorLowerLayer = Arrays.stream(secondInteriorLowerLayerL).filter(x -> {
            LayerDescriptor layer1 = graph.resolveInteriorLayer(x.getUUID()).get();
            return layer1.equals(layer.getNextLayerDescriptor());
        }).findFirst().get();

        Set<Vertex> firstInteriorLowerLayerVertexes = firstInteriorLowerLayer.getAdjacentVertices();
        Set<Vertex> secondInteriorLowerLayerVertexes = secondInteriorLowerLayer.getAdjacentVertices();

        Vertex vertexToRemoval = null;

        for (Vertex n1 : firstInteriorLowerLayerVertexes){
            for(Vertex n2 : secondInteriorLowerLayerVertexes){
                if(n1.getCoordinates().equals(n2.getCoordinates()) && !n1.equalsIncludingId(n2)){
                    vertexToRemoval = n1; // from first
                }
            }
        }

        Vertex vertexCommon = null;

        for (Vertex n1 : firstInteriorLowerLayerVertexes){
            for(Vertex n2 : secondInteriorLowerLayerVertexes){
                if(n1.equalsIncludingId(n2)){
                    vertexCommon = n2; // from second
                }
            }
        }


        Vertex finalVertexCommon = vertexCommon;
        Vertex finalVertexToRemoval = vertexToRemoval;
        Vertex vertexSaved = firstInteriorLowerLayerVertexes.stream()
                .filter(x -> !x.getCoordinates().equals(finalVertexCommon.getCoordinates()) && !x.getCoordinates().equals(finalVertexToRemoval.getCoordinates()))
                .findFirst().get();
        LayerDescriptor layer_ = graph.resolveInteriorLayer(firstInteriorLowerLayer.getUUID()).get();


        graph.removeEdge(vertexToRemoval, firstInteriorLowerLayer);
        firstInteriorLowerLayer.removeAdjacentVertex(vertexToRemoval);
        graph.removeVertex(vertexToRemoval); // removes Interior as well


//        Set<Vertex> vertexCommon = firstInteriorLowerLayer.getAdjacentVertices();

        Vertex vertexToConnect = null;

        for (Vertex n1 : firstInteriorLowerLayerVertexes){
            for(Vertex n2 : secondInteriorLowerLayerVertexes){
                if(n1.getCoordinates().equals(n2.getCoordinates()) && !n1.equalsIncludingId(n2)){
                    vertexToConnect = n2; // from second
                }
            }
        }




        firstInteriorLowerLayer.addAdjecentVertex(vertexToConnect);
        graph.insertEdge(vertexToConnect, firstInteriorLowerLayer, layer_);
        graph.insertEdge(vertexSaved, vertexToConnect, layer_);

        return;

    }
}
