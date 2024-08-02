package edu.ivanuil.cell;

import edu.ivanuil.util.Coordinates;

import java.util.List;
import java.util.Set;

public interface Cell {

    Coordinates getCenter();
    List<Coordinates> getVertices();

    Set<Cell> getAdjacentCells();

    void addAdjacentCell(Cell cell);

    double getHeuristicDistance(Cell target);

    CellState getCellState();

}
