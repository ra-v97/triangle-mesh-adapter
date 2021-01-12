package pl.edu.agh.gg.processing;

import com.google.common.collect.Lists;
import org.javatuples.Pair;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.transformations.DoubleInteriorTransformation;
import pl.edu.agh.gg.transformations.Transformation;

import java.util.*;

public class ProductionChainController {

    private final GraphModel graph;

    private final ControlDiagram controlDiagram;

    public ProductionChainController(ControlDiagram controlDiagram, GraphModel initialGraph) {
        this.graph = initialGraph;
        this.controlDiagram = controlDiagram;
    }

    public Optional<GraphModel> nextStep() {
        final Optional<StepDescriptor> nextStepDescriptor = controlDiagram.nextStep();

        if (nextStepDescriptor.isEmpty() || graph == null) {
            return Optional.empty();
        }

        final StepDescriptor stepDescriptor = nextStepDescriptor.get();

        final var interiorsOnLayer = graph.getInteriorsOnLayer(stepDescriptor.getActiveLayer());

        if (stepDescriptor.isDoubleTransformation()) {
            return stepDescriptor.getDoubleInteriorTransformation()
                    .flatMap(transformation -> interiorsCrossJoin(interiorsOnLayer).stream()
                            .filter(interior -> transformation
                                    .isApplicable(graph, interior.getValue0(), interior.getValue1()))
                            .findFirst()
                            .flatMap(interior ->
                                    tryDoubleInteriorTransformation(transformation, interior.getValue0(), interior.getValue1())));
        } else {
            return stepDescriptor.getTransformation()
                    .flatMap(transformation -> interiorsOnLayer.stream()
                            .filter(interior -> transformation.isApplicable(graph, interior))
                            .findFirst()
                            .flatMap(interiorNode -> tryTransformation(transformation, interiorNode)));
        }
    }

    private List<Pair<InteriorNode, InteriorNode>> interiorsCrossJoin(List<InteriorNode> interiorNodeList) {
        if (interiorNodeList == null) {
            return List.of();
        }

        final List<Pair<InteriorNode, InteriorNode>> crossJoinList = Lists.newLinkedList();

        final var size = interiorNodeList.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                crossJoinList.add(new Pair<>(interiorNodeList.get(i), interiorNodeList.get(j)));
            }
        }
        return crossJoinList;
    }

    public Optional<GraphModel> tryTransformation(Transformation transformation, InteriorNode interiorNode) {
        if (Objects.isNull(graph) || !transformation.isApplicable(graph, interiorNode)) {
            return Optional.empty();
        }

        System.out.println("Executing transformation: "
                + transformation.getClass().getSimpleName()
                + " on interior" + interiorNode.getLabel());

        transformation.transform(graph, interiorNode);
        return Optional.of(graph);
    }

    public Optional<GraphModel> tryDoubleInteriorTransformation(DoubleInteriorTransformation transformation,
                                                                InteriorNode interiorNodeA, InteriorNode interiorNodeB) {

        if (Objects.isNull(graph) || !transformation.isApplicable(graph, interiorNodeA, interiorNodeB)) {
            return Optional.empty();
        }
        System.out.println("Executing double node transformation: "
                + transformation.getClass().getSimpleName()
                + " on interiors" + interiorNodeA.getLabel()
                + " " + interiorNodeB.getLabel());

        transformation.transform(graph, interiorNodeA, interiorNodeB);
        return Optional.of(graph);
    }
}
