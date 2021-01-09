package transformations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.GraphEdge;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;
import pl.edu.agh.gg.transformations.Transformation;
import pl.edu.agh.gg.transformations.TransformationP4;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


public class TransformationP4Test {

    @Test
    public void testIfNotApplicableGraphDoesNotChange_LackingVerticesOnEdges() {
        GraphModel graph = createGraphNotApplicableForP4_LackingVerticesOnEdges();
        GraphModel graph2 = createGraphNotApplicableForP4_LackingVerticesOnEdges();

        List<Transformation> transformations = Collections.singletonList(new TransformationP4());

        InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
        for (InteriorNode interior : interiors) {
            for (Transformation t : transformations) {
                Assertions.assertFalse(t.isApplicable(graph, interior), "Transformation is applicable for graph with lacking vertices but should not be");
                if (t.isApplicable(graph, interior)) {
                    t.transform(graph, interior);
                }
            }
        }


        Assertions.assertTrue(areGraphsEqual(graph, graph2), "Transformation is not applicable but graphs are different");
    }

    @Test
    public void testIfNotApplicableGraphDoesNotChange_LackingEdgeBetweenV2AndV3() {
        GraphModel graph = createGraphNotApplicableForP4_LackingEdgeBetweenV2AndV3();
        GraphModel graph2 = createGraphNotApplicableForP4_LackingEdgeBetweenV2AndV3();

        List<Transformation> transformations = Collections.singletonList(new TransformationP4());

        InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
        for (InteriorNode interior : interiors) {
            for (Transformation t : transformations) {
                Assertions.assertFalse(t.isApplicable(graph, interior), "Transformation is applicable for graph with lacking vertices but should not be");
                if (t.isApplicable(graph, interior)) {
                    t.transform(graph, interior);
                }
            }
        }


        Assertions.assertTrue(areGraphsEqual(graph, graph2), "Transformation is not applicable but graphs are different");
    }

    @Test
    public void testIfNotApplicableGraphDoesNotChange_LackingVertexV3() {
        GraphModel graph = createGraphNotApplicableForP4_LackingVertexV3();
        GraphModel graph2 = createGraphNotApplicableForP4_LackingVertexV3();

        List<Transformation> transformations = Collections.singletonList(new TransformationP4());

        InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
        for (InteriorNode interior : interiors) {
            for (Transformation t : transformations) {
                Assertions.assertFalse(t.isApplicable(graph, interior), "Transformation is applicable for graph with lacking vertices but should not be");
                if (t.isApplicable(graph, interior)) {
                    t.transform(graph, interior);
                }
            }
        }


        Assertions.assertTrue(areGraphsEqual(graph, graph2), "Transformation is not applicable but graphs are different");
    }

    @Test
    public void testIfNotApplicableGraphDoesNotChange_LackingVertexV12() {
        GraphModel graph = createGraphNotApplicableForP4_LackingVertexV12();
        GraphModel graph2 = createGraphNotApplicableForP4_LackingVertexV12();

        List<Transformation> transformations = Collections.singletonList(new TransformationP4());

        InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
        for (InteriorNode interior : interiors) {
            for (Transformation t : transformations) {
                Assertions.assertFalse(t.isApplicable(graph, interior), "Transformation is applicable for graph with lacking vertices but should not be");
                if (t.isApplicable(graph, interior)) {
                    t.transform(graph, interior);
                }
            }
        }


        Assertions.assertTrue(areGraphsEqual(graph, graph2), "Transformation is not applicable but graphs are different");
    }

    @Test
    public void testIfNotApplicableGraphDoesNotChange_LackingVertexV31() {
        GraphModel graph = createGraphNotApplicableForP4_LackingVertexV31();
        GraphModel graph2 = createGraphNotApplicableForP4_LackingVertexV31();

        List<Transformation> transformations = Collections.singletonList(new TransformationP4());

        InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
        for (InteriorNode interior : interiors) {
            for (Transformation t : transformations) {
                Assertions.assertFalse(t.isApplicable(graph, interior), "Transformation is applicable for graph with lacking vertices but should not be");
                if (t.isApplicable(graph, interior)) {
                    t.transform(graph, interior);
                }
            }
        }


        Assertions.assertTrue(areGraphsEqual(graph, graph2), "Transformation is not applicable but graphs are different");
    }

