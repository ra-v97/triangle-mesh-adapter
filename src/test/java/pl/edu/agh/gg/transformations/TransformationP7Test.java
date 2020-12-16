package pl.edu.agh.gg.transformations;

import org.junit.Test;
import pl.edu.agh.gg.model.GraphEdge;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.model.Vertex;
import pl.edu.agh.gg.transformations.fixtures.TransformationP7Fixtures;

import java.util.Collection;

import static org.junit.Assert .*;

public class TransformationP7Test {

    @Test
    public void shouldApplyProductionWhenGivenGraphMeetsTheRequirements() {
        // given
        var graph = TransformationP7Fixtures.validGraphForProduction();
        var transformation = new TransformationP7();
        var interiors = graph.getInteriors().toArray(new InteriorNode[0]);

        // when
        var isApplicable = isApplicable(graph, transformation, interiors);

        // then
        assertTrue(isApplicable);
    }

    @Test
    public void shouldApplyProductionWhenGivenGraphIsSubgraphAndMeetsTheRequirements() {
        // given
        var graph = TransformationP7Fixtures.validGraphAsSubgraphForProduction();
        var transformation = new TransformationP7();
        var interiors = graph.getInteriors().toArray(new InteriorNode[0]);

        // when
        var isApplicable = isApplicable(graph, transformation, interiors);

        // then
        assertTrue(isApplicable);
    }

    @Test
    public void shouldNotBeApplicableToTwoTheSameInteriors() {
        // given
        var graph = TransformationP7Fixtures.validGraphForProduction();
        var transformation = new TransformationP7();
        var someInterior = graph.getInteriors().iterator().next();

        // when
        var isApplicable = transformation.isApplicable(graph, someInterior, someInterior);

        // then
        assertFalse(isApplicable);
    }

    @Test
    public void shouldNotBeApplicableWhenVertexIsMissing() {
        // given
        var graph = TransformationP7Fixtures.graphWithMissingVertex();
        var transformation = new TransformationP7();
        var interiors = graph.getInteriors().toArray(new InteriorNode[0]);

        // when
        var isApplicable = isApplicable(graph, transformation, interiors);

        // then
        assertFalse(isApplicable);
    }

    @Test
    public void shouldNotBeApplicableWhenEdgeIsMissing() {
        // given
        var graph = TransformationP7Fixtures.graphWithMissingEdge();
        var transformation = new TransformationP7();
        var interiors = graph.getInteriors().toArray(new InteriorNode[0]);

        // when
        var isApplicable = isApplicable(graph, transformation, interiors);

        // then
        assertFalse(isApplicable);
    }

    @Test
    public void shouldNotBeApplicableWhenCoordinatesAreInvalid() {
        // given
        var graph = TransformationP7Fixtures.graphWithInvalidCoordinates();
        var transformation = new TransformationP7();
        var interiors = graph.getInteriors().toArray(new InteriorNode[0]);

        // when
        var isApplicable = isApplicable(graph, transformation, interiors);

        // then
        assertFalse(isApplicable);
    }

    @Test
    public void shouldNotBeApplicableWhenInteriorIsChangedToVertex() {
        // given
        var graph = TransformationP7Fixtures.graphWithInvalidTag();
        var transformation = new TransformationP7();
        var interiors = graph.getInteriors().toArray(new InteriorNode[0]);

        // when
        var isApplicable = isApplicable(graph, transformation, interiors);

        // then
        assertFalse(isApplicable);
    }

    @Test
    public void shouldTransformGraphWhichIsPartOfSubgraph() {
        // given
        var graph = TransformationP7Fixtures.validGraphAsSubgraphForProduction();
        var transformation = new TransformationP7();

        // when
        var result = apply(graph, transformation);

        // then
        assertTrue(isEqual(TransformationP7Fixtures.transformedGraphAsSubgraph(), result));
    }

    @Test
    public void shouldTransformGraph() {
        // given
        var graph = TransformationP7Fixtures.validGraphForProduction();
        var transformation = new TransformationP7();

        // when
        var result = apply(graph, transformation);

        // then
        assertTrue(isEqual(TransformationP7Fixtures.validResult(), result));
    }

    static boolean isApplicable(GraphModel graph, DoubleInteriorTransformation t, InteriorNode[] interiors) {
        for (InteriorNode interior1 : interiors) {
            for (InteriorNode interior2 : interiors) {
                if (!interior1.equals(interior2) && t.isApplicable(graph, interior1, interior2)) {
                    return true;
                }
            }
        }
        return false;
    }

    static GraphModel apply(GraphModel graph, DoubleInteriorTransformation t) {
        InteriorNode[] interiors = graph.getInteriors().toArray(new InteriorNode[0]);
        for (InteriorNode interior1 : interiors) {
            for (InteriorNode interior2 : interiors) {
                if (!interior1.equals(interior2) && t.isApplicable(graph, interior1, interior2)) {
                    t.transform(graph, interior1, interior2);
                    return graph;
                }
            }
        }
        return null;
    }

    static boolean isEqual(GraphModel g1, GraphModel g2) {
        if (g2 == null) {
            return false;
        }
        Collection<Vertex> vl1 = g1.getVertices();
        Collection<Vertex> vl2 = g2.getVertices();
        Collection<InteriorNode> il1 = g1.getInteriors();
        Collection<InteriorNode> il2 = g2.getInteriors();
        Collection<GraphEdge> el1 = g1.getEdges();
        Collection<GraphEdge> el2 = g2.getEdges();

        for (Vertex v1 : vl1) {
            boolean found = false;
            for (Vertex v2 : vl2) {
                if (v1.getCoordinates().equals(v2.getCoordinates())) {
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
                if (i1.getCoordinates().equals(i2.getCoordinates())) {
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
                var e1e = e1.getEdgeNodes();
                var e2e = e2.getEdgeNodes();
                if (e1e.getValue0().getCoordinates().equals(e2e.getValue1().getCoordinates()) || (e1e.getValue0().getCoordinates().equals(e2e.getValue0().getCoordinates()))) {
                    if (e1e.getValue1().getCoordinates().equals(e2e.getValue1().getCoordinates()) || (e1e.getValue1().getCoordinates().equals(e2e.getValue0().getCoordinates()))) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found)
                return false;
        }
        System.out.println("e");

        return true;
    }
}