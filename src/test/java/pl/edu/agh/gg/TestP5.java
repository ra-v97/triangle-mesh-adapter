package pl.edu.agh.gg;

import org.graphstream.graph.Edge;
import org.junit.Test;
import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphEdge;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;
import pl.edu.agh.gg.transformations.Transformation;
import pl.edu.agh.gg.transformations.TransformationP5;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestP5 {

    @Test
    public void testTransformation() {
        GraphModel graph = graphApplicableForP5();
        GraphModel graph2 = graphAfterP5();


        List<Transformation> transformations = Arrays.asList(new TransformationP5());

        for (int i = 0; i < 2; i ++) {
            InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
            for (InteriorNode interior : interiors) {
                for (Transformation t : transformations) {
                    if (t.isApplicable(graph, interior)) {
                        t.transform(graph, interior);
                    }
                }
            }
        }

        assertTrue("transformation has proper output and is applicable", areGraphsEqual(graph, graph2));
    }

    @Test
    public void testIsApplicable() {
        GraphModel graph = graphNotApplicableForP5();
        GraphModel graph2 = graphNotApplicableForP5();

        List<Transformation> transformations = Arrays.asList(new TransformationP5());

        for (int i = 0; i < 2; i ++) {
            InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
            for (InteriorNode interior : interiors) {
                for (Transformation t : transformations) {
                    if (t.isApplicable(graph, interior)) {
                        t.transform(graph, interior);
                    }
                }
            }
        }

        assertTrue("transformation is not applicable", areGraphsEqual(graph, graph2));

    }


    public boolean areGraphsEqual(GraphModel g1, GraphModel g2){
        Collection<Vertex> vl1 = g1.getVertices();
        Collection<Vertex> vl2 = g2.getVertices();

        Collection<InteriorNode> il1 = g1.getInteriors();
        Collection<InteriorNode> il2 = g2.getInteriors();


        Collection<GraphEdge> el1 = g1.getEdges();
        Collection<GraphEdge> el2 = g2.getEdges();

        for (Vertex v1: vl1){
            boolean found = false;
            for (Vertex v2: vl2){
                if (v1.equals(v2)){
                    found = true;
                    break;
                }
            }
            if(!found) {
                return false;
            }
        }

        System.out.println("v");
        for (InteriorNode i1: il1){
            boolean found = false;
            for (InteriorNode i2: il2){
                if (i1.equals(i2)){
                    found = true;
                    break;
                }
            }
            if(!found)
                return false;
        }
        System.out.println("i");


        for (GraphEdge e1: el1){
            boolean found = false;
            for (GraphEdge e2: el2){

                if (e1.equals(e2)){
                    found = true;
                    break;
                }
            }
            if(!found)
                return false;
        }
        System.out.println("e");

        return true;

    }


    private GraphModel graphAfterP5(){
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY()+ 0.5, stNoCo.getZ());
        Coordinates v23Co = new Coordinates(stNoCo.getX()+0.5, stNoCo.getY(), stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() +0.5, stNoCo.getY()+ 0.5, stNoCo.getZ());

        Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        Vertex v12 = graphModel.insertVertex("V12", v12Co, layerDescriptor).get();
        Vertex v23 = graphModel.insertVertex("V23", v23Co, layerDescriptor).get();
        Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v12, layerDescriptor);
        graphModel.insertEdge(v12, v2, layerDescriptor);
        graphModel.insertEdge(v2, v23, layerDescriptor);
        graphModel.insertEdge(v23, v3, layerDescriptor);
        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layerDescriptor);

        InteriorNode iLayer0 = graphModel.insertInterior("i", layerDescriptor, v1, v2, v3).get();

        LayerDescriptor layer1 = layerDescriptor.getNextLayerDescriptor();

        v1 = graphModel.insertVertex("V1", v1Co, layer1).get();
        v2 = graphModel.insertVertex("V2", v2Co, layer1).get();
        v3 = graphModel.insertVertex("V3", v3Co, layer1).get();
        v12 = graphModel.insertVertex("V12", v12Co, layer1).get();
        v23 = graphModel.insertVertex("V23", v23Co, layer1).get();
        v31 = graphModel.insertVertex("V31", v31Co, layer1).get();


        graphModel.insertEdge(v1, v12, layer1);
        graphModel.insertEdge(v12, v2, layer1);
        graphModel.insertEdge(v2, v23, layer1);
        graphModel.insertEdge(v23, v3, layer1);
        graphModel.insertEdge(v3, v31, layer1);
        graphModel.insertEdge(v31, v1, layer1);

        graphModel.insertEdge(v31, v12, layer1);
        graphModel.insertEdge(v23, v31, layer1);
        graphModel.insertEdge(v2, v31,  layer1);

        final InteriorNode i1 = graphModel.insertInterior("I", layer1, v1, v12, v31).get();
        final InteriorNode i2 = graphModel.insertInterior("I", layer1, v31, v12, v2).get();
        final InteriorNode i3 = graphModel.insertInterior("I", layer1, v2, v31, v23).get();
        final InteriorNode i4 = graphModel.insertInterior("I", layer1, v23, v31, v3).get();

        graphModel.insertEdge(i1, iLayer0);
        graphModel.insertEdge(i2, iLayer0);
        graphModel.insertEdge(i3, iLayer0);
        graphModel.insertEdge(i4, iLayer0);


        return graphModel;

    }



    private GraphModel graphApplicableForP5(){
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY()+ 0.5, stNoCo.getZ());
        Coordinates v23Co = new Coordinates(stNoCo.getX()+0.5, stNoCo.getY(), stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() +0.5, stNoCo.getY()+ 0.5, stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        final Vertex v12 = graphModel.insertVertex("V12", v12Co, layerDescriptor).get();
        final Vertex v23 = graphModel.insertVertex("V23", v23Co, layerDescriptor).get();
        final Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v12, layerDescriptor);
        graphModel.insertEdge(v12, v2, layerDescriptor);
        graphModel.insertEdge(v2, v23, layerDescriptor);
        graphModel.insertEdge(v23, v3, layerDescriptor);
        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }


    private GraphModel graphNotApplicableForP5(){
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX() - 0.5, stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() - 1, stNoCo.getZ());
        Coordinates v4Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        final Vertex v4 = graphModel.insertVertex("V4", v4Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v2, layerDescriptor);
        graphModel.insertEdge(v1, v4, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);
        graphModel.insertEdge(v3, v4, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }

}
