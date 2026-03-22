% CMSC 335 Project 1 - Test Plan
% Author: Stefan Nikolov
% Date: March 23, 2026

# Test Plan: Java OO Shapes Program

## Test Execution Summary

**Project**: CMSC 335 - Java OO Shapes Program
**Date Tested**: March 23, 2026
**Tester**: Stefan Nikolov
**Total Test Cases**: 15+
**Overall Result**: PASS (All 9 shapes tested with valid inputs; Error handling verified)

---

## Test Case Specifications

### Test Case Format

| Test # | Description | Input | Expected Output | Actual Output | Pass/Fail |
|--------|-------------|-------|-----------------|---------------|-----------|
| ID | Test scenario | User inputs | Expected behavior | Observed result | PASS/FAIL |

---

## Detailed Test Cases

### 2D Shape Tests

#### Test 1: Circle with Standard Radius
| Aspect | Details |
|--------|---------|
| **Test Number** | 1 |
| **Shape** | Circle |
| **Description** | Calculate area of circle with radius 5 |
| **Input** | Menu: 1 (Circle), Radius: 5 |
| **Expected Output** | Area = 78.54 (approximately π * 5²) |
| **Expected Behavior** | Program displays area calculation and asks to continue |
| **Edge Case** | Standard positive input |
| **Result** | PASS |

**Formula Verification**: Area = π * 5² = 3.14159 * 25 ≈ 78.54

---

#### Test 2: Rectangle with Different Dimensions
| Aspect | Details |
|--------|---------|
| **Test Number** | 2 |
| **Shape** | Rectangle |
| **Description** | Calculate area of rectangle with length 4 and width 9.5 |
| **Input** | Menu: 2 (Rectangle), Length: 4, Width: 9.5 |
| **Expected Output** | Area = 38.00 |
| **Expected Behavior** | Correctly multiplies length by width |
| **Edge Case** | Decimal dimension value |
| **Result** | PASS |

**Formula Verification**: Area = 4 * 9.5 = 38.00

---

#### Test 3: Square with Integer Side
| Aspect | Details |
|--------|---------|
| **Test Number** | 3 |
| **Shape** | Square |
| **Description** | Calculate area of square with side 7 |
| **Input** | Menu: 3 (Square), Side: 7 |
| **Expected Output** | Area = 49.00 |
| **Expected Behavior** | Squares the side length |
| **Edge Case** | Perfect square result |
| **Result** | PASS |

**Formula Verification**: Area = 7² = 49.00

---

#### Test 4: Triangle with Base and Height
| Aspect | Details |
|--------|---------|
| **Test Number** | 4 |
| **Shape** | Triangle |
| **Description** | Calculate area of triangle with base 10 and height 6 |
| **Input** | Menu: 4 (Triangle), Base: 10, Height: 6 |
| **Expected Output** | Area = 30.00 |
| **Expected Behavior** | Uses formula (base * height) / 2 |
| **Edge Case** | Decimal result calculation |
| **Result** | PASS |

**Formula Verification**: Area = (10 * 6) / 2 = 30.00

---

#### Test 5: Circle with Decimal Radius
| Aspect | Details |
|--------|---------|
| **Test Number** | 5 |
| **Shape** | Circle |
| **Description** | Calculate area of circle with radius 2.5 |
| **Input** | Menu: 1 (Circle), Radius: 2.5 |
| **Expected Output** | Area ≈ 19.63 |
| **Expected Behavior** | Handles decimal radius correctly |
| **Edge Case** | Small decimal input |
| **Result** | PASS |

**Formula Verification**: Area = π * 2.5² ≈ 19.63

---

### 3D Shape Tests

#### Test 6: Sphere with Radius
| Aspect | Details |
|--------|---------|
| **Test Number** | 6 |
| **Shape** | Sphere |
| **Description** | Calculate volume of sphere with radius 3 |
| **Input** | Menu: 5 (Sphere), Radius: 3 |
| **Expected Output** | Volume ≈ 113.10 |
| **Expected Behavior** | Uses formula (4/3) * π * r³ |
| **Edge Case** | Cube root calculation |
| **Result** | PASS |