    @Test
    public void testIfNotApplicableGraphDoesNotChange_LackingEdgeV31V3() {
        GraphModel graph = createGraphNotApplicableForP4_LackingEdgeV31V3();
        GraphModel graph2 = createGraphNotApplicableForP4_LackingEdgeV31V3();

        List<Transformation> transformations = Collections.singletonList(new TransformationP4());

        InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
        for (InteriorNode interior : interiors) {
            for (Transformation t : transformations) {
                Assertions.assertFalse(t.isApplicable(graph, interior), "Transformation is applicable for graph with lacking vertices but should not be");
                if (t.isApplicable(graph, interior)) {
                    t.transform(graph, interior);
                }
            }
        }


        Assertions.assertTrue(areGraphsEqual(graph, graph2), "Transformation is not applicable but graphs are different");
    }

    @Test
    public void testTransformationForApplicableGraph() {
        GraphModel graph = createGraphApplicableForP4();
        GraphModel graph2 = createGraphAfterP4();

        List<Transformation> transformations = Collections.singletonList(new TransformationP4());

        InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
        for (InteriorNode interior : interiors) {
            for (Transformation t : transformations) {
                Assertions.assertTrue(t.isApplicable(graph, interior), "Transformation for P4 graph is not applicable but should be");
                if (t.isApplicable(graph, interior)) {
                    t.transform(graph, interior);
                }
            }
        }


        Assertions.assertTrue(areGraphsEqual(graph, graph2), "Transformation for applicable graph gives other graph than expected");
    }

    @Test
    public void testTransformationForApplicableGraphRotated() {
        GraphModel graph = createGraphApplicableForP4Rotated();
        GraphModel graph2 = createGraphAfterP4ForP4RotatedGraph();

        List<Transformation> transformations = Collections.singletonList(new TransformationP4());

        InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
        for (InteriorNode interior : interiors) {
            for (Transformation t : transformations) {
                Assertions.assertTrue(t.isApplicable(graph, interior), "Transformation for P4 graph is not applicable but should be");
                if (t.isApplicable(graph, interior)) {
                    t.transform(graph, interior);
                }
            }
        }


        Assertions.assertTrue(areGraphsEqual(graph, graph2), "Transformation for applicable graph gives other graph than expected");
    }

    @Test
    public void testTransformationForApplicableGraphWithMoreVerticesAndEdges() {
        GraphModel graph = createGraphApplicableForP4WithMoreVerticesAndEdges();
        GraphModel graph2 = createGraphAfterP4WithMoreVerticesAndEdges();

        List<Transformation> transformations = Collections.singletonList(new TransformationP4());

        InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
        for (InteriorNode interior : interiors) {
            for (Transformation t : transformations) {
                Assertions.assertTrue(t.isApplicable(graph, interior), "Transformation for P4 graph is not applicable but should be");
                if (t.isApplicable(graph, interior)) {
                    t.transform(graph, interior);
                }
            }
        }


        Assertions.assertTrue(areGraphsEqual(graph, graph2), "Transformation for applicable graph gives other graph than expected");
    }

    @Test
    public void testTransformationForApplicableP5Graph() {
        GraphModel graph = createGraphApplicableForP5();
        GraphModel graph2 = createGraphAfterP4ForP5Graph();

        List<Transformation> transformations = Collections.singletonList(new TransformationP4());

        InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
        for (InteriorNode interior : interiors) {
            for (Transformation t : transformations) {
                Assertions.assertTrue(t.isApplicable(graph, interior), "Transformation for P5 graph is not applicable but should be");
                if (t.isApplicable(graph, interior)) {
                    t.transform(graph, interior);
                }
            }
        }


        Assertions.assertTrue(areGraphsEqual(graph, graph2), "transformation has proper output and is applicable");
    }


