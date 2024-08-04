package edu.ivanuil.field;

import edu.ivanuil.util.Coordinates;
import edu.ivanuil.util.Rectangle;

import java.util.HashSet;
import java.util.Set;

public class PuncturedField implements Field {

    private final double difficulty;
    final Set<Rectangle> holes = new HashSet<>();

    public PuncturedField(double baseDifficulty) {
        this.difficulty = baseDifficulty;
    }

    public void addHole(Rectangle hole) {
        holes.add(hole);
    }

    public Set<Rectangle> getHoles() {
        return Set.copyOf(holes);
    }

    @Override
    public double getDifficulty(Coordinates point1, Coordinates point2) {
        for (var hole : holes) {
            if (hole.isInside(point1) || hole.isInside(point2))
                return Double.POSITIVE_INFINITY;
        }
        return difficulty * point1.getDistance(point2);
    }
}
