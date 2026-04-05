# Developer Guide

## 1. Project Structure
- Shape.java
- TwoDimensionalShape.java
- ThreeDimensionalShape.java
- Circle.java, Rectangle.java, Square.java, Triangle.java
- Sphere.java, Cube.java, Cone.java, Cylinder.java, Torus.java
- ShapeDisplayPanel.java
- ShapesGuiProgram.java
- draw_uml_pngs.py
- Project2ScreenshotGenerator.java

## 2. Compilation
```bash
javac *.java
```

## 3. Execution
```bash
java ShapesGuiProgram
```

## 4. GUI Behavior
- Shape dropdown chooses one of 9 shapes.
- Unit dropdown selects cm, m, or in for display text.
- Parameter dropdowns provide dimension choices.
- Draw Shape button constructs the selected shape object and updates output.
- Clear button resets only the drawing/result.
- Reset Defaults button restores shape, unit, and dimensions.
- The left panel is intentionally compact so the drawing area stays readable.

## 5. Event Handlers and Listeners
- Shape selector action listener updates parameter labels and visibility.
- Draw button action listener validates parameters, builds the selected shape, computes area/volume, and renders it.
- Clear and Reset buttons each trigger dedicated handlers.

## 6. Design Notes
- The inheritance hierarchy from Project 1 is preserved.
- The rendering panel is separated into ShapeDisplayPanel to keep UI concerns isolated.
- 3D shapes are represented with simple projected drawings inside Swing graphics.
- UML documentation is stored as Mermaid source and exported PNG images for submission review.

## 7. Assumptions
- Triangle area uses base and height.
- Torus volume uses major and minor radius, where major > minor.
- All dimensions must be positive.

## 8. Lessons Learned
- UI event flow is easiest to maintain when display and model concerns are separated.
- Reusing validated core classes from Project 1 lowers risk in Project 2.
- Input constraints still matter in GUI apps even with dropdown controls.
- Custom painting requires clear scaling logic to avoid clipping and distortion.
- Clear documentation packaging helps separate review-time artifacts from final submission archives.
