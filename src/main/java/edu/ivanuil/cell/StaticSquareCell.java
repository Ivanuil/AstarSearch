package edu.ivanuil.cell;

import edu.ivanuil.util.Coordinates;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class StaticSquareCell implements Cell {

    @Getter
    private final Coordinates center;
    private final double size;
    @Getter @Setter
    private double pathLength;
    @Getter @Setter
    private CellState cellState;

    private final Set<StaticSquareCell> neighbors = new HashSet<>();

    @Override
    public List<Coordinates> getVertices() {
        return List.of(
                new Coordinates(center.longitude() - size / 2, center.latitude() - size / 2),
                new Coordinates(center.longitude() - size / 2, center.latitude() + size / 2),
                new Coordinates(center.longitude() + size / 2, center.latitude() + size / 2),
                new Coordinates(center.longitude() + size / 2, center.latitude() - size / 2));
    }

    @Override
    public Set<Cell> getAdjacentCells() {
        return new HashSet<>(neighbors);
    }

    @Override
    public void addAdjacentCell(Cell cell) {
        neighbors.add((StaticSquareCell) cell);
    }

    @Override
    public double getHeuristicDistance(Cell target) {
        return getCenter().getDistance(target.getCenter());
    }

    @Override
    public String toString() {
        return "Cell {" + center.longitude() + "; " + center.latitude() + "}";
    }

}

