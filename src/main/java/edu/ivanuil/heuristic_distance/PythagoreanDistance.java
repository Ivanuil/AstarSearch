package edu.ivanuil.heuristic_distance;

import edu.ivanuil.cell.Cell;

public class PythagoreanDistance implements HeuristicDistance {

    @Override
    public double getHeuristicDistance(Cell cell, Cell target) {
        return cell.getCenter().getDistance(target.getCenter());
    }

}