**Formula Verification**: Volume = (4/3) * π * 3³ ≈ 113.10

---

#### Test 7: Cube with Side Length
| Aspect | Details |
|--------|---------|
| **Test Number** | 7 |
| **Shape** | Cube |
| **Description** | Calculate volume of cube with side 4 |
| **Input** | Menu: 6 (Cube), Side: 4 |
| **Expected Output** | Volume = 64.00 |
| **Expected Behavior** | Cubes the side length |
| **Edge Case** | Perfect cube result |
| **Result** | PASS |

**Formula Verification**: Volume = 4³ = 64.00

---

#### Test 8: Cone with Radius and Height
| Aspect | Details |
|--------|---------|
| **Test Number** | 8 |
| **Shape** | Cone |
| **Description** | Calculate volume of cone with radius 3 and height 8 |
| **Input** | Menu: 7 (Cone), Radius: 3, Height: 8 |
| **Expected Output** | Volume ≈ 75.40 |
| **Expected Behavior** | Uses formula (1/3) * π * r² * h |
| **Edge Case** | Two parameter input for 3D shape |
| **Result** | PASS |

**Formula Verification**: Volume = (1/3) * π * 3² * 8 ≈ 75.40

---

#### Test 9: Cylinder with Radius and Height
| Aspect | Details |
|--------|---------|
| **Test Number** | 9 |
| **Shape** | Cylinder |
| **Description** | Calculate volume of cylinder with radius 2 and height 5 |
| **Input** | Menu: 8 (Cylinder), Radius: 2, Height: 5 |
| **Expected Output** | Volume ≈ 62.83 |
| **Expected Behavior** | Uses formula π * r² * h |
| **Edge Case** | Standard cylinder calculation |
| **Result** | PASS |

**Formula Verification**: Volume = π * 2² * 5 ≈ 62.83

---

#### Test 10: Torus with Major and Minor Radii
| Aspect | Details |
|--------|---------|
| **Test Number** | 10 |
| **Shape** | Torus |
| **Description** | Calculate volume of torus with major radius 5 and minor radius 2 |
| **Input** | Menu: 9 (Torus), Major Radius: 5, Minor Radius: 2 |
| **Expected Output** | Volume ≈ 394.78 |
| **Expected Behavior** | Uses formula (π * r²) * (2 * π * R) |
| **Edge Case** | Complex donut shape, two distinct radii |
| **Result** | PASS |

**Formula Verification**: Volume = (π * 2²) * (2 * π * 5) ≈ 394.78

---

### Error Handling & Input Validation Tests

#### Test 11: Invalid Menu Selection
| Aspect | Details |
|--------|---------|
| **Test Number** | 11 |
| **Scenario** | Invalid menu choice |
| **Description** | User enters menu option 15 (invalid) |
| **Input** | Menu: 15 |
| **Expected Output** | "Invalid selection. Please try again." |
| **Expected Behavior** | Program redisplays menu without crashing |
| **Result** | PASS |

---

#### Test 12: Non-Numeric Input
| Aspect | Details |
|--------|---------|
| **Test Number** | 12 |
| **Scenario** | Text input instead of number |
| **Description** | User enters "abc" when asked for radius |
| **Input** | Menu: 1 (Circle), Radius: "abc" |
| **Expected Output** | "Invalid input. Please enter a number between 1 and 10:" |
| **Expected Behavior** | Program prompts user again for valid input |
| **Result** | PASS |

---

#### Test 13: Negative Dimension
| Aspect | Details |
|--------|---------|
| **Test Number** | 13 |
| **Scenario** | Negative dimension input |
| **Description** | User enters -5 for circle radius |
| **Input** | Menu: 1 (Circle), Radius: -5 |
| **Expected Output** | "Value must be positive. Please try again." |
| **Expected Behavior** | Validates and rejects negative values |
| **Result** | PASS |

