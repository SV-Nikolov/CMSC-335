% CMSC 335 Project 1 - Developer's Guide
% Author: Stefan Nikolov
% Date: March 23, 2026

# Developer's Guide: Java OO Shapes Program

## 1. Overview

This project implements an Object-Oriented Java application that demonstrates inheritance, polymorphism, and encapsulation through a comprehensive shape hierarchy. The program features a command-line menu interface that allows users to construct various 2D and 3D shapes and calculate their properties.

---

## 2. Project Structure

```
Project1/
├── Shape.java                  (Abstract base class)
├── TwoDimensionalShape.java    (2D base class)
├── ThreeDimensionalShape.java  (3D base class)
├── Circle.java                 (2D shape)
├── Rectangle.java              (2D shape)
├── Square.java                 (2D shape)
├── Triangle.java               (2D shape)
├── Sphere.java                 (3D shape)
├── Cube.java                   (3D shape)
├── Cone.java                   (3D shape)
├── Cylinder.java               (3D shape)
├── Torus.java                  (3D shape)
├── ShapesProgram.java          (Main driver program)
├── UML_Diagram.md              (Visual class hierarchy)
├── DEVELOPERS_GUIDE.md         (This file)
└── TEST_PLAN.md                (Test cases and results)
```

---

## 3. Compilation Instructions

### Prerequisites
- Java Development Kit (JDK) 8 or higher installed
- Command-line terminal or IDE with Java support

### Compiling All Files

**Option 1: Individual Compilation**
```bash
javac Shape.java
javac TwoDimensionalShape.java
javac ThreeDimensionalShape.java
javac Circle.java
javac Rectangle.java
javac Square.java
javac Triangle.java
javac Sphere.java
javac Cube.java
javac Cone.java
javac Cylinder.java
javac Torus.java
javac ShapesProgram.java
```

**Option 2: Batch Compilation**
```bash
javac *.java
```

### Verification
After compilation, you should see .class files generated for each Java file.

---

## 4. Execution Instructions

### Running the Program

```bash
java ShapesProgram
```

### Expected Output
The program will display:
1. Welcome message
2. Menu with 10 options
3. Prompts for shape parameters
4. Calculated area or volume
5. Exit message with date/time

---

## 5. Program Structure & Architecture

### 5.1 Class Hierarchy

**Top Level: Shape (Abstract)**
- Base class for all shapes
- Contains shared attributes (dimensions)
- Defines abstract methods each shape must implement

**Mid Level: TwoDimensionalShape and ThreeDimensionalShape**
- Inherit from Shape
- Add specific properties (area or volume)
- Define abstract calculateArea/Volume methods

**Concrete Level: Individual Shapes**
- Circle, Rectangle, Square, Triangle (2D)
- Sphere, Cube, Cone, Cylinder, Torus (3D)
- Each implements specific calculation logic

### 5.2 Design Patterns Used

1. **Template Method Pattern**: Abstract methods in base classes
2. **Strategy Pattern**: Different calculation strategies per shape
3. **Inheritance Pattern**: Code reuse through class hierarchy

### 5.3 Key Features

- **Input Validation**: Ensures all dimensions are positive
- **Error Handling**: Catches invalid input and displays error messages
- **User Interactivity**: Continuous loop until user exits
- **Clear Output**: Formatted results with descriptions

---

## 6. Implementation Details

### 6.1 Shape Calculations

#### 2D Shapes (Area)
- **Circle**: π * r²
- **Rectangle**: length * width
- **Square**: side²
- **Triangle**: (base * height) / 2

#### 3D Shapes (Volume)
- **Sphere**: (4/3) * π * r³
- **Cube**: side³
- **Cone**: (1/3) * π * r² * h
- **Cylinder**: π * r² * h
- **Torus**: (π * r²) * (2 * π * R)

### 6.2 Input Validation Strategy

```java
while (!scanner.hasNextDouble()) {
    // Handle non-numeric input
    scanner.nextLine();
    // Prompt user again
}
```

