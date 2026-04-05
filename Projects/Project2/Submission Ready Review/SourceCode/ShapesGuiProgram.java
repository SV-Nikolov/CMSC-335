/**
 * File: ShapesGuiProgram.java
 * Author: Stefan Nikolov
 * Date: April 5, 2026
 * Purpose: Swing GUI application for constructing shapes and displaying area/volume with event handlers
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;

public class ShapesGuiProgram extends JFrame {
    private static final String[] SHAPE_NAMES = {
        "Circle", "Rectangle", "Square", "Triangle",
        "Sphere", "Cube", "Cone", "Cylinder", "Torus"
    };

    private static final Double[] DEFAULT_DIMENSION_CHOICES = {
        1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 12.0, 15.0
    };

    private JComboBox<String> shapeSelector;
    private JLabel firstParameterLabel;
    private JLabel secondParameterLabel;
    private JComboBox<Double> firstParameterDropdown;
    private JComboBox<Double> secondParameterDropdown;
    private JComboBox<String> unitSelector;
    private JLabel resultLabel;
    private ShapeDisplayPanel shapeDisplayPanel;

    public ShapesGuiProgram() {
        super("CMSC 335 Project 2 - Shape Visualizer");
        initializeUi();
    }

    private void initializeUi() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));

        JPanel topPanel = createTopPanel();
        JPanel leftPanel = createControlPanel();
        shapeDisplayPanel = new ShapeDisplayPanel();

        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(shapeDisplayPanel, BorderLayout.CENTER);

        updateParameterLabels((String) shapeSelector.getSelectedItem());

        setPreferredSize(new Dimension(920, 500));
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));

        JLabel title = new JLabel("Java Swing Shape Explorer (Project 2)");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        topPanel.add(title);

        return topPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(8, 8, 8, 0),
            BorderFactory.createTitledBorder("Shape Controls")
        ));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel shapeLabel = new JLabel("Select Shape:");
        shapeSelector = new JComboBox<String>(SHAPE_NAMES);
        shapeSelector.addActionListener(event -> updateParameterLabels((String) shapeSelector.getSelectedItem()));

        JLabel unitLabel = new JLabel("Unit:");
        unitSelector = new JComboBox<String>(new String[] {"cm", "m", "in"});

        firstParameterLabel = new JLabel("Parameter 1:");
        secondParameterLabel = new JLabel("Parameter 2:");

        firstParameterDropdown = new JComboBox<Double>(DEFAULT_DIMENSION_CHOICES);
        secondParameterDropdown = new JComboBox<Double>(DEFAULT_DIMENSION_CHOICES);

        JButton drawButton = new JButton("Draw Shape");
        drawButton.addActionListener(event -> buildAndRenderShape());

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(event -> clearDisplay());

        JButton resetButton = new JButton("Reset Defaults");
        resetButton.addActionListener(event -> resetDefaults());

        resultLabel = new JLabel("Result: Select a shape and click Draw Shape");

        constraints.gridx = 0;
        constraints.gridy = 0;
        controlPanel.add(shapeLabel, constraints);

        constraints.gridy = 1;
        controlPanel.add(shapeSelector, constraints);

        constraints.gridy = 2;
        controlPanel.add(unitLabel, constraints);

        constraints.gridy = 3;
        controlPanel.add(unitSelector, constraints);

        constraints.gridy = 4;
        controlPanel.add(firstParameterLabel, constraints);

        constraints.gridy = 5;
        controlPanel.add(firstParameterDropdown, constraints);

        constraints.gridy = 6;
        controlPanel.add(secondParameterLabel, constraints);

        constraints.gridy = 7;
        controlPanel.add(secondParameterDropdown, constraints);

        constraints.gridy = 8;
        controlPanel.add(drawButton, constraints);

        constraints.gridy = 9;
        controlPanel.add(clearButton, constraints);

        constraints.gridy = 10;
        controlPanel.add(resetButton, constraints);

        constraints.gridy = 11;
        controlPanel.add(resultLabel, constraints);

        return controlPanel;
    }

    private void updateParameterLabels(String shapeName) {
        if (shapeName == null) {
            return;
        }

        switch (shapeName) {
            case "Circle":
                firstParameterLabel.setText("Radius:");
                secondParameterLabel.setVisible(false);
                secondParameterDropdown.setVisible(false);
                secondParameterDropdown.setEnabled(false);
                break;
            case "Rectangle":
                firstParameterLabel.setText("Length:");
                secondParameterLabel.setText("Width:");
                secondParameterLabel.setVisible(true);
                secondParameterDropdown.setVisible(true);
                secondParameterDropdown.setEnabled(true);
                break;
            case "Square":
                firstParameterLabel.setText("Side Length:");
                secondParameterLabel.setVisible(false);
                secondParameterDropdown.setVisible(false);
                secondParameterDropdown.setEnabled(false);
                break;
            case "Triangle":
                firstParameterLabel.setText("Base:");
                secondParameterLabel.setText("Height:");
                secondParameterLabel.setVisible(true);
                secondParameterDropdown.setVisible(true);
                secondParameterDropdown.setEnabled(true);
                break;
            case "Sphere":
                firstParameterLabel.setText("Radius:");
                secondParameterLabel.setVisible(false);
                secondParameterDropdown.setVisible(false);
                secondParameterDropdown.setEnabled(false);
                break;
            case "Cube":
                firstParameterLabel.setText("Side Length:");
                secondParameterLabel.setVisible(false);
                secondParameterDropdown.setVisible(false);
                secondParameterDropdown.setEnabled(false);
                break;
            case "Cone":
                firstParameterLabel.setText("Radius:");
                secondParameterLabel.setText("Height:");
                secondParameterLabel.setVisible(true);
                secondParameterDropdown.setVisible(true);
                secondParameterDropdown.setEnabled(true);
                break;
            case "Cylinder":
                firstParameterLabel.setText("Radius:");
                secondParameterLabel.setText("Height:");
                secondParameterLabel.setVisible(true);
                secondParameterDropdown.setVisible(true);
                secondParameterDropdown.setEnabled(true);
                break;
            case "Torus":
                firstParameterLabel.setText("Major Radius:");
                secondParameterLabel.setText("Minor Radius:");
                secondParameterLabel.setVisible(true);
                secondParameterDropdown.setVisible(true);
                secondParameterDropdown.setEnabled(true);
                break;
            default:
                break;
        }

        revalidate();
        repaint();
    }

    private void buildAndRenderShape() {
        String selectedShape = (String) shapeSelector.getSelectedItem();
        if (selectedShape == null) {
            return;
        }

        double firstValue = ((Double) firstParameterDropdown.getSelectedItem()).doubleValue();
        Double secondSelection = (Double) secondParameterDropdown.getSelectedItem();
        double secondValue = secondSelection == null ? 1.0 : secondSelection.doubleValue();

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String unit = (String) unitSelector.getSelectedItem();

        try {
            switch (selectedShape) {
                case "Circle": {
                    Circle circle = new Circle(firstValue);
                    shapeDisplayPanel.setShapeToDisplay(selectedShape, firstValue, 1.0);
                    resultLabel.setText("Result: " + circle.getDescription() + " | Area = "
                        + decimalFormat.format(circle.getArea()) + " " + unit + "^2");
                    break;
                }
                case "Rectangle": {
                    Rectangle rectangle = new Rectangle(firstValue, secondValue);
                    shapeDisplayPanel.setShapeToDisplay(selectedShape, firstValue, secondValue);
                    resultLabel.setText("Result: " + rectangle.getDescription() + " | Area = "
                        + decimalFormat.format(rectangle.getArea()) + " " + unit + "^2");
                    break;
                }
                case "Square": {
                    Square square = new Square(firstValue);
                    shapeDisplayPanel.setShapeToDisplay(selectedShape, firstValue, 1.0);
                    resultLabel.setText("Result: " + square.getDescription() + " | Area = "
                        + decimalFormat.format(square.getArea()) + " " + unit + "^2");
                    break;
                }
                case "Triangle": {
                    Triangle triangle = new Triangle(firstValue, secondValue);
                    shapeDisplayPanel.setShapeToDisplay(selectedShape, firstValue, secondValue);
                    resultLabel.setText("Result: " + triangle.getDescription() + " | Area = "
                        + decimalFormat.format(triangle.getArea()) + " " + unit + "^2");
                    break;
                }
                case "Sphere": {
                    Sphere sphere = new Sphere(firstValue);
                    shapeDisplayPanel.setShapeToDisplay(selectedShape, firstValue, 1.0);
                    resultLabel.setText("Result: " + sphere.getDescription() + " | Volume = "
                        + decimalFormat.format(sphere.getVolume()) + " " + unit + "^3");
                    break;
                }
                case "Cube": {
                    Cube cube = new Cube(firstValue);
                    shapeDisplayPanel.setShapeToDisplay(selectedShape, firstValue, 1.0);
                    resultLabel.setText("Result: " + cube.getDescription() + " | Volume = "
                        + decimalFormat.format(cube.getVolume()) + " " + unit + "^3");
                    break;
                }
                case "Cone": {
                    Cone cone = new Cone(firstValue, secondValue);
                    shapeDisplayPanel.setShapeToDisplay(selectedShape, firstValue, secondValue);
                    resultLabel.setText("Result: " + cone.getDescription() + " | Volume = "
                        + decimalFormat.format(cone.getVolume()) + " " + unit + "^3");
                    break;
                }
                case "Cylinder": {
                    Cylinder cylinder = new Cylinder(firstValue, secondValue);
                    shapeDisplayPanel.setShapeToDisplay(selectedShape, firstValue, secondValue);
                    resultLabel.setText("Result: " + cylinder.getDescription() + " | Volume = "
                        + decimalFormat.format(cylinder.getVolume()) + " " + unit + "^3");
                    break;
                }
                case "Torus": {
                    if (firstValue <= secondValue) {
                        throw new IllegalArgumentException("Major radius must be greater than minor radius.");
                    }
                    Torus torus = new Torus(firstValue, secondValue);
                    shapeDisplayPanel.setShapeToDisplay(selectedShape, firstValue, secondValue);
                    resultLabel.setText("Result: " + torus.getDescription() + " | Volume = "
                        + decimalFormat.format(torus.getVolume()) + " " + unit + "^3");
                    break;
                }
                default:
                    break;
            }
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(
                this,
                exception.getMessage(),
                "Invalid Shape Parameters",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearDisplay() {
        shapeDisplayPanel.setShapeToDisplay("", 1.0, 1.0);
        resultLabel.setText("Result: Select a shape and click Draw Shape");
    }

    private void resetDefaults() {
        shapeSelector.setSelectedIndex(0);
        unitSelector.setSelectedItem("cm");
        firstParameterDropdown.setSelectedItem(1.0);
        secondParameterDropdown.setSelectedItem(1.0);
        clearDisplay();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception exception) {
                // Continue with default look and feel if unavailable.
            }
            ShapesGuiProgram gui = new ShapesGuiProgram();
            gui.setVisible(true);
        });
    }
}
