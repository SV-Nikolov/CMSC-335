/**
 * File: Cone.java
 * Author: Stefan Nikolov
 * Date: March 23, 2026
 * Purpose: Cone class that represents a 3D cone
 */

public class Cone extends ThreeDimensionalShape {
    private double radius;
    private double height;
    
    /**
     * Constructor for Cone
     * @param radius the radius of the cone's base (must be positive)
     * @param height the height of the cone (must be positive)
     */
    public Cone(double radius, double height) {
        super();
        if (radius <= 0 || height <= 0) {
            throw new IllegalArgumentException("Radius and height must be positive");
        }
        this.radius = radius;
        this.height = height;
        calculateVolume();
    }
    
    /**
     * Calculate the volume of the cone using formula: V = (1/3) * π * r² * h
     */
    @Override
    public void calculateVolume() {
        volume = (1.0 / 3.0) * Math.PI * radius * radius * height;
    }
    
    /**
     * Get the description of the cone
     * @return description string
     */
    @Override
    public String getDescription() {
        return String.format("Cone with radius %.2f and height %.2f", radius, height);
    }
}
