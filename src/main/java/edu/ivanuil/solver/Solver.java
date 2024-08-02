package edu.ivanuil.solver;

import edu.ivanuil.cell.Cell;

import java.util.List;

public interface Solver {

    void solve();
    List<Cell> getPath();
    double getPathDifficulty();

}
