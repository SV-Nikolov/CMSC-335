/**
 * File: ShapeDisplayPanel.java
 * Author: Stefan Nikolov
 * Date: April 5, 2026
 * Purpose: Custom Swing panel to display selected shapes as 2D drawings/projections
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class ShapeDisplayPanel extends JPanel {
    private String shapeName;
    private double parameterOne;
    private double parameterTwo;

    public ShapeDisplayPanel() {
        this.shapeName = "";
        this.parameterOne = 1.0;
        this.parameterTwo = 1.0;
        setPreferredSize(new Dimension(520, 360));
        setBackground(Color.WHITE);
    }

    public void setShapeToDisplay(String shapeName, double parameterOne, double parameterTwo) {
        this.shapeName = shapeName;
        this.parameterOne = parameterOne;
        this.parameterTwo = parameterTwo;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2d = (Graphics2D) graphics.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(2.0f));

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int centerX = panelWidth / 2;
        int centerY = panelHeight / 2;

        if (shapeName == null || shapeName.isEmpty()) {
            g2d.setColor(Color.DARK_GRAY);
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 18));
            g2d.drawString("Select a shape and click Draw Shape", 110, centerY);
            g2d.dispose();
            return;
        }

        double scale = Math.min(panelWidth, panelHeight) / 18.0;
        int sizeOne = Math.max(20, (int) Math.round(parameterOne * scale));
        int sizeTwo = Math.max(20, (int) Math.round(parameterTwo * scale));

        switch (shapeName) {
            case "Circle":
                drawCircle(g2d, centerX, centerY, sizeOne);
                break;
            case "Rectangle":
                drawRectangle(g2d, centerX, centerY, sizeOne, sizeTwo);
                break;
            case "Square":
                drawSquare(g2d, centerX, centerY, sizeOne);
                break;
            case "Triangle":
                drawTriangle(g2d, centerX, centerY, sizeOne, sizeTwo);
                break;
            case "Sphere":
                drawSphere(g2d, centerX, centerY, sizeOne);
                break;
            case "Cube":
                drawCube(g2d, centerX, centerY, sizeOne);
                break;
            case "Cone":
                drawCone(g2d, centerX, centerY, sizeOne, sizeTwo);
                break;
            case "Cylinder":
                drawCylinder(g2d, centerX, centerY, sizeOne, sizeTwo);
                break;
            case "Torus":
                drawTorus(g2d, centerX, centerY, sizeOne, sizeTwo);
                break;
            default:
                break;
        }

        g2d.dispose();
    }

    private void drawCircle(Graphics2D g2d, int centerX, int centerY, int diameter) {
        g2d.setColor(new Color(45, 125, 210));
        g2d.fillOval(centerX - diameter / 2, centerY - diameter / 2, diameter, diameter);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(centerX - diameter / 2, centerY - diameter / 2, diameter, diameter);
    }

    private void drawRectangle(Graphics2D g2d, int centerX, int centerY, int width, int height) {
        g2d.setColor(new Color(52, 168, 83));
        g2d.fillRect(centerX - width / 2, centerY - height / 2, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(centerX - width / 2, centerY - height / 2, width, height);
    }

    private void drawSquare(Graphics2D g2d, int centerX, int centerY, int side) {
        g2d.setColor(new Color(244, 160, 0));
        g2d.fillRect(centerX - side / 2, centerY - side / 2, side, side);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(centerX - side / 2, centerY - side / 2, side, side);
    }

    private void drawTriangle(Graphics2D g2d, int centerX, int centerY, int base, int height) {
        int x1 = centerX;
        int y1 = centerY - height / 2;
        int x2 = centerX - base / 2;
        int y2 = centerY + height / 2;
        int x3 = centerX + base / 2;
        int y3 = centerY + height / 2;

        g2d.setColor(new Color(171, 71, 188));
        g2d.fillPolygon(new int[] {x1, x2, x3}, new int[] {y1, y2, y3}, 3);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(new int[] {x1, x2, x3}, new int[] {y1, y2, y3}, 3);
    }

    private void drawSphere(Graphics2D g2d, int centerX, int centerY, int diameter) {
        g2d.setColor(new Color(66, 133, 244));
        g2d.fillOval(centerX - diameter / 2, centerY - diameter / 2, diameter, diameter);
        g2d.setColor(new Color(227, 242, 253));
        g2d.fillOval(centerX - diameter / 3, centerY - diameter / 3, diameter / 3, diameter / 3);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(centerX - diameter / 2, centerY - diameter / 2, diameter, diameter);
    }

    private void drawCube(Graphics2D g2d, int centerX, int centerY, int side) {
        int offset = Math.max(8, side / 5);
        int x = centerX - side / 2;
        int y = centerY - side / 2;

        g2d.setColor(new Color(100, 181, 246));
        g2d.fillRect(x, y, side, side);
        g2d.setColor(new Color(66, 165, 245));
        g2d.fillRect(x + offset, y - offset, side, side);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, side, side);
        g2d.drawRect(x + offset, y - offset, side, side);
        g2d.drawLine(x, y, x + offset, y - offset);
        g2d.drawLine(x + side, y, x + side + offset, y - offset);
        g2d.drawLine(x, y + side, x + offset, y + side - offset);
        g2d.drawLine(x + side, y + side, x + side + offset, y + side - offset);
    }

    private void drawCone(Graphics2D g2d, int centerX, int centerY, int radius, int height) {
        int topX = centerX;
        int topY = centerY - height / 2;
        int baseY = centerY + height / 2;

        g2d.setColor(new Color(255, 183, 77));
        g2d.fillPolygon(new int[] {topX, centerX - radius, centerX + radius}, new int[] {topY, baseY, baseY}, 3);
        g2d.setColor(new Color(255, 224, 178));
        g2d.fillOval(centerX - radius, baseY - radius / 4, radius * 2, radius / 2);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(topX, topY, centerX - radius, baseY);
        g2d.drawLine(topX, topY, centerX + radius, baseY);
        g2d.drawOval(centerX - radius, baseY - radius / 4, radius * 2, radius / 2);
    }

    private void drawCylinder(Graphics2D g2d, int centerX, int centerY, int radius, int height) {
        int topY = centerY - height / 2;
        int bottomY = centerY + height / 2;

        g2d.setColor(new Color(129, 199, 132));
        g2d.fillRect(centerX - radius, topY, radius * 2, height);
        g2d.setColor(new Color(200, 230, 201));
        g2d.fillOval(centerX - radius, topY - radius / 4, radius * 2, radius / 2);
        g2d.fillOval(centerX - radius, bottomY - radius / 4, radius * 2, radius / 2);

        g2d.setColor(Color.BLACK);
        g2d.drawOval(centerX - radius, topY - radius / 4, radius * 2, radius / 2);
        g2d.drawLine(centerX - radius, topY, centerX - radius, bottomY);
        g2d.drawLine(centerX + radius, topY, centerX + radius, bottomY);
        g2d.drawOval(centerX - radius, bottomY - radius / 4, radius * 2, radius / 2);
    }

    private void drawTorus(Graphics2D g2d, int centerX, int centerY, int majorRadius, int minorRadius) {
        int outer = majorRadius + minorRadius;
        int inner = Math.max(10, majorRadius - minorRadius);

        g2d.setColor(new Color(239, 154, 154));
        g2d.fillOval(centerX - outer, centerY - outer / 2, outer * 2, outer);
        g2d.setColor(Color.WHITE);
        g2d.fillOval(centerX - inner, centerY - inner / 2, inner * 2, inner);

        g2d.setColor(Color.BLACK);
        g2d.drawOval(centerX - outer, centerY - outer / 2, outer * 2, outer);
        g2d.drawOval(centerX - inner, centerY - inner / 2, inner * 2, inner);
    }
}
