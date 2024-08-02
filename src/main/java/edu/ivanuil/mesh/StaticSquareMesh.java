package edu.ivanuil.mesh;

import edu.ivanuil.cell.Cell;
import edu.ivanuil.cell.CellState;
import edu.ivanuil.cell.StaticSquareCell;
import edu.ivanuil.util.Coordinates;
import edu.ivanuil.util.TwoDimensionalIndex;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class StaticSquareMesh implements Mesh {

    private final Map<TwoDimensionalIndex, StaticSquareCell> cellMap = new HashMap<>();
    @Getter
    private final StaticSquareCell startCell;
    @Getter
    private final StaticSquareCell endCell;

    public StaticSquareMesh(Coordinates start, Coordinates end,
                            Coordinates leftBottom, Coordinates rightTop, double precision) {
        if (isOutOfSpace(start, leftBottom, rightTop) || isOutOfSpace(end, leftBottom, rightTop))
            throw new IllegalArgumentException("Start or End point not in space");

        StaticSquareCell[][] matrix = new StaticSquareCell
                [(int) ((rightTop.longitude() - leftBottom.longitude()) / precision)]
                [(int) ((rightTop.latitude() - leftBottom.latitude()) / precision)];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                double x = leftBottom.longitude() + i * precision;
                double y = leftBottom.latitude() + j * precision;
                StaticSquareCell cell = new StaticSquareCell(new Coordinates(x, y), precision);
                cellMap.put(new TwoDimensionalIndex(x, y), cell);
                matrix[i][j] = cell;
            }
        }
        log.info("Initialised cells matrix, number of cells: {}", cellMap.size());

        setNeighbors(matrix);
        log.info("Neighbours set");

        startCell = matrix[(int) ((start.longitude() - leftBottom.longitude()) / precision)][
                (int) ((start.latitude() - leftBottom.latitude()) / precision)];
        endCell = matrix[(int) ((end.longitude() - leftBottom.longitude()) / precision)][
                (int) ((end.latitude() - leftBottom.latitude()) / precision)];
        startCell.setCellState(CellState.START);
        startCell.setCellState(CellState.END);
        log.info("Cells containing start and end points found");
    }

    private static boolean isOutOfSpace(Coordinates point, Coordinates leftBottom, Coordinates rightTop) {
        if (point.longitude() < leftBottom.longitude() || point.latitude() < leftBottom.latitude())
            return true;
        if (point.longitude() > rightTop.longitude() || point.latitude() > rightTop.latitude())
            return true;
        return false;
    }

    private void setNeighbors(StaticSquareCell[][] matrix) {
        // Vertical
        for (int i = 0; i < matrix.length - 1; i++)
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j].addAdjacentCell(matrix[i + 1][j]);
                matrix[i + 1][j].addAdjacentCell(matrix[i][j]);
            }

        // Horizontal
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length - 1; j++) {
                matrix[i][j].addAdjacentCell(matrix[i][j + 1]);
                matrix[i][j + 1].addAdjacentCell(matrix[i][j]);
            }

        // Main diagonal
        for (int i = 0; i < matrix.length - 1; i++)
            for (int j = 0; j < matrix[i].length - 1; j++) {
                matrix[i][j].addAdjacentCell(matrix[i + 1][j + 1]);
                matrix[i + 1][j + 1].addAdjacentCell(matrix[i][j]);
            }

        // Side diagonal
        for (int i = 1; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length - 1; j++) {
                matrix[i][j].addAdjacentCell(matrix[i - 1][j + 1]);
                matrix[i - 1][j + 1].addAdjacentCell(matrix[i][j]);
            }

    }

    @Override
    public Set<Cell> getCells() {
        return Set.copyOf(cellMap.values());
    }

}
