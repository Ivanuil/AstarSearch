package edu.ivanuil.heuristic_distance;

import edu.ivanuil.cell.Cell;

public class ManhattanDistance implements HeuristicDistance {

    @Override
    public double getHeuristicDistance(Cell cell, Cell target) {
        double xDistance = Math.abs(cell.getCenter().longitude() - target.getCenter().longitude());
        double yDistance = Math.abs(cell.getCenter().latitude() - target.getCenter().latitude());
        return xDistance + yDistance;
    }

}
