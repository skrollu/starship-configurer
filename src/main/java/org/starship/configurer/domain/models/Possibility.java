package org.starship.configurer.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Objects;

@Builder
@Data
public class Possibility {
    @NonNull
    private ComponentType componentType;
    @Builder.Default
    private int number = 1;
    @NonNull
    private Size size;

    public boolean isCompatible(Possibility with) {
        return this.isCompatible(with.getComponentType(), with.size);
    }

    /**
     * @param componentType
     * @param size
     * @return true if the given componentType is equal to the current possibility
     * and the given size is =< to the current possibility
     */
    public boolean isCompatible(ComponentType componentType, Size size) {
        if (!this.componentType.equals(componentType))
            return false;
        if (this.size.compare(size) == -1)
            return false;
        return true;
    }

    /**
     * Doesn't include number to have Possibility unity
     *
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
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(componentType, size);
    }
}
