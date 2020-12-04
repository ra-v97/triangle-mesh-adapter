package pl.edu.agh.gg.model.api;

import java.util.Objects;
import java.util.UUID;

public interface Identifiable {

    UUID getUUID();

    // Returns unique string id of graph element, which is required by GraphStream Library.
    // This must be unique across GS graph.
    String getStringId();

    // Return graph element label. Labels can repeat, they are displayed as GS node labels
    // and also used in Identifiable elements comparison in order to fit appropriate left side of grammar production.
    String getLabel();

    default boolean logicalEquals(Identifiable identifiable) {
        if(identifiable == null){
            return false;
        }
        return Objects.equals(this.getLabel(), identifiable.getLabel());
    }
}
