package edu.ivanuil.util;

public record TwoDimensionalIndex(
        double x,
        double y) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TwoDimensionalIndex that)) return false;
        return Double.compare(x, that.x) == 0 && Double.compare(y, that.y) == 0;
    }

}
