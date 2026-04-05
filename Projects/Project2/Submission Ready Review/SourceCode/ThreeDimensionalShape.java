/**
 * File: ThreeDimensionalShape.java
 * Author: Stefan Nikolov
 * Date: March 23, 2026
 * Purpose: Abstract class for three-dimensional shapes
 */

public abstract class ThreeDimensionalShape extends Shape {
    protected double volume;
    
    /**
     * Constructor for ThreeDimensionalShape
     */
    public ThreeDimensionalShape() {
        super(3);
        this.volume = 0.0;
    }
    
    /**
     * Get the volume of the 3D shape
     * @return the volume
     */
    public double getVolume() {
        return volume;
    }
    
    /**
     * Abstract method that subclasses must implement to calculate volume
     */
    public abstract void calculateVolume();
}
