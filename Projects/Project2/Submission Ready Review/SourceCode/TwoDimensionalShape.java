/**
 * File: TwoDimensionalShape.java
 * Author: Stefan Nikolov
 * Date: March 23, 2026
 * Purpose: Abstract class for two-dimensional shapes
 */

public abstract class TwoDimensionalShape extends Shape {
    protected double area;
    
    /**
     * Constructor for TwoDimensionalShape
     */
    public TwoDimensionalShape() {
        super(2);
        this.area = 0.0;
    }
    
    /**
     * Get the area of the 2D shape
     * @return the area
     */
    public double getArea() {
        return area;
    }
    
    /**
     * Abstract method that subclasses must implement to calculate area
     */
    public abstract void calculateArea();
}
