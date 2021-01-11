package pl.edu.agh.gg.app;

import pl.edu.agh.gg.common.Coordinates;
import pl.edu.agh.gg.common.LayerDescriptor;
import pl.edu.agh.gg.model.*;
import pl.edu.agh.gg.transformations.*;
import pl.edu.agh.gg.visualization.Visualizer;

import java.util.*;
import java.util.stream.Stream;


public class MainExE {
    public static void main(String[] args) {
        GraphModel graph = new GraphModel();
        final Visualizer visualizer = new Visualizer(graph);
        //layer 0
        LayerDescriptor layer0 = new LayerDescriptor(0);
        StartingNode startingNode = graph.insertStartingInterior("S", layer0, new Coordinates(50, 50, 0));

        applyTransformationIfPossible(new TransformationP1(), graph, startingNode);
//        visualizer.visualize(new LayerDescriptor(1));

        InteriorNode[] p1Interiors = getConnectedInteriorNodes(startingNode);
        InteriorNode p1Interior1 = p1Interiors[0];
        InteriorNode p1Interior2 = p1Interiors[1];
        applyTransformationIfPossible(new TransformationP2(), graph, p1Interior1);
        applyTransformationIfPossible(new TransformationP2(), graph, p1Interior2);
//        visualizer.visualize(new LayerDescriptor(2));

        applyTwoInteriorsTransformationIfPossible(new TransformationP6(), graph, p1Interior1, p1Interior2);
//        visualizer.visualize(new LayerDescriptor(2));

        InteriorNode[] p1_p2_p6interiors1 = getConnectedInteriorNodes(p1Interior1);
        InteriorNode p1_p2_p6interior1 = p1_p2_p6interiors1[1];
        InteriorNode p1_p2_p6interior2 = p1_p2_p6interiors1[2];
        InteriorNode[] p1_p2_p6interiors2 = getConnectedInteriorNodes(p1Interior2);
        InteriorNode p1_p2_p6interior3 = p1_p2_p6interiors2[1];
        InteriorNode p1_p2_p6interior4 = p1_p2_p6interiors2[2];
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6interior1);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6interior2);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6interior3);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6interior4);
//        visualizer.visualize(new LayerDescriptor(3));

        InteriorNode[] p1_p2_p6_p2interiors1 = getConnectedInteriorNodes(p1_p2_p6interior1);
        InteriorNode[] p1_p2_p6_p2interiors2 = getConnectedInteriorNodes(p1_p2_p6interior2);
        InteriorNode[] p1_p2_p6_p2interiors3 = getConnectedInteriorNodes(p1_p2_p6interior3);
        InteriorNode[] p1_p2_p6_p2interiors4 = getConnectedInteriorNodes(p1_p2_p6interior4);
        InteriorNode p1_p2_p6_p2interior1 = p1_p2_p6_p2interiors1[1];
        p1_p2_p6_p2interior1.setLabel("A");
        InteriorNode p1_p2_p6_p2interior2 = p1_p2_p6_p2interiors1[2];
        p1_p2_p6_p2interior2.setLabel("B");
        InteriorNode p1_p2_p6_p2interior3 = p1_p2_p6_p2interiors2[1];
        p1_p2_p6_p2interior3.setLabel("C");
        InteriorNode p1_p2_p6_p2interior4 = p1_p2_p6_p2interiors2[2];
        p1_p2_p6_p2interior4.setLabel("D");
        InteriorNode p1_p2_p6_p2interior5 = p1_p2_p6_p2interiors3[1];
        p1_p2_p6_p2interior5.setLabel("E");
        InteriorNode p1_p2_p6_p2interior6 = p1_p2_p6_p2interiors3[2];
        p1_p2_p6_p2interior6.setLabel("F");
        InteriorNode p1_p2_p6_p2interior7 = p1_p2_p6_p2interiors4[1];
        p1_p2_p6_p2interior7.setLabel("G");
        InteriorNode p1_p2_p6_p2interior8 = p1_p2_p6_p2interiors4[2];
        p1_p2_p6_p2interior8.setLabel("H");
//        visualizer.visualize(new LayerDescriptor(3));

