/**
 * File: Cube.java
 * Author: Stefan Nikolov
 * Date: March 23, 2026
 * Purpose: Cube class that represents a 3D cube
 */

public class Cube extends ThreeDimensionalShape {
    private double side;
    
    /**
     * Constructor for Cube
     * @param side the length of each side of the cube (must be positive)
     */
    public Cube(double side) {
        super();
        if (side <= 0) {
            throw new IllegalArgumentException("Side length must be positive");
        }
        this.side = side;
        calculateVolume();
    }
    
    /**
     * Calculate the volume of the cube using formula: V = side³
     */
    @Override
    public void calculateVolume() {
        volume = side * side * side;
    }
    
    /**
     * Get the description of the cube
     * @return description string
     */
    @Override
    public String getDescription() {
        return String.format("Cube with side length %.2f", side);
    }
}
