package pl.edu.agh.gg.model;

import pl.edu.agh.gg.common.Coordinates;

import java.util.UUID;

public class StartingNode extends InteriorNode {

    private static final String STARTING_NODE_DEFAULT_SYMBOL = "S";

    public StartingNode(UUID id, String label, Coordinates coordinates) {
        super(id, label, coordinates);
    }
}
