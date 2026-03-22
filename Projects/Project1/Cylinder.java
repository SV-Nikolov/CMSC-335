/**
 * File: Cylinder.java
 * Author: Stefan Nikolov
 * Date: March 23, 2026
 * Purpose: Cylinder class that represents a 3D cylinder
 */

public class Cylinder extends ThreeDimensionalShape {
    private double radius;
    private double height;
    
    /**
     * Constructor for Cylinder
     * @param radius the radius of the cylinder's base (must be positive)
     * @param height the height of the cylinder (must be positive)
     */
    public Cylinder(double radius, double height) {
        super();
        if (radius <= 0 || height <= 0) {
            throw new IllegalArgumentException("Radius and height must be positive");
        }
        this.radius = radius;
        this.height = height;
        calculateVolume();
    }
    
    /**
     * Calculate the volume of the cylinder using formula: V = π * r² * h
     */
    @Override
    public void calculateVolume() {
        volume = Math.PI * radius * radius * height;
    }
    
    /**
     * Get the description of the cylinder
     * @return description string
     */
    @Override
    public String getDescription() {
        return String.format("Cylinder with radius %.2f and height %.2f", radius, height);
    }
}