    public boolean areGraphsEqual(GraphModel g1, GraphModel g2) {
        Collection<Vertex> vl1 = g1.getVertices();
        Collection<Vertex> vl2 = g2.getVertices();

        Collection<InteriorNode> il1 = g1.getInteriors();
        Collection<InteriorNode> il2 = g2.getInteriors();


        Collection<GraphEdge> el1 = g1.getEdges();
        Collection<GraphEdge> el2 = g2.getEdges();

        for (Vertex v1 : vl1) {
            boolean found = false;
            for (Vertex v2 : vl2) {
                if (v1.equals(v2)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        System.out.println("v");
        for (InteriorNode i1 : il1) {
            boolean found = false;
            for (InteriorNode i2 : il2) {
                if (i1.equals(i2)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return false;
        }
        System.out.println("i");


        for (GraphEdge e1 : el1) {
            boolean found = false;
            for (GraphEdge e2 : el2) {

                if (e1.equals(e2)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return false;
        }
        System.out.println("e");

        return true;

    }


    private GraphModel createGraphApplicableForP4() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 0.5, stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY() + 0.5, stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        final Vertex v12 = graphModel.insertVertex("V12", v12Co, layerDescriptor).get();
        final Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v12, layerDescriptor);
        graphModel.insertEdge(v12, v2, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);
        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }

    private GraphModel createGraphAfterP4() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 0.5, stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY() + 0.5, stNoCo.getZ());

        Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        Vertex v12 = graphModel.insertVertex("V12", v12Co, layerDescriptor).get();
        Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v12, layerDescriptor);
        graphModel.insertEdge(v12, v2, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);
        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layerDescriptor);

        InteriorNode iLayer0 = graphModel.insertInterior("i", layerDescriptor, v1, v2, v3).get();

        LayerDescriptor layer1 = layerDescriptor.getNextLayerDescriptor();

        v1 = graphModel.insertVertex("V1", v1Co, layer1).get();
        v2 = graphModel.insertVertex("V2", v2Co, layer1).get();
        v3 = graphModel.insertVertex("V3", v3Co, layer1).get();
        v12 = graphModel.insertVertex("V12", v12Co, layer1).get();
        v31 = graphModel.insertVertex("V31", v31Co, layer1).get();


        graphModel.insertEdge(v1, v12, layer1);
        graphModel.insertEdge(v12, v2, layer1);
        graphModel.insertEdge(v2, v3, layer1);
        graphModel.insertEdge(v3, v31, layer1);
        graphModel.insertEdge(v31, v1, layer1);

        graphModel.insertEdge(v31, v12, layer1);
        graphModel.insertEdge(v2, v31, layer1);

        final InteriorNode i1 = graphModel.insertInterior("I", layer1, v1, v12, v31).get();
        final InteriorNode i2 = graphModel.insertInterior("I", layer1, v31, v12, v2).get();
        final InteriorNode i3 = graphModel.insertInterior("I", layer1, v2, v31, v3).get();

        graphModel.insertEdge(i1, iLayer0);
//        graphModel.insertEdge(i2, iLayer0);
        graphModel.insertEdge(i3, iLayer0);

        return graphModel;
    }


    private GraphModel createGraphApplicableForP4Rotated() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v23Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY(), stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY() + 0.5, stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        final Vertex v23 = graphModel.insertVertex("V23", v23Co, layerDescriptor).get();
        final Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v2, layerDescriptor);
        graphModel.insertEdge(v2, v23, layerDescriptor);
        graphModel.insertEdge(v23, v3, layerDescriptor);
        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }

    private GraphModel createGraphAfterP4ForP4RotatedGraph() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v23Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY(), stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY() + 0.5, stNoCo.getZ());

        Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        Vertex v23 = graphModel.insertVertex("V23", v23Co, layerDescriptor).get();
        Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v2, layerDescriptor);
        graphModel.insertEdge(v2, v23, layerDescriptor);
        graphModel.insertEdge(v23, v3, layerDescriptor);
        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layerDescriptor);

        InteriorNode iLayer0 = graphModel.insertInterior("i", layerDescriptor, v1, v2, v3).get();

        LayerDescriptor layer1 = layerDescriptor.getNextLayerDescriptor();

        v1 = graphModel.insertVertex("V1", v1Co, layer1).get();
        v2 = graphModel.insertVertex("V2", v2Co, layer1).get();
        v3 = graphModel.insertVertex("V3", v3Co, layer1).get();
        v23 = graphModel.insertVertex("V23", v23Co, layer1).get();
        v31 = graphModel.insertVertex("V31", v31Co, layer1).get();


        graphModel.insertEdge(v1, v2, layerDescriptor);
        graphModel.insertEdge(v2, v23, layerDescriptor);
        graphModel.insertEdge(v23, v3, layerDescriptor);
        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layer1);

        graphModel.insertEdge(v31, v23, layer1);
        graphModel.insertEdge(v2, v31, layer1);

        final InteriorNode i1 = graphModel.insertInterior("I", layer1, v1, v2, v31).get();
        final InteriorNode i2 = graphModel.insertInterior("I", layer1, v31, v23, v2).get();
        final InteriorNode i3 = graphModel.insertInterior("I", layer1, v23, v31, v3).get();

        graphModel.insertEdge(i1, iLayer0);
        graphModel.insertEdge(i3, iLayer0);

        return graphModel;

    }


    private GraphModel createGraphApplicableForP5() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 0.5, stNoCo.getZ());
        Coordinates v23Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY(), stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY() + 0.5, stNoCo.getZ());

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

    private GraphModel createGraphAfterP4ForP5Graph() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 0.5, stNoCo.getZ());
        Coordinates v23Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY(), stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY() + 0.5, stNoCo.getZ());

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
        v31 = graphModel.insertVertex("V31", v31Co, layer1).get();


        graphModel.insertEdge(v1, v12, layer1);
        graphModel.insertEdge(v12, v2, layer1);
        graphModel.insertEdge(v2, v3, layer1);
        graphModel.insertEdge(v3, v31, layer1);
        graphModel.insertEdge(v31, v1, layer1);

        graphModel.insertEdge(v31, v12, layer1);
        graphModel.insertEdge(v2, v31, layer1);

        final InteriorNode i1 = graphModel.insertInterior("I", layer1, v1, v12, v31).get();
        final InteriorNode i2 = graphModel.insertInterior("I", layer1, v31, v12, v2).get();
        final InteriorNode i3 = graphModel.insertInterior("I", layer1, v2, v31, v3).get();

        graphModel.insertEdge(i1, iLayer0);
        graphModel.insertEdge(i3, iLayer0);

        return graphModel;

    }


    private GraphModel createGraphApplicableForP4WithMoreVerticesAndEdges() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 0.5, stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY() + 0.5, stNoCo.getZ());
        Coordinates v4Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() + 1, stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        final Vertex v12 = graphModel.insertVertex("V12", v12Co, layerDescriptor).get();
        final Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();
        final Vertex v4 = graphModel.insertVertex("V4", v4Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v12, layerDescriptor);
        graphModel.insertEdge(v12, v2, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);
        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layerDescriptor);
        graphModel.insertEdge(v4, v3, layerDescriptor);
        graphModel.insertEdge(v4, v1, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }


    private GraphModel createGraphAfterP4WithMoreVerticesAndEdges() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 0.5, stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY() + 0.5, stNoCo.getZ());
        Coordinates v4Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() + 1, stNoCo.getZ());

        Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        Vertex v12 = graphModel.insertVertex("V12", v12Co, layerDescriptor).get();
        Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();
        Vertex v4 = graphModel.insertVertex("V4", v4Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v12, layerDescriptor);
        graphModel.insertEdge(v12, v2, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);
        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layerDescriptor);
        graphModel.insertEdge(v4, v3, layerDescriptor);
        graphModel.insertEdge(v4, v1, layerDescriptor);

        InteriorNode iLayer0 = graphModel.insertInterior("i", layerDescriptor, v1, v2, v3).get();

        LayerDescriptor layer1 = layerDescriptor.getNextLayerDescriptor();

        v1 = graphModel.insertVertex("V1", v1Co, layer1).get();
        v2 = graphModel.insertVertex("V2", v2Co, layer1).get();
        v3 = graphModel.insertVertex("V3", v3Co, layer1).get();
        v12 = graphModel.insertVertex("V12", v12Co, layer1).get();
        v31 = graphModel.insertVertex("V31", v31Co, layer1).get();


        graphModel.insertEdge(v1, v12, layer1);
        graphModel.insertEdge(v12, v2, layer1);
        graphModel.insertEdge(v2, v3, layer1);
        graphModel.insertEdge(v3, v31, layer1);
        graphModel.insertEdge(v31, v1, layer1);

        graphModel.insertEdge(v31, v12, layer1);
        graphModel.insertEdge(v2, v31, layer1);

        final InteriorNode i1 = graphModel.insertInterior("I", layer1, v1, v12, v31).get();
        final InteriorNode i2 = graphModel.insertInterior("I", layer1, v31, v12, v2).get();
        final InteriorNode i3 = graphModel.insertInterior("I", layer1, v2, v31, v3).get();

        graphModel.insertEdge(i1, iLayer0);
