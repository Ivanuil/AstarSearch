package edu.ivanuil;

import edu.ivanuil.field.Field;
import edu.ivanuil.field.HomogeneousField;
import edu.ivanuil.mesh.DynamicSquareMesh;
import edu.ivanuil.mesh.Mesh;
import edu.ivanuil.solver.BasicSolver;
import edu.ivanuil.solver.Solver;
import edu.ivanuil.util.Coordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicSquareMeshTest {

    @Test
    public void dynamicSquareMeshTest() {
        Mesh mesh = new DynamicSquareMesh(
                new Coordinates(0, 0),
                new Coordinates(10, -10),
                1);

        Field field = new HomogeneousField(1);

        Solver solver = new BasicSolver(mesh, field);
        solver.solve();

        var path = solver.getPath();
        assertEquals(11, path.size());
        assertEquals(mesh.getStartCell(), path.getFirst());
        assertEquals(mesh.getEndCell(), path.getLast());
    }

}
