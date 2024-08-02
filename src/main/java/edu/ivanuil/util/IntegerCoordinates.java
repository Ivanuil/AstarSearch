package edu.ivanuil.util;

public record IntegerCoordinates(
        int x,
        int y) {

    public IntegerCoordinates getShifted(int xShift, int yShift) {
        return new IntegerCoordinates(x + xShift, y + yShift);
    }

}
