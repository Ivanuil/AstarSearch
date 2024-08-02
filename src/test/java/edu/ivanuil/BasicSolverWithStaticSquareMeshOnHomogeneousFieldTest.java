package edu.ivanuil;

import edu.ivanuil.field.Field;
import edu.ivanuil.field.HomogeneousField;
import edu.ivanuil.mesh.Mesh;
import edu.ivanuil.mesh.StaticSquareMesh;
import edu.ivanuil.solver.BasicSolver;
import edu.ivanuil.solver.Solver;
import edu.ivanuil.util.Coordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicSolverWithStaticSquareMeshOnHomogeneousFieldTest {

    @Test
    public void basicSolverWithSquareMeshOnHomogeneousFieldTest() {
        Mesh mesh = new StaticSquareMesh(
                new Coordinates(5, 5),
                new Coordinates(95, 95),
                new Coordinates(0, 0),
                new Coordinates(100, 100),
                10);
        Field field = new HomogeneousField(1);
        Solver solver = new BasicSolver(mesh, field);
        solver.solve();
        var path = solver.getPath();

        assertEquals(10, path.size());
        for (int i = 0; i < path.size(); i++) {
            assertEquals(i * 10, path.get(i).getCenter().longitude(), 0.001);
            assertEquals(i * 10, path.get(i).getCenter().latitude(), 0.001);
        }
    }

}
