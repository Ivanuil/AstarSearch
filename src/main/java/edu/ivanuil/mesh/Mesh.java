package edu.ivanuil.mesh;

import edu.ivanuil.cell.Cell;

import java.util.Set;

public interface Mesh {

    /**
     * Returns an unmodifiable copy of Cells Set
     * @return copy of Cells Set
     */
    Set<Cell> getCells();

    Cell getStartCell();
    Cell getEndCell();

    double getPrecision();

}
