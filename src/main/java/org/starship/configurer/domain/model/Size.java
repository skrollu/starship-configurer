package org.starship.configurer.domain.model;

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
     *
     * @param size
     * @return <br/>  1 if the callee is greater than the compared Size
     * <br/>  0 if the callee is equal to the compared Size
     * <br/> -1 if the callee is smaller than the compared Size
     */
    @Deprecated
    public int compare(Size size) {
        if (this.isGreaterThan(size))
            return 1;
        if (this.isSmallerThan(size))
            return -1;
        return 0;
    }

    /**
     * @param size The size to compare with the callee
     * @return true if the callee is greater than or equal to the given size
     */
    public boolean isGreaterThanOrEqualTo(Size size) {
        return isGreaterThan(size) || this.value == size.value;
    }

    /**
     * @param size The size to compare with the callee
     * @return true if the callee is greater than the given size
     */
    public boolean isGreaterThan(Size size) {
        return this.value > size.value;
    }

    /**
     * @param size The size to compare with the callee
     * @return true if the callee is smaller than or equal to the given size
     */
    public boolean isSmallerThanOrEqualTo(Size size) {
        return isSmallerThan(size) || this.value == size.value;
    }

    /**
     * @param size The size to compare with the callee
     * @return true if the callee is smaller than the given size
     */
    public boolean isSmallerThan(Size size) {
        return this.value < size.value;
    }
}
