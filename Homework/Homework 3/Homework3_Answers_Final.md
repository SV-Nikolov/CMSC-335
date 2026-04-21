# Homework 3 Answers

Student: Stefan Nikolov  
Course: CMSC 335

## Problem 1 (5 pts)
**What events do these components generate in Swing or JavaFX?**

### JButton (Swing) / Button (JavaFX)
- Swing `JButton`: `ActionEvent` when activated (click, keyboard activation).
- JavaFX `Button`: `ActionEvent` (typically handled with `setOnAction(...)`).

### JTextField (Swing) / TextField (JavaFX)
- Swing `JTextField`:
  - `ActionEvent` when Enter is pressed.
  - Document change events through its `Document` (via `DocumentListener`).
- JavaFX `TextField`:
  - `ActionEvent` when Enter is pressed.
  - Text/value change notifications via `textProperty()` listeners.

### JComboBox (Swing) / ComboBox (JavaFX)
- Swing `JComboBox`:
  - `ActionEvent` for action/selection changes.
  - `ItemEvent` for item selected/deselected transitions.
- JavaFX `ComboBox`:
  - `ActionEvent` when user commits selection/action.
  - Value change notifications via `valueProperty()` listeners.

## Problem 2 (5 pts)
**What methods does `JTable` implement that are required by interfaces implemented by `JTable` (beyond those implemented through parent classes)?**

`JTable` directly implements listener/scrolling contracts, including `TableModelListener`, `Scrollable`, `TableColumnModelListener`, `ListSelectionListener`, `CellEditorListener`, and `RowSorterListener`.

### Methods required by those interfaces
- From `TableModelListener`:
  - `tableChanged(TableModelEvent e)`

- From `Scrollable`:
  - `getPreferredScrollableViewportSize()`
  - `getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)`
  - `getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)`
  - `getScrollableTracksViewportWidth()`
  - `getScrollableTracksViewportHeight()`

- From `TableColumnModelListener`:
  - `columnAdded(TableColumnModelEvent e)`
  - `columnRemoved(TableColumnModelEvent e)`
  - `columnMoved(TableColumnModelEvent e)`
  - `columnMarginChanged(ChangeEvent e)`
  - `columnSelectionChanged(ListSelectionEvent e)`

- From `ListSelectionListener`:
  - `valueChanged(ListSelectionEvent e)`

- From `CellEditorListener`:
  - `editingStopped(ChangeEvent e)`
  - `editingCanceled(ChangeEvent e)`

- From `RowSorterListener`:
  - `sorterChanged(RowSorterEvent e)`

These methods satisfy the interface obligations that `JTable` itself declares and uses in its behavior.

## Problem 3 (5 pts)
**Address differences among layout managers, focusing on behavior when container is resized.**

### a) FlowPane (FX) / FlowLayout (Swing)
- Places components in a flowing line, wrapping when needed.
- On resize:
  - Wider container: fewer wraps (more controls per row).
  - Narrower container: more wraps (additional rows).

### b) GridPane (FX) / GridLayout (Swing)
- `GridLayout` (Swing): uniform cell sizing across a strict grid.
- `GridPane` (FX): supports constraints, growth priorities, and spanning.
- On resize:
  - `GridLayout`: evenly stretches/shrinks all cells.
  - `GridPane`: reallocates space based on row/column constraints.

### c) AnchorPane (FX)
- Nodes are pinned by top/bottom/left/right anchor offsets.
- On resize:
  - Anchored edges stay fixed offset from container bounds.
  - If opposite anchors are set (left+right or top+bottom), node stretches.

### d) TilePane (FX)
- Arranges children in equal-size tiles.
- On resize:
  - Reflows tile count per row/column.
  - Maintains consistent tile dimensions and spacing.

### e) Additional example: BorderPane (FX)
- Regions: top, bottom, left, right, center.
- On resize:
  - Center typically absorbs most extra or reduced space.
  - Edge regions mostly keep preferred dimensions unless constrained.

