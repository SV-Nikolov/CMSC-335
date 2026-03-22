# Developer Guide

## Project Structure
- Shape.java
- TwoDimensionalShape.java
- ThreeDimensionalShape.java
- Circle.java, Rectangle.java, Square.java, Triangle.java
- Sphere.java, Cube.java, Cone.java, Cylinder.java, Torus.java
- ShapesProgram.java

## Compile
```bash
javac *.java
```

## Run
```bash
java ShapesProgram
```

## Program Behavior
- Menu options: Circle, Rectangle, Square, Triangle, Sphere, Cube, Cone, Cylinder, Torus, Exit
- 2D shapes compute area
- 3D shapes compute volume
- Invalid input is handled and user is reprompted
- Exit prints thank-you message with date/time

## Assumptions
- Triangle area uses base and height
- Torus uses major radius and minor radius, with major > minor
- All dimensions must be positive