//        graphModel.insertEdge(i2, iLayer0);
        graphModel.insertEdge(i3, iLayer0);

        return graphModel;
    }


    private GraphModel createGraphNotApplicableForP4_LackingVerticesOnEdges() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX() - 0.5, stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() - 1, stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v2, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);
        graphModel.insertEdge(v3, v1, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }


    private GraphModel createGraphNotApplicableForP4_LackingEdgeBetweenV2AndV3() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 0.5, stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY() + 0.5, stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        final Vertex v12 = graphModel.insertVertex("V12", v12Co, layerDescriptor).get();
        final Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v12, layerDescriptor);
        graphModel.insertEdge(v12, v2, layerDescriptor);
        //graphModel.insertEdge(v2, v3, layerDescriptor);
        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }

    private GraphModel createGraphNotApplicableForP4_LackingVertexV3() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
//        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 0.5, stNoCo.getZ());
//        Coordinates v31Co = new Coordinates(stNoCo.getX() +0.5, stNoCo.getY()+ 0.5, stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
//        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        final Vertex v12 = graphModel.insertVertex("V12", v12Co, layerDescriptor).get();
//        final Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v12, layerDescriptor);
        graphModel.insertEdge(v12, v2, layerDescriptor);
//        graphModel.insertEdge(v2, v3, layerDescriptor);
//        graphModel.insertEdge(v3, v31, layerDescriptor);
//        graphModel.insertEdge(v31, v1, layerDescriptor);

