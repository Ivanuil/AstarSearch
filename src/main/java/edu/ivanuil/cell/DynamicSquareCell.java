package edu.ivanuil.cell;

import edu.ivanuil.mesh.DynamicSquareMesh;
import edu.ivanuil.util.Coordinates;
import edu.ivanuil.util.IntegerCoordinates;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class DynamicSquareCell implements Cell {

    @Getter
    private final Coordinates center;
    @Getter
    private final IntegerCoordinates coordinates;
    @Getter @Setter
    private CellState cellState;
    private final double precision;

    private final DynamicSquareMesh mesh;
    private final Set<DynamicSquareCell> neighbours = new HashSet<>();
    private boolean areNeighboursInitialised = false;

    public DynamicSquareCell(Coordinates center, CellState cellState, double precision,
                             DynamicSquareMesh mesh, IntegerCoordinates coordinates) {
        this.center = center;
        this.cellState = cellState;
        this.precision = precision;
        this.mesh = mesh;
        this.coordinates = coordinates;
    }

    @Override
    public List<Coordinates> getVertices() {
        return List.of(
                new Coordinates(center.longitude() - precision / 2, center.latitude() - precision / 2),
                new Coordinates(center.longitude() - precision / 2, center.latitude() + precision / 2),
                new Coordinates(center.longitude() + precision / 2, center.latitude() + precision / 2),
                new Coordinates(center.longitude() + precision / 2, center.latitude() - precision / 2));
    }

    @Override
    public Set<Cell> getAdjacentCells() {
        if (!areNeighboursInitialised) {
            initializeNeighbours();
        }
        return Set.copyOf(neighbours);
    }

    private void initializeNeighbours() {
        var cellsMap = mesh.getCellsMap();
        Map<IntegerCoordinates, DynamicSquareCell> newCells = new HashMap<>();
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;

                initializeNeighbour(cellsMap, i, j, newCells);
            }
        mesh.addCells(newCells);
        areNeighboursInitialised = true;
    }

    private void initializeNeighbour(Map<IntegerCoordinates, DynamicSquareCell> cellsMap, int i, int j,
                                     Map<IntegerCoordinates, DynamicSquareCell> newCells) {
        var neighbourCoordinates = coordinates.getShifted(i, j);
        if (cellsMap.containsKey(neighbourCoordinates)) {
            var neighbour = cellsMap.get(neighbourCoordinates);
            neighbour.addAdjacentCell(this);
            neighbours.add(neighbour);
        } else {
            var neighbour = new DynamicSquareCell(
                    center.add(
                            precision * i,
                            precision * j),
                    CellState.NOT_TRAVERSED,
                    precision, mesh,
                    neighbourCoordinates);
            neighbour.addAdjacentCell(this);
            addAdjacentCell(neighbour);
            neighbours.add(neighbour);
            newCells.put(neighbourCoordinates, neighbour);
        }
    }

    @Override
    public void addAdjacentCell(Cell cell) {
        neighbours.add((DynamicSquareCell) cell);
    }

    @Override
    public double getHeuristicDistance(Cell target) {
        return center.getDistance(target.getCenter());
    }

}
