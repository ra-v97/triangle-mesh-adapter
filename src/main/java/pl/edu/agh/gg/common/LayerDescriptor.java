package pl.edu.agh.gg.common;

import java.util.Objects;
import java.util.Optional;

public final class LayerDescriptor {

    private final long layerNo;

    public LayerDescriptor() {
        layerNo = 0;
    }

    public LayerDescriptor(long layerNo) throws IllegalArgumentException {
        if (layerNo < 0) {
            throw new IllegalArgumentException("Layer cannot have negative number");
        }
        this.layerNo = layerNo;
    }

    public LayerDescriptor getNextLayerDescriptor() {
        return new LayerDescriptor(layerNo + 1);
    }

    public Optional<LayerDescriptor> getPreviousLayerDescriptor() {
        try {
            return Optional.of(new LayerDescriptor(layerNo - 1));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LayerDescriptor that = (LayerDescriptor) o;
        return layerNo == that.layerNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(layerNo);
    }
}
