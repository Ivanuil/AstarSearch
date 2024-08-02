package edu.ivanuil;

import edu.ivanuil.field.HomogeneousField;
import edu.ivanuil.mesh.DynamicSquareMesh;
import edu.ivanuil.solver.BasicSolver;
import edu.ivanuil.util.Coordinates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicSolverTest {

    @Test
    public void pathDistanceAlongLongitudeTest() {
        var mesh = new DynamicSquareMesh(
                new Coordinates(0, 0),
                new Coordinates(10, 0),
                1);
        var field = new HomogeneousField(1);
        var solver = new BasicSolver(mesh, field);

        solver.solve();

        Assertions.assertEquals(10, solver.getPathDifficulty());
    }

    @Test
    public void pathDistanceAlongLatitudeTest() {
        var mesh = new DynamicSquareMesh(
                new Coordinates(0, 0),
                new Coordinates(0, 10),
                1);
        var field = new HomogeneousField(1);
        var solver = new BasicSolver(mesh, field);

        solver.solve();

        Assertions.assertEquals(10, solver.getPathDifficulty());
    }

    @Test
    public void pathDistanceAlongDiagonalTest() {
        var mesh = new DynamicSquareMesh(
                new Coordinates(0, 0),
                new Coordinates(10, 10),
                1);
        var field = new HomogeneousField(1);
        var solver = new BasicSolver(mesh, field);

        solver.solve();

        Assertions.assertEquals(Math.sqrt(10 * 10 + 10 * 10), solver.getPathDifficulty(), 0.000001);
    }

    @Test
    public void pathDistanceRandomPointsTest() {
        double longitude = Math.random() * 100 + 10;
        double latitude = Math.random() * 100 + 10;
        var mesh = new DynamicSquareMesh(
                new Coordinates(0, 0),
                new Coordinates(longitude, latitude),
                1);
        var field = new HomogeneousField(1);
        var solver = new BasicSolver(mesh, field);

        solver.solve();

        double realLength = Math.sqrt(longitude * longitude + latitude * latitude);
        double precision = Math.ceil(Math.log10(realLength)) * 5;
        Assertions.assertEquals(realLength,
                solver.getPathDifficulty(), precision);
    }

}
