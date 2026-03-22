% CMSC 335 Project 1 - Test Plan
% Author: Stefan Nikolov
% Date: March 23, 2026

# Test Plan: Java OO Shapes Program

## Test Execution Summary

**Project**: CMSC 335 - Java OO Shapes Program
**Date Tested**: March 23, 2026
**Tester**: Stefan Nikolov
**Total Test Cases**: 16
**Overall Result**: PASS (All 9 shapes tested with valid inputs; Error handling verified)

---

## Summary of Test Results

| Category | Tests | Passed | Failed | Pass Rate |
|----------|-------|--------|--------|-----------|
| 2D Shapes | 5 | 5 | 0 | 100% |
| 3D Shapes | 5 | 5 | 0 | 100% |
| Error Handling | 6 | 6 | 0 | 100% |
| **TOTAL** | **16** | **16** | **0** | **100%** |

---

## Test Case Details

### 2D Shape Tests

#### Test 1: Circle - Standard Radius
| Property | Value |
|----------|-------|
| Shape | Circle |
| Input | Menu: 1, Radius: 5 |
| Expected Output | Area = 78.54 (π * 5²) |
| Formula | Area = π * r² |
| Result | ✅ PASS |

#### Test 2: Rectangle - Different Dimensions
| Property | Value |
|----------|-------|
| Shape | Rectangle |
| Input | Menu: 2, Length: 4, Width: 9.5 |
| Expected Output | Area = 38.00 |
| Formula | Area = length * width |
| Result | ✅ PASS |

#### Test 3: Square - Integer Side
| Property | Value |
|----------|-------|
| Shape | Square |
| Input | Menu: 3, Side: 7 |
| Expected Output | Area = 49.00 |
| Formula | Area = side² |
| Result | ✅ PASS |

#### Test 4: Triangle - Base and Height
| Property | Value |
|----------|-------|
| Shape | Triangle |
| Input | Menu: 4, Base: 10, Height: 6 |
| Expected Output | Area = 30.00 |
| Formula | Area = (base * height) / 2 |
| Result | ✅ PASS |

#### Test 5: Circle - Decimal Radius
| Property | Value |
|----------|-------|
| Shape | Circle |
| Input | Menu: 1, Radius: 2.5 |
| Expected Output | Area ≈ 19.63 |
| Formula | Area = π * r² |
| Result | ✅ PASS |

---

### 3D Shape Tests

#### Test 6: Sphere - Standard Radius
| Property | Value |
|----------|-------|
| Shape | Sphere |
| Input | Menu: 5, Radius: 3 |
| Expected Output | Volume ≈ 113.10 |
| Formula | Volume = (4/3) * π * r³ |
| Result | ✅ PASS |

#### Test 7: Cube - Integer Side
| Property | Value |
|----------|-------|
| Shape | Cube |
| Input | Menu: 6, Side: 4 |
| Expected Output | Volume = 64.00 |
| Formula | Volume = side³ |
| Result | ✅ PASS |

#### Test 8: Cone - Radius and Height
| Property | Value |
|----------|-------|
| Shape | Cone |
| Input | Menu: 7, Radius: 3, Height: 8 |
| Expected Output | Volume ≈ 75.40 |
| Formula | Volume = (1/3) * π * r² * h |
| Result | ✅ PASS |

#### Test 9: Cylinder - Radius and Height
| Property | Value |
|----------|-------|
| Shape | Cylinder |
| Input | Menu: 8, Radius: 2, Height: 5 |
| Expected Output | Volume ≈ 62.83 |
| Formula | Volume = π * r² * h |
| Result | ✅ PASS |

#### Test 10: Torus - Major and Minor Radii
| Property | Value |
|----------|-------|
| Shape | Torus |
| Input | Menu: 9, Major: 5, Minor: 2 |
| Expected Output | Volume ≈ 394.78 |
| Formula | Volume = (π * r²) * (2 * π * R) |
| Result | ✅ PASS |

---

### Error Handling & Validation Tests

#### Test 11: Invalid Menu Selection
| Scenario | Invalid menu choice |
|----------|---------------------|
| Input | Menu: 15 (out of range) |
| Expected | "Invalid selection. Please try again." |
| Behavior | Redisplays menu without crashing |
| Result | ✅ PASS |

#### Test 12: Non-Numeric Input
| Scenario | Text instead of number |
|----------|------------------------|
| Input | Menu: 1 (Circle), Radius: "abc" |
| Expected | Returns error and prompts again |
| Behavior | Gracefully handles invalid type |
| Result | ✅ PASS |

#### Test 13: Negative Dimension
| Scenario | Negative value input |
|----------|---------------------|
| Input | Menu: 1 (Circle), Radius: -5 |
| Expected | "Value must be positive. Please try again." |
| Behavior | Rejects negative values |
| Result | ✅ PASS |

#### Test 14: Zero Dimension
| Scenario | Zero value input |
|----------|------------------|
| Input | Menu: 2 (Rectangle), Length: 0 |
| Expected | "Value must be positive. Please try again." |
| Behavior | Rejects zero values |
| Result | ✅ PASS |

#### Test 15: Program Exit
| Scenario | Program termination |
|----------|-------------------|
| Input | Menu: 10 (Exit) |
| Expected | Exit message with current date/time |
| Behavior | Displays goodbye + timestamp, exits gracefully |
| Result | ✅ PASS |

#### Test 16: Invalid Torus Configuration
| Scenario | Major ≤ Minor radius |
|----------|---------------------|
| Input | Menu: 9, Major: 2, Minor: 5 |
| Expected | "Error: Major radius must be greater than minor radius" |
| Behavior | Rejects invalid configuration |
| Result | ✅ PASS |

---

## Compilation & Execution Status

### ✅ Compilation Verification
- All 13 Java files compiled successfully
- Zero compiler errors
- Zero compiler warnings
- All .class files generated correctly

### ✅ Program Execution
- Welcome message displays correctly
- Menu shows all 10 options
- Input validation works perfectly
- Calculations accurate for all shapes
- Exit message includes timestamp
- Program terminates gracefully

---

## Test Coverage Summary

✅ **All 9 shapes tested** - Circle, Rectangle, Square, Triangle, Sphere, Cube, Cone, Cylinder, Torus

✅ **Valid inputs** - Integer and decimal values

✅ **Error cases** - Negative, zero, non-numeric, invalid menu selections

✅ **Edge cases** - Torus major/minor radius validation

✅ **Exit functionality** - Timestamp and graceful termination

---

## Notes on Testing

1. **Floating-Point Precision**: All outputs verified to 2 decimal places
2. **Input Handling**: Both integer and decimal inputs tested
3. **Timestamp Validation**: Exit message includes accurate date/time
4. **Menu Loop**: Correctly loops until exit is selected
5. **Error Recovery**: Program never crashes, always prompts for retry

---

## Lessons from Testing

- Input validation is critical for user experience
- Clear error messages help users recover from mistakes
- Edge cases reveal design robustness
- Comprehensive testing improves code quality

---

**Test Plan: COMPLETE ✅**

**Overall Status: ALL TESTS PASSED (16/16 = 100%)**

