package edu.ivanuil;

import edu.ivanuil.cell.Cell;
import edu.ivanuil.field.Field;
import edu.ivanuil.field.PuncturedField;
import edu.ivanuil.heuristic_distance.HeuristicDistance;
import edu.ivanuil.heuristic_distance.PythagoreanDistance;
import edu.ivanuil.mesh.DynamicSquareMesh;
import edu.ivanuil.mesh.Mesh;
import edu.ivanuil.solver.BasicSolver;
import edu.ivanuil.solver.Solver;
import edu.ivanuil.util.Coordinates;
import edu.ivanuil.util.Rectangle;
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

        Mesh mesh = new DynamicSquareMesh(
                new Coordinates(0, 0),
                new Coordinates(100, 100),
                10);

        PuncturedField field = new PuncturedField(1)
                .addHole(new Rectangle(
                new Coordinates(30, 30),
                new Coordinates(70, 70)));

        HeuristicDistance heuristicDistance = new PythagoreanDistance();

        Solver solver = new BasicSolver(mesh, field, heuristicDistance);

        Canvas canvas = new Canvas(6, mesh.getCells(), List.of(), field.getHoles());
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
        @Setter
        private Set<Rectangle> holes;

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

            Cell start = path.getFirst();
            g.drawOval(
                    convertCoordinates(start.getCenter().longitude(), leftOffset - 5),
                    convertCoordinates(start.getCenter().latitude(), topOffset - 5),
                    10, 10);
            for (int i = 0; i < path.size() - 1; i++) {
                Cell first = path.get(i);
                Cell second = path.get(i + 1);
                g.drawLine(
                        convertCoordinates(first.getCenter().longitude(), leftOffset),
                        convertCoordinates(first.getCenter().latitude(), topOffset),
                        convertCoordinates(second.getCenter().longitude(), leftOffset),
                        convertCoordinates(second.getCenter().latitude(), topOffset));
            }
            Cell end = path.getLast();
            g.drawOval(
                    convertCoordinates(end.getCenter().longitude(), leftOffset - 5),
                    convertCoordinates(end.getCenter().latitude(), topOffset - 5),
                    10, 10);

            for (Rectangle hole : holes) {
                g.drawRect(
                        convertCoordinates(hole.leftBottom().longitude(), leftOffset),
                        convertCoordinates(hole.leftBottom().latitude(), topOffset),
                        convertCoordinates(hole.rightTop().longitude() - hole.leftBottom().longitude(), 0),
                        convertCoordinates(hole.rightTop().latitude() - hole.leftBottom().latitude(), 0));
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
