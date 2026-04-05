/**
 * File: Triangle.java
 * Author: Stefan Nikolov
 * Date: March 23, 2026
 * Purpose: Triangle class that represents a 2D triangle
 * Assumption: Triangle area is calculated using base * height / 2
 */

public class Triangle extends TwoDimensionalShape {
    private double base;
    private double height;
    
    /**
     * Constructor for Triangle
     * @param base the base of the triangle (must be positive)
     * @param height the height of the triangle (must be positive)
     */
    public Triangle(double base, double height) {
        super();
        if (base <= 0 || height <= 0) {
            throw new IllegalArgumentException("Base and height must be positive");
        }
        this.base = base;
        this.height = height;
        calculateArea();
    }
    
    /**
     * Calculate the area of the triangle using formula: A = (base * height) / 2
     */
    @Override
    public void calculateArea() {
        area = (base * height) / 2.0;
    }
    
    /**
     * Get the description of the triangle
     * @return description string
     */
    @Override
    public String getDescription() {
        return String.format("Triangle with base %.2f and height %.2f", base, height);
    }
}
