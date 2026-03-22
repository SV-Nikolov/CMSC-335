/**
 * File: Torus.java
 * Author: Stefan Nikolov
 * Date: March 23, 2026
 * Purpose: Torus class that represents a 3D torus (donut shape)
 * Assumption: Torus volume is calculated using majorRadius and minorRadius
 */

public class Torus extends ThreeDimensionalShape {
    private double majorRadius;
    private double minorRadius;
    
    /**
     * Constructor for Torus
     * @param majorRadius the distance from center of torus to center of tube (must be positive)
     * @param minorRadius the radius of the tube (must be positive)
     */
    public Torus(double majorRadius, double minorRadius) {
        super();
        if (majorRadius <= 0 || minorRadius <= 0) {
            throw new IllegalArgumentException("Major and minor radii must be positive");
        }
        this.majorRadius = majorRadius;
        this.minorRadius = minorRadius;
        calculateVolume();
    }
    
    /**
     * Calculate the volume of the torus using formula: V = (π * r²) * (2 * π * R)
     * where r is minor radius and R is major radius
     */
    @Override
    public void calculateVolume() {
        volume = (Math.PI * minorRadius * minorRadius) * (2 * Math.PI * majorRadius);
    }
    
    /**
     * Get the description of the torus
     * @return description string
     */
    @Override
    public String getDescription() {
        return String.format("Torus with major radius %.2f and minor radius %.2f", majorRadius, minorRadius);
    }
}
