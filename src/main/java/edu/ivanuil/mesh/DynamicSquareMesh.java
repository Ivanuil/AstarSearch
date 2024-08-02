package edu.ivanuil.mesh;

import edu.ivanuil.cell.Cell;
import edu.ivanuil.cell.CellState;
import edu.ivanuil.cell.DynamicSquareCell;
import edu.ivanuil.util.Coordinates;
import edu.ivanuil.util.IntegerCoordinates;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class DynamicSquareMesh implements Mesh {

    private final Map<IntegerCoordinates, DynamicSquareCell> cells = new HashMap<>();
    @Getter
    private final DynamicSquareCell startCell;
    @Getter
    private final DynamicSquareCell endCell;
    private final double precision;

    public DynamicSquareMesh(Coordinates start, Coordinates end, double precision) {
        this.precision = precision;

        startCell = new DynamicSquareCell(start, CellState.START, precision,
                this, new IntegerCoordinates(0, 0));
        endCell = new DynamicSquareCell(
                new Coordinates(
                        convertCoordinate(end.longitude(), start.longitude()),
                        convertCoordinate(end.latitude(), start.latitude())),
                CellState.END, precision, this,
                new IntegerCoordinates(
                        (int) Math.round((end.longitude() - start.longitude()) / precision),
                        (int) Math.round((end.latitude() - start.latitude()) / precision)));

        cells.put(startCell.getCoordinates(), startCell);
        cells.put(endCell.getCoordinates(), endCell);

        log.info("Initialized dynamic square mesh, initial number of vertices: {}", cells.size());
    }

    private double convertCoordinate(double coordinate, double zeroCoordinate) {
        double shift = coordinate - zeroCoordinate;
        long count = Math.round(shift / precision);
        return zeroCoordinate + count * precision;
    }

    public Map<IntegerCoordinates, DynamicSquareCell> getCellsMap() {
        return Map.copyOf(cells);
    }

    public void addCells(Map<IntegerCoordinates, DynamicSquareCell> newCells) {
        if (newCells.isEmpty())
            return;

        cells.putAll(newCells);
        log.info("Initialised new cells in dynamic square mesh, count: {}", newCells.size());
    }

    @Override
    public Set<Cell> getCells() {
        return Set.copyOf(cells.values());
    }

}
