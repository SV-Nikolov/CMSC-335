/**
 * File: Circle.java
 * Author: Stefan Nikolov
 * Date: March 23, 2026
 * Purpose: Circle class that represents a 2D circle
 */

public class Circle extends TwoDimensionalShape {
    private double radius;
    
    /**
     * Constructor for Circle
     * @param radius the radius of the circle (must be positive)
     */
    public Circle(double radius) {
        super();
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        this.radius = radius;
        calculateArea();
    }
    
    /**
     * Calculate the area of the circle using formula: A = π * r²
     */
    @Override
    public void calculateArea() {
        area = Math.PI * radius * radius;
    }
    
    /**
     * Get the description of the circle
     * @return description string
     */
    @Override
    public String getDescription() {
        return String.format("Circle with radius %.2f", radius);
    }
}
