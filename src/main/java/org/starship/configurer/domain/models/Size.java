package org.starship.configurer.domain.models;

public enum Size {
    ONE(1),
    TWO(2),
    THREE(3);

    public final int value;

    private Size(int value) {
        this.value = value;
    }

    /**
     * compare the given size with the callee
     * @param size
     * @return <br/> 1 if the callee is greater than the compared Size
     * <br/> 0 if the callee is the same as the compared Size
     * <br/> -1 if the callee is smaller than the compared Size
     */
    public int compare(Size size) {
        if (this.isGreaterThan(size))
            return 1;
        if (this.isSmallerThan(size))
            return -1;
        return 0;
    }

    boolean isGreaterThan(Size size) {
        return this.value > size.value;
    }


    boolean isSmallerThan(Size size) {
        return this.value < size.value;
    }
}
