package edu.ivanuil;

import edu.ivanuil.cell.Cell;
import edu.ivanuil.field.Field;
import edu.ivanuil.field.HomogeneousField;
import edu.ivanuil.mesh.DynamicSquareMesh;
import edu.ivanuil.mesh.Mesh;
import edu.ivanuil.solver.BasicSolver;
import edu.ivanuil.solver.Solver;
import edu.ivanuil.util.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class App {

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 800);

//        Mesh mesh = new StaticSquareMesh(
//                new Coordinates(15, 15),
//                new Coordinates(105, 105),
//                new Coordinates(10, 10),
//                new Coordinates(110, 110),
//                1);

        Mesh mesh = new DynamicSquareMesh(
                new Coordinates(45, 15),
                new Coordinates(105, 105),
                10);

        Field field = new HomogeneousField(1); // new PuncturedField(1);
//        field.addHole(new Rectangle(
//                new Coordinates(40, 40),
//                new Coordinates(60, 60)
//        ));

        Solver solver = new BasicSolver(mesh, field);

        Canvas canvas = new Canvas(6, mesh.getCells(), List.of());
        mainFrame.add(canvas);
        mainFrame.setVisible(true);

        solver.solve();
        canvas.setPath(solver.getPath());
        canvas.setCells(mesh.getCells());
        canvas.repaint();
    }

    @AllArgsConstructor
    private static class Canvas extends JPanel {

        private final double scale;

        private final int topOffset = 50;
        private final int leftOffset = 50;

        @Setter
        private Set<Cell> cells;
        @Setter
        private List<Cell> path;

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(800, 800);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponents(g);

            cells.forEach(cell -> {
                List<Coordinates> vertices = cell.getVertices();
                for (int i = 0; i < vertices.size() - 1; i++) {
                    drawLine(vertices.get(i), vertices.get(i + 1), g);
                }
                drawLine(vertices.getLast(), vertices.getFirst(), g);
            });

            for (Cell cell : path) {
                g.drawOval(
                        convertCoordinates(cell.getCenter().longitude(), leftOffset),
                        convertCoordinates(cell.getCenter().longitude(), topOffset),
                        10, 10);
            }
        }

        private void drawLine(Coordinates start, Coordinates end, Graphics g) {
            g.drawLine(convertCoordinates(start.longitude(), leftOffset),
                    convertCoordinates(start.latitude(), topOffset),
                    convertCoordinates(end.longitude(), leftOffset),
                    convertCoordinates(end.latitude(), topOffset));
        }

        private int convertCoordinates(double coordinate, int offset) {
            return (int) (coordinate * scale) + offset;
        }

    }

}