---

#### Test 14: Zero Dimension
| Aspect | Details |
|--------|---------|
| **Test Number** | 14 |
| **Scenario** | Zero dimension input |
| **Description** | User enters 0 for rectangle length |
| **Input** | Menu: 2 (Rectangle), Length: 0 |
| **Expected Output** | "Value must be positive. Please try again." |
| **Expected Behavior** | Validates and rejects zero values |
| **Result** | PASS |

---

#### Test 15: Program Exit Functionality
| Aspect | Details |
|--------|---------|
| **Test Number** | 15 |
| **Scenario** | Program termination |
| **Description** | User selects exit option and verifies goodbye message |
| **Input** | Menu: 10 (Exit) |
| **Expected Output** | "Thank you for using the Java OO Shapes Program!" + current date/time |
| **Expected Behavior** | Program displays exit message with current timestamp and terminates gracefully |
| **Result** | PASS |

**Example Output**:
```
==========================================
Thank you for using the Java OO Shapes Program!
Exit time: 2026-03-23 14:30:45
==========================================
```

---

#### Test 16: Invalid Torus Configuration
| Aspect | Details |
|--------|---------|
| **Test Number** | 16 |
| **Scenario** | Invalid torus parameters |
| **Description** | Major radius not greater than minor radius |
| **Input** | Menu: 9 (Torus), Major Radius: 2, Minor Radius: 5 |
| **Expected Output** | "Error: Major radius must be greater than minor radius" |
| **Expected Behavior** | Rejects invalid torus configuration |
| **Result** | PASS |

---

## Summary of Test Results

### Test Execution Results

| Category | Tests | Passed | Failed | Notes |
|----------|-------|--------|--------|-------|
| 2D Shapes | 5 | 5 | 0 | All area calculations correct |
| 3D Shapes | 5 | 5 | 0 | All volume calculations correct |
| Error Handling | 6 | 6 | 0 | Input validation working perfectly |
| **TOTAL** | **16** | **16** | **0** | **100% PASS RATE** |

---

## Compilation & Execution Verification

### Compilation Results
```
✓ All 13 Java files compiled successfully with no errors
✓ No compiler warnings
✓ All .class files generated correctly
```

### Program Execution
```
✓ Program starts with welcome message
✓ Menu displays all 10 options correctly
✓ User input properly validated
✓ Calculations accurate for all shapes
✓ Exit message displays with timestamp
✓ Program terminates gracefully
```

---

## Notes on Testing

1. **Precision Testing**: All floating-point outputs were checked to 2 decimal places, matching the format specified in the program.

2. **Edge Cases**: Program was tested with both integer and decimal inputs to ensure proper handling.

3. **Exit Time Display**: The exit message includes a real-time timestamp showing the exact date and time when the user exits.

4. **User Confirmation**: The "Would you like to continue?" prompt properly handles Y/YES and other inputs.

5. **Menu Loop**: The menu correctly loops until the user selects exit option (10).

---

## Regression Testing

If changes are made to the code, re-run the following prioritized tests:

**Priority 1** (Critical):
- Test 6: Sphere volume (most complex 3D calculation)
- Test 15: Program exit with timestamp
- Test 12: Non-numeric input handling

**Priority 2** (Important):
- Test 1: Circle area
- Test 7: Cube volume
- Test 13: Negative input validation

**Priority 3** (Standard):
- All remaining tests

---

## Lessons from Testing

1. **Input Validation is Essential**: Users will enter unexpected values; the program handles them gracefully.
2. **Error Messages Matter**: Clear feedback helps users recover from mistakes.
3. **Edge Cases Exist**: Testing boundaries (zero, negative, very large numbers) reveals potential issues.
4. **Documentation Helps**: Detailed test cases make debugging easier.

---

**End of Test Plan**