//        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }

    private GraphModel createGraphNotApplicableForP4_LackingVertexV12() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
//        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY()+ 0.5, stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY() + 0.5, stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
//        final Vertex v12 = graphModel.insertVertex("V12", v12Co, layerDescriptor).get();
        final Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();

//        graphModel.insertEdge(v1, v12, layerDescriptor);
//        graphModel.insertEdge(v12, v2, layerDescriptor);
        graphModel.insertEdge(v1, v2, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);
        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }

    private GraphModel createGraphNotApplicableForP4_LackingVertexV31() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 0.5, stNoCo.getZ());
//        Coordinates v31Co = new Coordinates(stNoCo.getX() +0.5, stNoCo.getY()+ 0.5, stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        final Vertex v12 = graphModel.insertVertex("V12", v12Co, layerDescriptor).get();
//        final Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v12, layerDescriptor);
        graphModel.insertEdge(v12, v2, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);
//        graphModel.insertEdge(v3, v31, layerDescriptor);
//        graphModel.insertEdge(v31, v1, layerDescriptor);
        graphModel.insertEdge(v3, v1, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }

    private GraphModel createGraphNotApplicableForP4_LackingEdgeV31V3() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX(), stNoCo.getY(), stNoCo.getZ());
        Coordinates v3Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY(), stNoCo.getZ());
        Coordinates v12Co = new Coordinates(stNoCo.getX(), stNoCo.getY() + 0.5, stNoCo.getZ());
        Coordinates v31Co = new Coordinates(stNoCo.getX() + 0.5, stNoCo.getY() + 0.5, stNoCo.getZ());

        final Vertex v1 = graphModel.insertVertex("V1", v1Co, layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", v2Co, layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", v3Co, layerDescriptor).get();
        final Vertex v12 = graphModel.insertVertex("V12", v12Co, layerDescriptor).get();
        final Vertex v31 = graphModel.insertVertex("V31", v31Co, layerDescriptor).get();

        graphModel.insertEdge(v1, v12, layerDescriptor);
        graphModel.insertEdge(v12, v2, layerDescriptor);
        graphModel.insertEdge(v2, v3, layerDescriptor);
//        graphModel.insertEdge(v3, v31, layerDescriptor);
        graphModel.insertEdge(v31, v1, layerDescriptor);

        graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        return graphModel;
    }
}
