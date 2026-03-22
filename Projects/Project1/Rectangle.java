/**
 * File: Rectangle.java
 * Author: Stefan Nikolov
 * Date: March 23, 2026
 * Purpose: Rectangle class that represents a 2D rectangle
 */

public class Rectangle extends TwoDimensionalShape {
    private double length;
    private double width;
    
    /**
     * Constructor for Rectangle
     * @param length the length of the rectangle (must be positive)
     * @param width the width of the rectangle (must be positive)
     */
    public Rectangle(double length, double width) {
        super();
        if (length <= 0 || width <= 0) {
            throw new IllegalArgumentException("Length and width must be positive");
        }
        this.length = length;
        this.width = width;
        calculateArea();
    }
    
    /**
     * Calculate the area of the rectangle using formula: A = length * width
     */
    @Override
    public void calculateArea() {
        area = length * width;
    }
    
    /**
     * Get the description of the rectangle
     * @return description string
     */
    @Override
    public String getDescription() {
        return String.format("Rectangle with length %.2f and width %.2f", length, width);
    }
}
