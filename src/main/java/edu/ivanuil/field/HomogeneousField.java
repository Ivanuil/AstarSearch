package edu.ivanuil.field;

import edu.ivanuil.util.Coordinates;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HomogeneousField implements Field {

    private final double difficulty;

    @Override
    public double getDifficulty(Coordinates point1, Coordinates point2) {
        return difficulty * point1.getDistance(point2);
    }
}
