package pl.edu.agh.gg.processing;

import com.google.common.collect.Lists;
import org.javatuples.Pair;
import pl.edu.agh.gg.model.GraphModel;
import pl.edu.agh.gg.model.InteriorNode;
import pl.edu.agh.gg.transformations.DoubleInteriorTransformation;
import pl.edu.agh.gg.transformations.Transformation;

import java.util.*;

public class ProductionChainController {

    private final GraphModel initialGraph;

    private final ControlDiagram controlDiagram;

    private final Deque<GraphModel> graphResolvingStages;

    public ProductionChainController(ControlDiagram controlDiagram, GraphModel initialGraph) {
        this.initialGraph = new GraphModel(initialGraph);
        this.graphResolvingStages = new ArrayDeque<>();
        this.controlDiagram = controlDiagram;
        this.graphResolvingStages.push(this.initialGraph);
    }

    public Optional<GraphModel> nextStep() {
        final Optional<StepDescriptor> nextStepDescriptor = controlDiagram.nextStep();

        if (nextStepDescriptor.isEmpty() || graphResolvingStages.isEmpty()) {
            return Optional.empty();
        }

        final StepDescriptor stepDescriptor = nextStepDescriptor.get();

        final GraphModel topStackGraph = graphResolvingStages.peekLast();

        final var interiorsOnLayer = topStackGraph.getInteriorsOnLayer(stepDescriptor.getActiveLayer());

        if (stepDescriptor.isDoubleTransformation()) {
            return stepDescriptor.getDoubleInteriorTransformation()
                    .flatMap(transformation -> interiorsCrossJoin(interiorsOnLayer).stream()
                            .filter(interior -> transformation
                                    .isApplicable(topStackGraph, interior.getValue0(), interior.getValue1()))
                            .findFirst()
                            .flatMap(interior ->
                                    tryDoubleInteriorTransformation(transformation, interior.getValue0(), interior.getValue1())));
        } else {
            return stepDescriptor.getTransformation()
                    .flatMap(transformation -> interiorsOnLayer.stream()
                            .filter(interior -> transformation.isApplicable(topStackGraph, interior))
                            .findFirst()
                            .flatMap(interiorNode -> tryTransformation(transformation, interiorNode)));
        }
    }

    public Optional<GraphModel> previousStage() {
        if (graphResolvingStages.isEmpty()) {
            return Optional.empty();
        }

        controlDiagram.prevStep();
        graphResolvingStages.pop();

        if (graphResolvingStages.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(graphResolvingStages.peekLast());
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
        if (graphResolvingStages.isEmpty()) {
            return Optional.empty();
        }
        final GraphModel topStackGraph = graphResolvingStages.peekLast();

        if (Objects.isNull(topStackGraph) || !transformation.isApplicable(topStackGraph, interiorNode)) {
            return Optional.empty();
        }
        final GraphModel graphCopy = new GraphModel(topStackGraph);

        System.out.println("Executing transformation: "
                + transformation.getClass().getSimpleName()
                + " on interior" + interiorNode.getLabel());

        transformation.transform(graphCopy, interiorNode);
        graphResolvingStages.push(graphCopy);
        return Optional.of(graphCopy);
    }

    public Optional<GraphModel> tryDoubleInteriorTransformation(DoubleInteriorTransformation transformation,
                                                                InteriorNode interiorNodeA, InteriorNode interiorNodeB) {
        if (graphResolvingStages.isEmpty()) {
            return Optional.empty();
        }

        final GraphModel topStackGraph = graphResolvingStages.peekLast();
        if (Objects.isNull(topStackGraph) || !transformation.isApplicable(topStackGraph, interiorNodeA, interiorNodeB)) {
            return Optional.empty();
        }
        final GraphModel graphCopy = new GraphModel(topStackGraph);

        System.out.println("Executing double node transformation: "
                + transformation.getClass().getSimpleName()
                + " on interiors" + interiorNodeA.getLabel()
                + " " + interiorNodeB.getLabel());

        transformation.transform(graphCopy, interiorNodeA, interiorNodeB);
        graphResolvingStages.push(graphCopy);
        return Optional.of(graphCopy);
    }
}
