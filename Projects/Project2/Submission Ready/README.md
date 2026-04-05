# CMSC 335 Project 2 - README

## Project Overview
This project extends Project 1 into a Java Swing GUI that uses event handlers and listeners.
The application allows users to select a shape, choose dimensional parameters from dropdown controls, and draw the shape in a frame while calculating area or volume.

## Visual Documentation
- UML class diagram image: [UML_CLASS_DIAGRAM.png](UML_CLASS_DIAGRAM.png)
- UML event flow image: [UML_EVENT_FLOW.png](UML_EVENT_FLOW.png)
- Mermaid sources: [UML_CLASS_DIAGRAM.mmd](UML_CLASS_DIAGRAM.mmd), [UML_EVENT_FLOW.mmd](UML_EVENT_FLOW.mmd)

## Implemented Shape Set
- Circle
- Rectangle
- Square
- Triangle
- Sphere
- Cube
- Cone
- Cylinder
- Torus

## Deliverables in This Folder
- Java source files for all classes
- UML class diagram in Mermaid format and PNG image
- UML event flow in Mermaid format and PNG image
- Developer guide
- Test plan with screenshot references and PASS/FAIL status
- Submission checklist and index
- Submission packaging script

## Build and Run
```bash
javac *.java
java ShapesGuiProgram
```

## Notes
- 2D shapes display area.
- 3D shapes display volume.
- Torus enforces major radius > minor radius.
- The GUI uses listeners on dropdowns and buttons to update controls and render output.
- A standalone launcher is provided as [run.bat](run.bat).