//        Collection<InteriorNode> interiors = graph.getInteriors();
//        for (InteriorNode interior1 : interiors) {
//            for (InteriorNode interior2 : interiors) {
//                TransformationP12 transformationP12 = new TransformationP12();
//                if (transformationP12.isApplicable(graph, interior1, interior2)) {
//                    transformationP12.transform(graph, interior1, interior2);
//                    System.out.println("hura");
//                }
//            }
//        }

        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6interior1, p1_p2_p6interior3);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6interior2, p1_p2_p6interior4);
//        visualizer.visualize(new LayerDescriptor(3));

        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6interior1, p1_p2_p6interior2);
        applyTwoInteriorsTransformationIfPossible(new TransformationP13(), graph, p1_p2_p6interior3, p1_p2_p6interior4);
//        visualizer.visualize(new LayerDescriptor(3));

        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2interior1);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2interior6);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2interior5);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2interior7);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2interior1, p1_p2_p6_p2interior6);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2interior5, p1_p2_p6_p2interior7);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2interior5, p1_p2_p6_p2interior6);
//        visualizer.visualize(new LayerDescriptor(4));

        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2interior2);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2interior3);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2interior4);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2interior8);
        applyTwoInteriorsTransformationIfPossible(new TransformationP6(), graph, p1_p2_p6_p2interior2, p1_p2_p6_p2interior3);
        applyTwoInteriorsTransformationIfPossible(new TransformationP6(), graph, p1_p2_p6_p2interior4, p1_p2_p6_p2interior8);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2interior3, p1_p2_p6_p2interior4);
//        visualizer.visualize(new LayerDescriptor(4));

        InteriorNode p1_p2_p6_p2_p9interior1 = getConnectedInteriorNodes(p1_p2_p6_p2interior1)[1];
        p1_p2_p6_p2_p9interior1.setLabel("A");
        InteriorNode p1_p2_p6_p2_p9interior2 = getConnectedInteriorNodes(p1_p2_p6_p2interior6)[1];
        p1_p2_p6_p2_p9interior2.setLabel("B");
        InteriorNode p1_p2_p6_p2_p9interior3 = getConnectedInteriorNodes(p1_p2_p6_p2interior5)[1];
        p1_p2_p6_p2_p9interior3.setLabel("C");
        InteriorNode p1_p2_p6_p2_p9interior4 = getConnectedInteriorNodes(p1_p2_p6_p2interior7)[1];
        p1_p2_p6_p2_p9interior4.setLabel("D");

        InteriorNode p1_p2_p6_p2_p2interior1 = getConnectedInteriorNodes(p1_p2_p6_p2interior2)[1];
        p1_p2_p6_p2_p2interior1.setLabel("E");
        InteriorNode p1_p2_p6_p2_p2interior2 = getConnectedInteriorNodes(p1_p2_p6_p2interior2)[2];
        p1_p2_p6_p2_p2interior2.setLabel("F");
        InteriorNode p1_p2_p6_p2_p2interior3 = getConnectedInteriorNodes(p1_p2_p6_p2interior3)[1];
        p1_p2_p6_p2_p2interior3.setLabel("G");
        InteriorNode p1_p2_p6_p2_p2interior4 = getConnectedInteriorNodes(p1_p2_p6_p2interior3)[2];
        p1_p2_p6_p2_p2interior4.setLabel("H");
        InteriorNode p1_p2_p6_p2_p2interior5 = getConnectedInteriorNodes(p1_p2_p6_p2interior4)[1];
        p1_p2_p6_p2_p2interior5.setLabel("I");
        InteriorNode p1_p2_p6_p2_p2interior6 = getConnectedInteriorNodes(p1_p2_p6_p2interior4)[2];
        p1_p2_p6_p2_p2interior6.setLabel("J");
        InteriorNode p1_p2_p6_p2_p2interior7 = getConnectedInteriorNodes(p1_p2_p6_p2interior8)[1];
        p1_p2_p6_p2_p2interior7.setLabel("K");
        InteriorNode p1_p2_p6_p2_p2interior8 = getConnectedInteriorNodes(p1_p2_p6_p2interior8)[2];
        p1_p2_p6_p2_p2interior8.setLabel("L");
