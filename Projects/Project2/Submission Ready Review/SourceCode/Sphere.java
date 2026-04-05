/**
 * File: Sphere.java
 * Author: Stefan Nikolov
 * Date: March 23, 2026
 * Purpose: Sphere class that represents a 3D sphere
 */

public class Sphere extends ThreeDimensionalShape {
    private double radius;
    
    /**
     * Constructor for Sphere
     * @param radius the radius of the sphere (must be positive)
     */
    public Sphere(double radius) {
        super();
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        this.radius = radius;
        calculateVolume();
    }
    
    /**
     * Calculate the volume of the sphere using formula: V = (4/3) * π * r³
     */
    @Override
    public void calculateVolume() {
        volume = (4.0 / 3.0) * Math.PI * radius * radius * radius;
    }
    
    /**
     * Get the description of the sphere
     * @return description string
     */
    @Override
    public String getDescription() {
        return String.format("Sphere with radius %.2f", radius);
    }
}
