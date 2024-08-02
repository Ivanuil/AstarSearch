package edu.ivanuil.solver;

import edu.ivanuil.cell.Cell;
import edu.ivanuil.cell.CellMeta;
import edu.ivanuil.field.Field;
import edu.ivanuil.mesh.Mesh;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class BasicSolver implements Solver {

    private final Mesh mesh;
    private final Field field;

    private final Set<CellMeta> openList = new HashSet<>();
    private CellMeta chosenCell = null;

    private List<Cell> path = null;
    private double pathDifficulty;

    @Override
    public void solve() {
        openList.add(new CellMeta(mesh.getStartCell(), null, 0, 0));
        int stepsCount = 0;
        while (makeStep()) {
            stepsCount++;
            log.info("Made a step");
        }
        log.info("Path found in {} steps, total length: {}, total difficulty: {}",
                stepsCount, chosenCell.pathLength(), chosenCell.pathTotalDifficulty());
    }

    private boolean makeStep() {
        double minV = Double.POSITIVE_INFINITY;
        for (var cellMeta : openList) {
            double difficulty = cellMeta.pathTotalDifficulty()
                    + cellMeta.cell().getHeuristicDistance(mesh.getEndCell());
            if (difficulty < minV) {
                chosenCell = cellMeta;
                minV = difficulty;
            }
        }
        openList.remove(chosenCell);

        if (chosenCell.cell() == mesh.getEndCell()) {
            return false;
        }

        CellMeta finalChosenCell = chosenCell;
        chosenCell.cell().getAdjacentCells()
                .forEach(cell -> openList.add(new CellMeta(cell, finalChosenCell,
                        finalChosenCell.pathLength() +
                                cell.getCenter().getDistance(finalChosenCell.cell().getCenter()),
                        finalChosenCell.pathTotalDifficulty() +
                                cell.getCenter().getDistance(finalChosenCell.cell().getCenter())
                                        * field.getDifficulty(cell.getCenter()))));
        return true;
    }

    private void createPath() {
        if (chosenCell == null)
            throw new RuntimeException("Path not found");

        path = new ArrayList<>();
        var chosenCellCopy = chosenCell;
        while (chosenCellCopy != null) {
            path.addFirst(chosenCellCopy.cell());
            chosenCellCopy = chosenCellCopy.previous();
        }
        pathDifficulty = chosenCell.pathLength();
    }

    @Override
    public List<Cell> getPath() {
        if (path == null)
            createPath();
        return path;
    }

    @Override
    public double getPathDifficulty() {
        if (path == null)
            createPath();
        return pathDifficulty;
    }

}
