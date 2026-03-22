% CMSC 335 Project 1 - UML Class Diagram
% Author: Stefan Nikolov
% Date: March 23, 2026

# UML Class Diagram - Java OO Shapes Program

## Class Hierarchy Overview

```
                          Object (Java)
                              |
                          Shape (abstract)
                          - dimensions: int
                          + getDimensions(): int
                          + getDescription(): String
                              |
                ________________|_________________
               |                                  |
    TwoDimensionalShape (abstract)    ThreeDimensionalShape (abstract)
    - area: double                     - volume: double
    + getArea(): double                + getVolume(): double
    + calculateArea(): void            + calculateVolume(): void
       |                                  |
    ___|___________________         _____|_____________________
   |   |   |   |           |       |     |    |    |    |
  Circ Rect Squa Trian    |      Sph   Cube  Cone Cyl  Torus
  le   angle are   gle    |      ere        (abstract)
                          |
                      [Additional shapes]
```

## Detailed Class Specifications

### 1. Shape (Abstract Base Class)
**Purpose**: Defines common interface for all shapes

**Attributes**:
- `protected int dimensions`: Number of spatial dimensions

**Methods**:
- `Shape(int dimensions)`: Constructor
- `int getDimensions()`: Returns the number of dimensions
- `abstract String getDescription()`: Must be implemented by subclasses

**Relationships**: Parent class for TwoDimensionalShape and ThreeDimensionalShape

---

### 2. TwoDimensionalShape (Abstract Class)
**Purpose**: Represents all 2D shapes

**Extends**: Shape

**Attributes**:
- `protected double area`: Calculated area of the shape

**Methods**:
- `TwoDimensionalShape()`: Constructor (sets dimensions = 2)
- `double getArea()`: Returns the calculated area
- `abstract void calculateArea()`: Must be implemented by subclasses

**Concrete Subclasses**: Circle, Rectangle, Square, Triangle

---

### 3. ThreeDimensionalShape (Abstract Class)
**Purpose**: Represents all 3D shapes

**Extends**: Shape

**Attributes**:
- `protected double volume`: Calculated volume of the shape

**Methods**:
- `ThreeDimensionalShape()`: Constructor (sets dimensions = 3)
- `double getVolume()`: Returns the calculated volume
- `abstract void calculateVolume()`: Must be implemented by subclasses

**Concrete Subclasses**: Sphere, Cube, Cone, Cylinder, Torus

---

## 2D Shape Implementations

### Circle
- **Formula**: Area = π * r²
- **Parameters**: radius (double)
- **Constructor**: `Circle(double radius)`

### Rectangle
- **Formula**: Area = length * width
- **Parameters**: length, width (double)
- **Constructor**: `Rectangle(double length, double width)`

### Square
- **Formula**: Area = side²
- **Parameters**: side (double)
- **Constructor**: `Square(double side)`

### Triangle
- **Formula**: Area = (base * height) / 2
- **Parameters**: base, height (double)
- **Constructor**: `Triangle(double base, double height)`
- **Assumption**: Uses base-height formula for area calculation

---

## 3D Shape Implementations

### Sphere
- **Formula**: Volume = (4/3) * π * r³
- **Parameters**: radius (double)
- **Constructor**: `Sphere(double radius)`

### Cube
- **Formula**: Volume = side³
- **Parameters**: side (double)
- **Constructor**: `Cube(double side)`

### Cone
- **Formula**: Volume = (1/3) * π * r² * h
- **Parameters**: radius, height (double)
- **Constructor**: `Cone(double radius, double height)`

### Cylinder
- **Formula**: Volume = π * r² * h
- **Parameters**: radius, height (double)
- **Constructor**: `Cylinder(double radius, double height)`

### Torus
- **Formula**: Volume = (π * r²) * (2 * π * R)
  - where r = minor radius, R = major radius
- **Parameters**: majorRadius, minorRadius (double)
- **Constructor**: `Torus(double majorRadius, double minorRadius)`
- **Assumption**: Major radius > Minor radius for valid torus

---

## Relationship Types

### Inheritance (IS-A)
- Shape is the parent of both TwoDimensionalShape and ThreeDimensionalShape
- Circle, Rectangle, Square, Triangle are types of TwoDimensionalShape
- Sphere, Cube, Cone, Cylinder, Torus are types of ThreeDimensionalShape

### Composition (HAS-A)
- Each TwoDimensionalShape has an area
- Each ThreeDimensionalShape has a volume
- Each Shape has a number of dimensions

---

## Key Design Principles Applied

1. **Abstraction**: Shape, TwoDimensionalShape, and ThreeDimensionalShape are abstract
2. **Polymorphism**: Each shape implements area/volume calculation differently
3. **Single Responsibility**: Each class has one clear purpose
4. **DRY (Don't Repeat Yourself)**: Common functionality in parent classes
5. **Encapsulation**: Attributes are protected, methods are public

