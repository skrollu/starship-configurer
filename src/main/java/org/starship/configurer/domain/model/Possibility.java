package org.starship.configurer.domain.model;

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

    /**
     * @param with
     * @return true if the given componentType is equal to the current possibility
     * and the given size is =< to the current possibility
     * and number =< too
     */
    public boolean isCompatibleWith(final @NonNull Possibility with) {
        return this.isCompatibleWith(with.getComponentType(), with.getSize(), with.getNumber());
    }

    /**
     * @param componentType
     * @param size
     * @param number
     * @return true if the given componentType is equal to the current possibility
     * and the given size is =< to the current possibility
     * and number =< too
     */
     boolean isCompatibleWith(final @NonNull ComponentType componentType,
                                 final @NonNull Size size,
                                 final int number) {
        if (!this.componentType.equals(componentType))
            return false;
        if (!this.size.isGreaterThanOrEqualTo(size))
            return false;
        if (this.number < number)
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
