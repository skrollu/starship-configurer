package org.starship.configurer.domain.models;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Builder
@Data
public class Possibility {
    private ComponentType componentType;
    private int number;
    private Size size;

    /**
     * Doesn't include number to have Possibility unity
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Possibility that = (Possibility) o;
        return componentType == that.componentType && size == that.size;
    }

    /**
     * Doesn't include number to have Possibility unity
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(componentType, size);
    }
}