## Problem 4 (10 pts)
**Dining philosophers**

### a) What is wrong with everyone doing “pick left, then pick right”?
This can deadlock. If all philosophers pick up the left chopstick first, each one waits for the right chopstick held by a neighbor. That creates circular wait and no one can proceed.

### b) How can it be fixed to avoid deadlock?
Use an arbitrator/waiter with a semaphore allowing at most four philosophers to attempt pickup at once.

Example strategy:
1. Philosopher requests permit (`Semaphore(4, true)`).
2. On permit grant, philosopher acquires both chopsticks, eats, and releases both.
3. Philosopher releases semaphore permit.

Why this works:
- Limiting contenders prevents full circular hold-and-wait among all five philosophers.

### c) Is the solution starvation free?
- With fair semaphore/queueing (`fair=true`) and fair lock acquisition on chopsticks, starvation can be prevented in practice.
- Without fairness guarantees, deadlock can still be avoided, but starvation may still occur for unlucky threads.

## Problem 5 (10 pts)
**What methods must a class implementing `java.util.concurrent.locks.Lock` implement, and what are expected characteristics?**

### Required methods
1. `void lock()`
2. `void lockInterruptibly() throws InterruptedException`
3. `boolean tryLock()`
4. `boolean tryLock(long time, TimeUnit unit) throws InterruptedException`
5. `void unlock()`
6. `Condition newCondition()`

### Characteristics
- `lock()`:
  - Blocks until lock is acquired.
  - Standard choice for unconditional acquisition.

- `lockInterruptibly()`:
  - Blocks but can be interrupted while waiting.
  - Throws `InterruptedException` on interruption.

- `tryLock()`:
  - Immediate non-blocking attempt.
  - Returns `true` on success, `false` otherwise.

- `tryLock(timeout, unit)`:
  - Waits up to bounded time.
  - Returns success/failure; interruptible during wait.

- `unlock()`:
  - Releases lock.
  - Should be used in `finally` to avoid lock leaks.
  - Called by owning thread according to implementation rules.

- `newCondition()`:
  - Creates a condition queue bound to this lock.
  - Enables `await/signal/signalAll` style coordination.

## Problem 6 (5 pts)
**What does the JVM do when it encounters `synchronized`?**

- For `synchronized(obj) { ... }`, JVM performs monitor enter/exit on `obj`.
- For `synchronized` instance method, monitor is `this`.
- For `static synchronized` method, monitor is the `Class` object.
- If monitor is occupied, thread waits until it can acquire it.
- Lock is reentrant for the owning thread.
- Monitor exit provides happens-before visibility guarantees to subsequent acquirers of the same monitor.

## Problem 7 (10 pts)
**Difference between `Lock` interface and `synchronized` keyword**

### Similarity
- Both provide mutual exclusion and visibility guarantees for shared-state access.

### Differences
1. Acquisition/release:
- `synchronized`: automatic by JVM at scope boundaries.
- `Lock`: explicit `lock()` and `unlock()`.

2. Advanced acquisition options:
- `Lock`: timed, interruptible, non-blocking (`tryLock`) options.
- `synchronized`: no direct timed/non-blocking/interruptible acquisition API.

3. Condition handling:
- `synchronized`: single intrinsic condition queue (`wait/notify/notifyAll`).
- `Lock`: multiple `Condition` objects possible per lock.

4. Fairness policy:
- `Lock` implementations (for example `ReentrantLock`) may support fairness mode.
- `synchronized` has no fairness configuration.

5. Safety tradeoff:
- `synchronized` is simpler and less error-prone for basic cases.
- `Lock` is more flexible but easier to misuse if unlock is forgotten.

### Practical recommendation
- Prefer `synchronized` for straightforward locking.
- Prefer `Lock` when advanced control is needed (timeouts, interruption, multiple conditions, fairness settings).
