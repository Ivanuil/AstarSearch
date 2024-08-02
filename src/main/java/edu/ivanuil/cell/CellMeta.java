package edu.ivanuil.cell;

public record CellMeta(
        Cell cell,
        CellMeta previous,
        double pathLength,
        double pathTotalDifficulty) {

    @Override
    public String toString() {
        return cell.toString();
    }

}
