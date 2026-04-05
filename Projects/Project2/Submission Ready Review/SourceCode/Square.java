/**
 * File: Square.java
 * Author: Stefan Nikolov
 * Date: March 23, 2026
 * Purpose: Square class that represents a 2D square
 */

public class Square extends TwoDimensionalShape {
    private double side;
    
    /**
     * Constructor for Square
     * @param side the length of each side of the square (must be positive)
     */
    public Square(double side) {
        super();
        if (side <= 0) {
            throw new IllegalArgumentException("Side length must be positive");
        }
        this.side = side;
        calculateArea();
    }
    
    /**
     * Calculate the area of the square using formula: A = side²
     */
    @Override
    public void calculateArea() {
        area = side * side;
    }
    
    /**
     * Get the description of the square
     * @return description string
     */
    @Override
    public String getDescription() {
        return String.format("Square with side length %.2f", side);
    }
}
