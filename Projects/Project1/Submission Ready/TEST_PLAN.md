# Test Plan

## Test Summary
| Category | Total | Pass |
|---|---:|---:|
| 2D calculations | 4 | 4 |
| 3D calculations | 5 | 5 |
| Validation and menu | 5 | 5 |
| Total | 14 | 14 |

## Core Calculation Tests
| Test | Input | Expected |
|---|---|---|
| Circle | r=5 | area=78.54 |
| Rectangle | l=4, w=9.5 | area=38.00 |
| Square | s=7 | area=49.00 |
| Triangle | b=10, h=6 | area=30.00 |
| Sphere | r=3 | volume=113.10 |
| Cube | s=4 | volume=64.00 |
| Cone | r=3, h=8 | volume=75.40 |
| Cylinder | r=2, h=5 | volume=62.83 |
| Torus | R=5, r=2 | volume=394.78 |

## Validation Tests
| Test | Input | Expected |
|---|---|---|
| Invalid menu option | 15 | Shows invalid selection and loops |
| Non-numeric value | "abc" | Rejects input and reprompts |
| Negative value | -5 | Rejects input and reprompts |
| Zero value | 0 | Rejects input and reprompts |
| Exit | 10 | Prints thank-you + timestamp |
