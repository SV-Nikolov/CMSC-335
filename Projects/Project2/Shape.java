/**
 * File: Shape.java
 * Author: Stefan Nikolov
 * Date: March 23, 2026
 * Purpose: Base abstract class for all shapes in the OO Shapes program
 */

public abstract class Shape {
    protected int dimensions;
    
    /**
     * Constructor for Shape
     * @param dimensions the number of dimensions
     */
    public Shape(int dimensions) {
        this.dimensions = dimensions;
    }
    
    /**
     * Get the number of dimensions for this shape
     * @return the number of dimensions
     */
    public int getDimensions() {
        return dimensions;
    }
    
    /**
     * Abstract method that subclasses must implement
     * @return description of the shape
     */
    public abstract String getDescription();
}