//        visualizer.visualize(new LayerDescriptor(4));

        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2interior1, p1_p2_p6_p2interior2);
        applyTwoInteriorsTransformationIfPossible(new TransformationP13(), graph, p1_p2_p6_p2interior7, p1_p2_p6_p2interior8);
        visualizer.visualize(new LayerDescriptor(4));

        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p9interior1);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p9interior2);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p9interior3);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p9interior4);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2interior2);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2interior8);

        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p9interior1, p1_p2_p6_p2_p9interior2);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p9interior3, p1_p2_p6_p2_p9interior4);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p9interior4, p1_p2_p6_p2_p2interior8);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p9interior1, p1_p2_p6_p2_p2interior2);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p9interior2, p1_p2_p6_p2_p9interior3);

        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2interior1);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2interior3);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2interior4);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2interior5);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2interior6);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2interior7);

        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2interior1, p1_p2_p6_p2_p2interior3);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2interior3, p1_p2_p6_p2_p2interior4);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2interior6, p1_p2_p6_p2_p2interior5);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2interior5, p1_p2_p6_p2_p2interior7);
        applyTwoInteriorsTransformationIfPossible(new TransformationP6(), graph, p1_p2_p6_p2_p2interior6, p1_p2_p6_p2_p2interior4);

        InteriorNode p1_p2_p6_p2_p9_p9interior1 = getConnectedInteriorNodes(p1_p2_p6_p2_p9interior1)[1];
        p1_p2_p6_p2_p9_p9interior1.setLabel("A");
        InteriorNode p1_p2_p6_p2_p9_p9interior2 = getConnectedInteriorNodes(p1_p2_p6_p2_p9interior2)[1];
        p1_p2_p6_p2_p9_p9interior2.setLabel("B");
        InteriorNode p1_p2_p6_p2_p9_p9interior3 = getConnectedInteriorNodes(p1_p2_p6_p2_p9interior3)[1];
        p1_p2_p6_p2_p9_p9interior3.setLabel("C");
        InteriorNode p1_p2_p6_p2_p9_p9interior4 = getConnectedInteriorNodes(p1_p2_p6_p2_p9interior4)[1];
        p1_p2_p6_p2_p9_p9interior4.setLabel("D");
        InteriorNode p1_p2_p6_p2_p2_p9interior1 = getConnectedInteriorNodes(p1_p2_p6_p2_p2interior2)[1];
        p1_p2_p6_p2_p2_p9interior1.setLabel("E");
        InteriorNode p1_p2_p6_p2_p2_p9interior2 = getConnectedInteriorNodes(p1_p2_p6_p2_p2interior8)[1];
        p1_p2_p6_p2_p2_p9interior2.setLabel("F");

        InteriorNode p1_p2_p6_p2_p2_p2interior1 = getConnectedInteriorNodes(p1_p2_p6_p2_p2interior1)[1];
        p1_p2_p6_p2_p2_p2interior1.setLabel("G");
        InteriorNode p1_p2_p6_p2_p2_p2interior2 = getConnectedInteriorNodes(p1_p2_p6_p2_p2interior1)[2];
        p1_p2_p6_p2_p2_p2interior2.setLabel("H");
        InteriorNode p1_p2_p6_p2_p2_p2interior3 = getConnectedInteriorNodes(p1_p2_p6_p2_p2interior3)[1];
        p1_p2_p6_p2_p2_p2interior3.setLabel("I");
        InteriorNode p1_p2_p6_p2_p2_p2interior4 = getConnectedInteriorNodes(p1_p2_p6_p2_p2interior3)[2];
        p1_p2_p6_p2_p2_p2interior4.setLabel("J");
        InteriorNode p1_p2_p6_p2_p2_p2interior5 = getConnectedInteriorNodes(p1_p2_p6_p2_p2interior4)[1];
        p1_p2_p6_p2_p2_p2interior5.setLabel("K");
        InteriorNode p1_p2_p6_p2_p2_p2interior6 = getConnectedInteriorNodes(p1_p2_p6_p2_p2interior4)[2];
        p1_p2_p6_p2_p2_p2interior6.setLabel("L");
        InteriorNode p1_p2_p6_p2_p2_p2interior7 = getConnectedInteriorNodes(p1_p2_p6_p2_p2interior5)[1];
        p1_p2_p6_p2_p2_p2interior7.setLabel("M");
        InteriorNode p1_p2_p6_p2_p2_p2interior8 = getConnectedInteriorNodes(p1_p2_p6_p2_p2interior5)[2];
        p1_p2_p6_p2_p2_p2interior8.setLabel("N");
        InteriorNode p1_p2_p6_p2_p2_p2interior9 = getConnectedInteriorNodes(p1_p2_p6_p2_p2interior6)[1];
        p1_p2_p6_p2_p2_p2interior9.setLabel("O");
        InteriorNode p1_p2_p6_p2_p2_p2interior10 = getConnectedInteriorNodes(p1_p2_p6_p2_p2interior6)[2];
        p1_p2_p6_p2_p2_p2interior10.setLabel("P");
        InteriorNode p1_p2_p6_p2_p2_p2interior11 = getConnectedInteriorNodes(p1_p2_p6_p2_p2interior7)[1];
        p1_p2_p6_p2_p2_p2interior11.setLabel("R");
        InteriorNode p1_p2_p6_p2_p2_p2interior12 = getConnectedInteriorNodes(p1_p2_p6_p2_p2interior7)[2];
        p1_p2_p6_p2_p2_p2interior12.setLabel("S");
        visualizer.visualize(new LayerDescriptor(5));

        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2interior2, p1_p2_p6_p2_p2interior4);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2interior8, p1_p2_p6_p2_p2interior7);
        applyTwoInteriorsTransformationIfPossible(new TransformationP13(), graph, p1_p2_p6_p2_p2interior2, p1_p2_p6_p2_p2interior1);
        visualizer.visualize(new LayerDescriptor(5));

        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2_p2interior1);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2_p2interior4);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2_p2interior3);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2_p2interior6);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2_p2interior10);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2_p2interior7);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2_p2interior8);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2_p2interior12);

        applyTwoInteriorsTransformationIfPossible(new TransformationP6(), graph, p1_p2_p6_p2_p2_p2interior1, p1_p2_p6_p2_p2_p2interior4);
        applyTwoInteriorsTransformationIfPossible(new TransformationP6(), graph, p1_p2_p6_p2_p2_p2interior3, p1_p2_p6_p2_p2_p2interior6);
        applyTwoInteriorsTransformationIfPossible(new TransformationP6(), graph, p1_p2_p6_p2_p2_p2interior10, p1_p2_p6_p2_p2_p2interior7);
        applyTwoInteriorsTransformationIfPossible(new TransformationP6(), graph, p1_p2_p6_p2_p2_p2interior8, p1_p2_p6_p2_p2_p2interior12);

        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2interior4, p1_p2_p6_p2_p2_p2interior3);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2interior6, p1_p2_p6_p2_p2_p2interior10);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2interior7, p1_p2_p6_p2_p2_p2interior8);

        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p9_p9interior1);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p9_p9interior2);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p9_p9interior3);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p9_p9interior4);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p9interior1);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p9interior2);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2interior2);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2interior5);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2interior9);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2interior11);

        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p9_p9interior1, p1_p2_p6_p2_p9_p9interior2);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p9_p9interior2, p1_p2_p6_p2_p9_p9interior3);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p9_p9interior3, p1_p2_p6_p2_p9_p9interior4);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2interior2, p1_p2_p6_p2_p2_p9interior1);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p9interior2, p1_p2_p6_p2_p2_p2interior11);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p9interior1, p1_p2_p6_p2_p2_p2interior5);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p9interior2, p1_p2_p6_p2_p2_p2interior9);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2interior5, p1_p2_p6_p2_p2_p2interior9);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p9_p9interior1, p1_p2_p6_p2_p2_p9interior1);
        applyTwoInteriorsTransformationIfPossible(new TransformationP13(), graph, p1_p2_p6_p2_p9_p9interior4, p1_p2_p6_p2_p2_p9interior2);

        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2interior2, p1_p2_p6_p2_p2_p2interior1);
        applyTwoInteriorsTransformationIfPossible(new TransformationP13(), graph, p1_p2_p6_p2_p2_p2interior5, p1_p2_p6_p2_p2_p2interior6);
        applyTwoInteriorsTransformationIfPossible(new TransformationP13(), graph, p1_p2_p6_p2_p2_p2interior9, p1_p2_p6_p2_p2_p2interior10);
        applyTwoInteriorsTransformationIfPossible(new TransformationP13(), graph, p1_p2_p6_p2_p2_p2interior11, p1_p2_p6_p2_p2_p2interior12);


        InteriorNode p1_p2_p6_p2_p9_p9_p9interior1 = getConnectedInteriorNodes(p1_p2_p6_p2_p9_p9interior1)[1];
        p1_p2_p6_p2_p9_p9_p9interior1.setLabel("A");
        InteriorNode p1_p2_p6_p2_p9_p9_p9interior2 = getConnectedInteriorNodes(p1_p2_p6_p2_p9_p9interior2)[1];
        p1_p2_p6_p2_p9_p9_p9interior2.setLabel("B");
        InteriorNode p1_p2_p6_p2_p9_p9_p9interior3 = getConnectedInteriorNodes(p1_p2_p6_p2_p9_p9interior3)[1];
        p1_p2_p6_p2_p9_p9_p9interior3.setLabel("C");
        InteriorNode p1_p2_p6_p2_p9_p9_p9interior4 = getConnectedInteriorNodes(p1_p2_p6_p2_p9_p9interior4)[1];
        p1_p2_p6_p2_p9_p9_p9interior4.setLabel("D");
        InteriorNode p1_p2_p6_p2_p2_p9_p9interior5 = getConnectedInteriorNodes(p1_p2_p6_p2_p2_p9interior1)[1];
        p1_p2_p6_p2_p2_p9_p9interior5.setLabel("E");
        InteriorNode p1_p2_p6_p2_p2_p9_p9interior6 = getConnectedInteriorNodes(p1_p2_p6_p2_p2_p9interior2)[1];
        p1_p2_p6_p2_p2_p9_p9interior6.setLabel("F");
        InteriorNode p1_p2_p6_p2_p2_p2_p9interior7 = getConnectedInteriorNodes(p1_p2_p6_p2_p2_p2interior2)[1];
        p1_p2_p6_p2_p2_p2_p9interior7.setLabel("G");
        InteriorNode p1_p2_p6_p2_p2_p2_p9interior8 = getConnectedInteriorNodes(p1_p2_p6_p2_p2_p2interior5)[1];
        p1_p2_p6_p2_p2_p2_p9interior8.setLabel("H");
        InteriorNode p1_p2_p6_p2_p2_p2_p9interior9 = getConnectedInteriorNodes(p1_p2_p6_p2_p2_p2interior9)[1];
        p1_p2_p6_p2_p2_p2_p9interior9.setLabel("I");
        InteriorNode p1_p2_p6_p2_p2_p2_p9interior10 = getConnectedInteriorNodes(p1_p2_p6_p2_p2_p2interior11)[1];
        p1_p2_p6_p2_p2_p2_p9interior10.setLabel("J");


        InteriorNode p1_p2_p6_p2_p2_p2_p2interior1 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior1)[1];
        p1_p2_p6_p2_p2_p2_p2interior1.setLabel("K");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior2 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior1)[2];
        p1_p2_p6_p2_p2_p2_p2interior2.setLabel("L");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior3 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior10)[1];
        p1_p2_p6_p2_p2_p2_p2interior3.setLabel("M");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior4 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior10)[2];
        p1_p2_p6_p2_p2_p2_p2interior4.setLabel("N");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior5 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior3)[1];
        p1_p2_p6_p2_p2_p2_p2interior5.setLabel("O");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior6 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior3)[2];
        p1_p2_p6_p2_p2_p2_p2interior6.setLabel("P");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior7 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior4)[1];
        p1_p2_p6_p2_p2_p2_p2interior7.setLabel("Q");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior8 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior4)[2];
        p1_p2_p6_p2_p2_p2_p2interior8.setLabel("R");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior9 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior6)[1];
        p1_p2_p6_p2_p2_p2_p2interior9.setLabel("S");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior10 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior6)[2];
        p1_p2_p6_p2_p2_p2_p2interior10.setLabel("T");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior11 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior7)[1];
        p1_p2_p6_p2_p2_p2_p2interior11.setLabel("U");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior12 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior7)[2];
        p1_p2_p6_p2_p2_p2_p2interior12.setLabel("W");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior13 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior8)[1];
        p1_p2_p6_p2_p2_p2_p2interior13.setLabel("V");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior14 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior8)[2];
        p1_p2_p6_p2_p2_p2_p2interior14.setLabel("X");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior15 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior12)[1];
        p1_p2_p6_p2_p2_p2_p2interior15.setLabel("Y");
        InteriorNode p1_p2_p6_p2_p2_p2_p2interior16 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2interior12)[2];
        p1_p2_p6_p2_p2_p2_p2interior16.setLabel("Z");

        visualizer.visualize(new LayerDescriptor(6));

        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p9_p9_p9interior1);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p9_p9_p9interior2);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p9_p9_p9interior3);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p9_p9_p9interior4);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p9_p9interior5);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p9_p9interior6);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p9interior7);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p9interior8);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p9interior9);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p9interior10);

        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p2interior1);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p2interior2);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p2interior3);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p2interior4);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p2interior5);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p2interior7);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p2interior9);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p2interior10);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p2interior11);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p2interior13);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p2interior15);
        applyTransformationIfPossible(new TransformationP9(), graph, p1_p2_p6_p2_p2_p2_p2interior16);

        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2_p2_p2interior8);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2_p2_p2interior6);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2_p2_p2interior12);
        applyTransformationIfPossible(new TransformationP2(), graph, p1_p2_p6_p2_p2_p2_p2interior14);

        visualizer.visualize(new LayerDescriptor(7));

        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p9_p9_p9interior1, p1_p2_p6_p2_p9_p9_p9interior2);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p9_p9_p9interior2, p1_p2_p6_p2_p9_p9_p9interior3);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p9_p9_p9interior3, p1_p2_p6_p2_p9_p9_p9interior4);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p9_p9interior5, p1_p2_p6_p2_p2_p2_p9interior7);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p9_p9interior5, p1_p2_p6_p2_p2_p2_p9interior8);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p9_p9interior6, p1_p2_p6_p2_p2_p2_p9interior9);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p9_p9interior6, p1_p2_p6_p2_p2_p2_p9interior10);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p9_p9_p9interior1, p1_p2_p6_p2_p2_p9_p9interior5);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p9_p9_p9interior4, p1_p2_p6_p2_p2_p9_p9interior6);
        applyTwoInteriorsTransformationIfPossible(new TransformationP13(), graph, p1_p2_p6_p2_p2_p2_p9interior8, p1_p2_p6_p2_p2_p2_p9interior9);

        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p9interior7, p1_p2_p6_p2_p2_p2_p2interior1);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p9interior8, p1_p2_p6_p2_p2_p2_p2interior9);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p9interior9, p1_p2_p6_p2_p2_p2_p2interior3);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p9interior10, p1_p2_p6_p2_p2_p2_p2interior15);

        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior1, p1_p2_p6_p2_p2_p2_p2interior2);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior9, p1_p2_p6_p2_p2_p2_p2interior5);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior3, p1_p2_p6_p2_p2_p2_p2interior4);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior15, p1_p2_p6_p2_p2_p2_p2interior13);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior15, p1_p2_p6_p2_p2_p2_p2interior16);

        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior7, p1_p2_p6_p2_p2_p2_p2interior5);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior10, p1_p2_p6_p2_p2_p2_p2interior4);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior11, p1_p2_p6_p2_p2_p2_p2interior13);

        InteriorNode p1_p2_p6_p2_p2_p2_p2_p2interior1 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2_p2interior8)[1];
        p1_p2_p6_p2_p2_p2_p2_p2interior1.setLabel("AA");
        InteriorNode p1_p2_p6_p2_p2_p2_p2_p2interior2 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2_p2interior8)[2];
        p1_p2_p6_p2_p2_p2_p2_p2interior2.setLabel("BB");
        InteriorNode p1_p2_p6_p2_p2_p2_p2_p2interior3 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2_p2interior6)[1];
        p1_p2_p6_p2_p2_p2_p2_p2interior3.setLabel("CC");
        InteriorNode p1_p2_p6_p2_p2_p2_p2_p2interior4 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2_p2interior6)[2];
        p1_p2_p6_p2_p2_p2_p2_p2interior4.setLabel("DD");
        InteriorNode p1_p2_p6_p2_p2_p2_p2_p2interior5 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2_p2interior12)[1];
        p1_p2_p6_p2_p2_p2_p2_p2interior5.setLabel("EE");
        InteriorNode p1_p2_p6_p2_p2_p2_p2_p2interior6 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2_p2interior12)[2];
        p1_p2_p6_p2_p2_p2_p2_p2interior6.setLabel("FF");
        InteriorNode p1_p2_p6_p2_p2_p2_p2_p2interior7 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2_p2interior14)[1];
        p1_p2_p6_p2_p2_p2_p2_p2interior7.setLabel("GG");
        InteriorNode p1_p2_p6_p2_p2_p2_p2_p2interior8 = getConnectedInteriorNodesSorted(p1_p2_p6_p2_p2_p2_p2interior14)[2];
        p1_p2_p6_p2_p2_p2_p2_p2interior8.setLabel("HH");

        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior2, p1_p2_p6_p2_p2_p2_p2interior8);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior7, p1_p2_p6_p2_p2_p2_p2interior8);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior10, p1_p2_p6_p2_p2_p2_p2interior6);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior11, p1_p2_p6_p2_p2_p2_p2interior12);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior16, p1_p2_p6_p2_p2_p2_p2interior14);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior5, p1_p2_p6_p2_p2_p2_p2interior6);
        applyTwoInteriorsTransformationIfPossible(new TransformationP12(), graph, p1_p2_p6_p2_p2_p2_p2interior4, p1_p2_p6_p2_p2_p2_p2interior12);
        applyTwoInteriorsTransformationIfPossible(new TransformationP13(), graph, p1_p2_p6_p2_p2_p2_p2interior13, p1_p2_p6_p2_p2_p2_p2interior14);

        visualizer.visualize(new LayerDescriptor(7));

        visualizer.visualize(new LayerDescriptor(0));
        visualizer.visualize(new LayerDescriptor(1));
        visualizer.visualize(new LayerDescriptor(2));
        visualizer.visualize(new LayerDescriptor(3));
        visualizer.visualize(new LayerDescriptor(4));
        visualizer.visualize(new LayerDescriptor(5));
        visualizer.visualize(new LayerDescriptor(6));
        visualizer.visualize(new LayerDescriptor(7));
    }

    private static void applyTransformationIfPossible(Transformation transformation, GraphModel graph, InteriorNode interiorNode) {
        if (transformation.isApplicable(graph, interiorNode)) {
            transformation.transform(graph, interiorNode);
            System.out.println("Executing transformation: " + transformation.getClass().getSimpleName() + " on interior " + interiorNode.getLabel());
        } else {
            final Visualizer visualizer = new Visualizer(graph);
//            visualizer.visualize();
            throw new IllegalStateException("Failed to execute " + transformation.getClass().getSimpleName());
        }
    }

    private static void applyTwoInteriorsTransformationIfPossible(DoubleInteriorTransformation transformation,
                                                                  GraphModel graph,
                                                                  InteriorNode interiorNode1,
                                                                  InteriorNode interiorNode2) {
        if (transformation.isApplicable(graph, interiorNode1, interiorNode2)) {
            transformation.transform(graph, interiorNode1, interiorNode2);
            System.out.println("Executing two interiors transformation: " + transformation.getClass().getSimpleName()
                    + " on interiors " + interiorNode1.getLabel() + " ," + interiorNode2.getLabel());
        } else {
            final Visualizer visualizer = new Visualizer(graph);
//            visualizer.visualize();
            throw new IllegalStateException("Failed to execute " + transformation.getClass().getSimpleName());
        }
    }

    private static InteriorNode[] getConnectedInteriorNodes(InteriorNode startingNode) {
        return startingNode.getAdjacentInteriors().toArray(InteriorNode[]::new);
    }

    private static InteriorNode[] getConnectedInteriorNodesSorted(InteriorNode startingNode) {
        InteriorNode[] all = startingNode.getAdjacentInteriors().toArray(InteriorNode[]::new);
        InteriorNode[] next = Stream.of(all[1], all[2]).sorted(
                Comparator.comparingDouble(GraphNode::getXCoordinate).thenComparingDouble(GraphNode::getYCoordinate)
        ).toArray(InteriorNode[]::new);
        return new InteriorNode[] {all[0], next[0], next[1]};
    }

    private static GraphModel createStartingGraph() {
        final GraphModel graphModel = new GraphModel();
        graphModel.insertStartingInterior("S", new LayerDescriptor(0), new Coordinates(0, 0, 0));
        return graphModel;
    }

    private static GraphModel createGraphApplicableForP3() {
        final GraphModel graphModel = new GraphModel();
        LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        Coordinates stNoCo = new Coordinates(0, 0, 0);

        Coordinates v1Co = new Coordinates(stNoCo.getX() + 1, stNoCo.getY() + 1, stNoCo.getZ());
        Coordinates v2Co = new Coordinates(stNoCo.getX() - 0.5, stNoCo.getY(), stNoCo.getZ()); // TODO: those coordinates will probably change to accommodate the size of the map
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

    private static GraphModel createSimpleGraph() {
        final GraphModel graphModel = new GraphModel();
        final LayerDescriptor layerDescriptor = new LayerDescriptor(0);
        final Vertex v1 = graphModel.insertVertex("V1", new Coordinates(0, 0, 0), layerDescriptor).get();
        final Vertex v2 = graphModel.insertVertex("V2", new Coordinates(2, 0, 0), layerDescriptor).get();
        final Vertex v3 = graphModel.insertVertex("V3", new Coordinates(1, 2, 0), layerDescriptor).get();
        final Vertex v4 = graphModel.insertVertex("V4", new Coordinates(0, 4, 0), layerDescriptor).get();
        final Vertex v5 = graphModel.insertVertex("V5", new Coordinates(2, 4, 0), layerDescriptor).get();

        final GraphEdge e1 = graphModel.insertEdge(v1, v2, layerDescriptor).get();
        final GraphEdge e2 = graphModel.insertEdge(v2, v3, layerDescriptor).get();
        final GraphEdge e3 = graphModel.insertEdge(v3, v1, layerDescriptor).get();
        final GraphEdge e4 = graphModel.insertEdge(v3, v4, layerDescriptor).get();
        final GraphEdge e5 = graphModel.insertEdge(v3, v5, layerDescriptor).get();
        final GraphEdge e6 = graphModel.insertEdge(v4, v5, layerDescriptor).get();

        final InteriorNode i1 = graphModel.insertInterior("I", layerDescriptor, v1, v2, v3).get();
        final InteriorNode i2 = graphModel.insertInterior("I", layerDescriptor, v3, v4, v5).get();

        //graphModel.removeInterior(i1);
        graphModel.removeVertex(v1);
        return graphModel;
    }

    private static GraphModel createMultiLayerGraph() {
        final GraphModel graphModel = new GraphModel();
        final LayerDescriptor layerDescriptor0 = new LayerDescriptor(0);
        final LayerDescriptor layerDescriptor1 = layerDescriptor0.getNextLayerDescriptor();

        final Vertex v1 = graphModel.insertVertex("V1", new Coordinates(0, 0, 0), layerDescriptor0).get();
        final Vertex v2 = graphModel.insertVertex("V2", new Coordinates(2, 0, 0), layerDescriptor0).get();
        final Vertex v3 = graphModel.insertVertex("V3", new Coordinates(1, 2, 0), layerDescriptor0).get();
        final Vertex v4 = graphModel.insertVertex("V4", new Coordinates(0, 2, 1), layerDescriptor1).get();
        final Vertex v5 = graphModel.insertVertex("V5", new Coordinates(3, 1, 1), layerDescriptor1).get();
        final Vertex v6 = graphModel.insertVertex("V6", new Coordinates(3, 3, 1), layerDescriptor1).get();

        final GraphEdge e1 = graphModel.insertEdge(v1, v2, layerDescriptor0).get();
        final GraphEdge e2 = graphModel.insertEdge(v2, v3, layerDescriptor0).get();
        final GraphEdge e3 = graphModel.insertEdge(v3, v1, layerDescriptor0).get();
        final GraphEdge e4 = graphModel.insertEdge(v4, v5, layerDescriptor1).get();
        final GraphEdge e5 = graphModel.insertEdge(v5, v6, layerDescriptor1).get();
        final GraphEdge e6 = graphModel.insertEdge(v6, v4, layerDescriptor1).get();

        final InteriorNode i1 = graphModel.insertInterior("I1", layerDescriptor0, v1, v2, v3).get();
        final InteriorNode i2 = graphModel.insertInterior("I2", layerDescriptor1, v4, v5, v6).get();

        final GraphEdge ei = graphModel.insertEdge(i1, i2).get();
        //graphModel.removeInterior(i1.getUUID());
        graphModel.removeEdge(e1);
        return graphModel;
    }
}
