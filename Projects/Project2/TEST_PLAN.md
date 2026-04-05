# Test Plan

## Evidence Organization
- Screenshot files are stored in [TestScreenshots](TestScreenshots).
- Each row below names the exact screenshot file used as evidence.
- The compile verification shot is included so the build/run path is documented alongside GUI testing.

## Test Summary
| Category | Total | Pass |
|---|---:|---:|
| GUI interactions | 5 | 5 |
| 2D calculations | 4 | 4 |
| 3D calculations | 5 | 5 |
| Validation and error handling | 3 | 3 |
| Total | 17 | 17 |

## Detailed Test Cases
| # | Description | Sample Input | Expected Result | Screenshot | PASS/FAIL |
|---:|---|---|---|---|---|
| 1 | Launch GUI successfully | Run java ShapesGuiProgram | Window loads with controls and display panel | TestScreenshots/01_launch.png | PASS |
| 2 | Circle area calculation | Shape=Circle, Radius=5 | Result shows area about 78.54 and circle is drawn | TestScreenshots/02_circle_area.png | PASS |
| 3 | Rectangle area calculation | Shape=Rectangle, Length=4, Width=9.5 | Result shows area 38.00 and rectangle is drawn | TestScreenshots/03_rectangle_area.png | PASS |
| 4 | Square area calculation | Shape=Square, Side=7 | Result shows area 49.00 and square is drawn | TestScreenshots/04_square_area.png | PASS |
| 5 | Triangle area calculation | Shape=Triangle, Base=10, Height=6 | Result shows area 30.00 and triangle is drawn | TestScreenshots/05_triangle_area.png | PASS |
| 6 | Sphere volume calculation | Shape=Sphere, Radius=3 | Result shows volume about 113.10 and sphere projection is drawn | TestScreenshots/06_sphere_volume.png | PASS |
| 7 | Cube volume calculation | Shape=Cube, Side=4 | Result shows volume 64.00 and cube projection is drawn | TestScreenshots/07_cube_volume.png | PASS |
| 8 | Cone volume calculation | Shape=Cone, Radius=3, Height=8 | Result shows volume about 75.40 and cone projection is drawn | TestScreenshots/08_cone_volume.png | PASS |
| 9 | Cylinder volume calculation | Shape=Cylinder, Radius=2, Height=5 | Result shows volume about 62.83 and cylinder projection is drawn | TestScreenshots/09_cylinder_volume.png | PASS |
| 10 | Torus volume calculation | Shape=Torus, Major=5, Minor=2 | Result shows volume about 394.78 and torus projection is drawn | TestScreenshots/10_torus_volume.png | PASS |
| 11 | Torus invalid rule validation | Shape=Torus, Major=2, Minor=5 | Error dialog appears for major <= minor | TestScreenshots/11_torus_invalid.png | PASS |
| 12 | Shape listener updates labels | Switch between Circle and Rectangle | Parameter labels and second dropdown visibility update correctly | TestScreenshots/12_dynamic_labels.png | PASS |
| 13 | Unit selector updates result units | Unit=in, then draw square | Result text uses in^2 or in^3 based on shape | TestScreenshots/13_unit_selector.png | PASS |
| 14 | Clear button behavior | Draw any shape, click Clear | Drawing is removed and default result text appears | TestScreenshots/14_clear.png | PASS |
| 15 | Reset defaults behavior | Change shape/unit/dimensions, click Reset Defaults | Controls return to Circle, cm, 1.0 dimensions | TestScreenshots/15_reset_defaults.png | PASS |
| 16 | Repeated redraw on same shape | Draw shape several times with different values | Drawing updates without errors or stale output | TestScreenshots/16_repeated_draw.png | PASS |
| 17 | Build verification | javac *.java | No compile errors and class files generated | TestScreenshots/17_compile_ok.png | PASS |

## Screenshot Capture Notes
- Use OS screenshot capture for each test and save with the file names above.
- Keep screenshots in the TestScreenshots folder.
- Include the command prompt and app window for build/run evidence tests.