This ensures the program gracefully handles:
- Non-numeric entries (strings, special characters)
- Negative or zero values
- Invalid menu selections

### 6.3 User Interaction Flow

1. Display menu
2. Get user input with validation
3. If valid selection (1-9): Create shape and calculate
4. If invalid: Show error message and loop
5. If exit (10): Display exit message with timestamp
6. Ask if user wants to continue

---

## 7. Testing Methodology

### Unit Testing Approach
1. Test each shape class individually
2. Verify calculations match mathematical formulas
3. Test input validation edge cases
4. Test menu navigation

### Manual Testing
- Compile and run program
- Enter various valid and invalid inputs
- Verify output matches expected calculations
- Check error handling for edge cases
- Verify exit message includes correct date/time

### Test Coverage
- All 9 shape types
- Valid positive inputs
- Invalid inputs (negative, zero, text)
- Invalid menu selections
- Exit functionality

---

## 8. Lessons Learned

### 8.1 OOP Principles Applied

**Inheritance**: Reduced code duplication by creating base classes for 2D and 3D shapes with common properties and methods.

**Polymorphism**: Each shape implements area/volume calculation differently, allowing the same method call to produce different results based on the object type.

**Encapsulation**: Private attributes and public methods hide implementation details while providing controlled access to shape properties.

**Abstraction**: Abstract base classes define the interface that all shapes must follow, allowing flexible implementation.

### 8.2 Design Challenges & Solutions

**Challenge 1: Code Duplication**
- Problem: Repeated menu display and input handling
- Solution: Created private static methods in main class for reusable logic

**Challenge 2: Input Type Errors**
- Problem: Users entering text instead of numbers crashes program
- Solution: Implemented Scanner.hasNextDouble() checks with retry loops

**Challenge 3: Negative Dimensions**
- Problem: Invalid shapes with negative dimensions
- Solution: Added validation in constructors with IllegalArgumentException

**Challenge 4: Memory Management**
- Problem: Accumulation of shape objects
- Solution: Objects are garbage collected after each iteration

### 8.3 What Would I Improve

1. **Persistence**: Save created shapes to file for later retrieval
2. **Graphical Display**: Show visual representation of shapes
3. **Error Recovery**: More granular error messages for specific validation failures
4. **Shape Modification**: Allow editing existing shape dimensions
5. **Historical Data**: Track all shapes created in current session and show statistics
6. **Configuration**: Allow customization of output precision and units

### 8.4 Key Takeaways

1. **Inheritance is powerful**: Reduces code, improves maintainability
2. **Abstract classes guide design**: Force consistent implementation
3. **Validation is critical**: Users will enter unexpected data
4. **Comments matter**: Well-documented code is easier to maintain
5. **Testing should be comprehensive**: Edge cases matter more than happy paths

---

## 9. Compilation Verification

After running `javac *.java`, you should have these .class files:
- Shape.class
- TwoDimensionalShape.class
- ThreeDimensionalShape.class
- Circle.class, Rectangle.class, Square.class, Triangle.class
- Sphere.class, Cube.class, Cone.class, Cylinder.class, Torus.class
- ShapesProgram.class

If any file fails to compile, check:
1. Java syntax for typos
2. Class dependencies (parent classes must compile first)
3. Package structure (all files in same directory)

---

## 10. Troubleshooting

### Common Issues

**Issue**: "Exception in thread... ClassNotFoundException"
- Solution: Ensure all .class files are in the same directory

**Issue**: "cannot find symbol" error during compilation
- Solution: Verify class names match exactly (case-sensitive)

**Issue**: Program crashes on invalid input
- Solution: This is by design - input validation loops until valid input

**Issue**: Area/Volume calculations seem wrong
- Solution: Verify formulas in individual shape classes match requirements

---

## 11. Future Enhancements

1. Support for additional shapes (Pyramid, Ellipse, etc.)
2. Unit conversion (cm to inches, etc.)
3. Batch shape processing from files
4. Shape comparison functionality
5. Database integration for shape history
6. Web-based interface

---

**End of Developer's Guide**

