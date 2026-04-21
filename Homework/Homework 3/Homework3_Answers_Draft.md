# Homework 3 Answers (Draft)

Student: Stefan Nikolov  
Course: CMSC 335

## Problem 1 (5 pts)
What events do these components generate in Swing or JavaFX?

### JButton (Swing) / Button (JavaFX)
- Swing `JButton`:
  - `ActionEvent` (primary event fired when button is activated).
- JavaFX `Button`:
  - `ActionEvent` (`setOnAction(...)`).

### JTextField (Swing) / TextField (JavaFX)
- Swing `JTextField`:
  - `ActionEvent` (typically when user presses Enter).
  - Text-change related document events via the attached `Document` (`DocumentListener`: insert/remove/change updates).
- JavaFX `TextField`:
  - `ActionEvent` (when Enter is pressed).
  - Value/text change notifications through `textProperty()` listeners.

### JComboBox (Swing) / ComboBox (JavaFX)
- Swing `JComboBox`:
  - `ActionEvent` (selection/action changes).
  - `ItemEvent` (item selected/deselected transitions).
- JavaFX `ComboBox`:
  - `ActionEvent` (user commits/changes selection).
  - Value change notifications through `valueProperty()` listeners.

## Problem 2 (5 pts)
What methods does `JTable` implement that are required by interfaces implemented by `JTable` (beyond parent-class interfaces)?

`JTable` implements these key interfaces directly: `TableModelListener`, `Scrollable`, `TableColumnModelListener`, `ListSelectionListener`, `CellEditorListener`, and `RowSorterListener` (and accessibility support).

Required methods include:

### From `TableModelListener`
- `tableChanged(TableModelEvent e)`

### From `Scrollable`
- `getPreferredScrollableViewportSize()`
- `getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)`
- `getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)`
- `getScrollableTracksViewportWidth()`
- `getScrollableTracksViewportHeight()`

### From `TableColumnModelListener`
- `columnAdded(TableColumnModelEvent e)`
- `columnRemoved(TableColumnModelEvent e)`
- `columnMoved(TableColumnModelEvent e)`
- `columnMarginChanged(ChangeEvent e)`
- `columnSelectionChanged(ListSelectionEvent e)`

### From `ListSelectionListener`
- `valueChanged(ListSelectionEvent e)`

### From `CellEditorListener`
- `editingStopped(ChangeEvent e)`
- `editingCanceled(ChangeEvent e)`

### From `RowSorterListener`
- `sorterChanged(RowSorterEvent e)`

These are the methods `JTable` provides to satisfy those interface contracts at its class level.

## Problem 3 (5 pts)
Differences among layout managers, especially when the container is resized.

### a) FlowPane (FX) / FlowLayout (Swing)
- Places children in a flow sequence (left-to-right, then wraps).
- On resize:
  - Width increase: fewer wraps, more controls fit per row.
  - Width decrease: more wrapping into additional rows.
- Components generally keep preferred sizes unless constrained.

### b) GridPane (FX) / GridLayout (Swing)
- GridLayout (Swing): strict equal-size cells in a fixed rows/columns grid.
- GridPane (FX): row/column constraints allow more flexible sizing and spanning.
- On resize:
  - GridLayout stretches all cells uniformly.
  - GridPane redistributes space by constraints/grow priorities; can preserve relative structure better.

### c) AnchorPane (FX)
- Children are anchored to top/bottom/left/right offsets.
- On resize:
  - Node position/size changes according to anchor constraints.
  - If both left and right anchors are set, width stretches; similarly for top/bottom and height.

### d) TilePane (FX)
- Arranges nodes in equal-size tiles.
- On resize:
  - Reflows by changing tile count per row/column.
  - Maintains uniform tile dimensions and spacing, producing a regular gallery-like layout.

### e) BorderPane (FX) (additional layout)
- Five regions: top, bottom, left, right, center.
- On resize:
  - Center region absorbs most extra/shrinking space.
  - Edge regions typically maintain preferred dimensions unless constraints require adjustment.

## Problem 4 (10 pts)
Dining philosophers.

### a) What is wrong with everyone doing: pick left then right?
- This can deadlock via circular wait:
  - All philosophers pick up their left chopstick.
  - Each waits forever for right chopstick held by a neighbor.
- Progress stops even though no philosopher can proceed to eating.

### b) How to fix to avoid deadlock?
A standard deadlock-free fix is an arbitrator (waiter) using a semaphore that allows at most 4 philosophers to attempt pickup simultaneously.

Example idea:
1. Philosopher requests permission from waiter (`Semaphore(4, true)`).
2. If granted, philosopher picks up both chopsticks, eats, puts both down.
3. Philosopher releases waiter permit.

Why this avoids deadlock:
- With only 4 contenders, at least one philosopher can obtain two chopsticks and complete, breaking circular hold-and-wait.

### c) Is this starvation free?
- With a fair semaphore/queue (`fair=true`) and fair chopstick locking policy, it is starvation-free in practice because waiting philosophers are served in order.
- Without fairness guarantees, deadlock can still be avoided, but starvation is still possible for unlucky philosophers.

## Problem 5 (10 pts)
What methods must a class implementing `java.util.concurrent.locks.Lock` implement, and expected characteristics?

Required methods:
1. `void lock()`
2. `void lockInterruptibly() throws InterruptedException`
3. `boolean tryLock()`
4. `boolean tryLock(long time, TimeUnit unit) throws InterruptedException`
5. `void unlock()`
6. `Condition newCondition()`

Expected characteristics:

### `lock()`
- Acquires lock, blocking indefinitely until available.
- Non-interruptible while waiting (implementation dependent but generally waits until lock acquired).

### `lockInterruptibly()`
- Like `lock()`, but waiting thread can be interrupted.
- Throws `InterruptedException` if interrupted before acquisition.

### `tryLock()`
- Non-blocking attempt.
- Returns immediately: `true` if acquired, `false` otherwise.

### `tryLock(timeout, unit)`
- Bounded wait attempt.
- Returns `true` if acquired within timeout; `false` if timed out.
- Interruptible while waiting.

### `unlock()`
- Releases lock.
- Must typically be called by owning thread (or implementation throws `IllegalMonitorStateException`-like behavior).
- Should be in `finally` blocks to ensure release.

### `newCondition()`
- Creates a `Condition` object bound to this lock.
- Enables explicit wait/signal patterns (`await`, `signal`, `signalAll`) with multiple condition queues per lock.

## Problem 6 (5 pts)
What does the JVM do when it encounters `synchronized`?

- For `synchronized(obj) { ... }`, JVM emits monitor-enter/monitor-exit semantics on `obj`'s monitor.
- For `synchronized` instance methods, monitor is the receiver object (`this`).
- For `static synchronized` methods, monitor is the `Class` object.
- If monitor is unavailable, thread blocks (or contends) until it can enter.
- Monitor ownership is reentrant: same thread may re-enter without deadlocking itself.
- On monitor exit, JVM enforces memory visibility/happens-before guarantees so updates inside synchronized regions become visible to threads that subsequently acquire the same monitor.

## Problem 7 (10 pts)
Difference between `Lock` interface and `synchronized` keyword.

### Similarity
- Both provide mutual exclusion and memory visibility guarantees for critical sections.

### Key differences
1. Acquisition model:
- `synchronized`: implicit acquisition/release by JVM at block/method boundaries.
- `Lock`: explicit `lock()`/`unlock()` calls by programmer.

2. Flexibility:
- `Lock` supports non-blocking and timed attempts (`tryLock()`), interruptible acquisition (`lockInterruptibly()`), and richer control patterns.
- `synchronized` has no built-in timed/non-blocking/interruptible acquisition API.

3. Conditions/waiting:
- `synchronized` uses a single intrinsic condition queue per monitor (`wait/notify/notifyAll`).
- `Lock` supports multiple `Condition` objects per lock for finer-grained coordination.

4. Fairness options:
- Many `Lock` implementations (for example `ReentrantLock`) can be configured with fairness policy.
- `synchronized` provides no fairness policy control.

5. Error risk:
- `Lock` is more powerful but easier to misuse if `unlock()` is forgotten.
- `synchronized` is safer for simple cases because release is automatic on normal exit or exception.

### Practical guidance
- Use `synchronized` for simple, structured locking.
- Use `Lock` when you need timed acquisition, interruptible waits, multiple conditions, or advanced concurrency control.

## Rubric Alignment Checklist
- Problem 1: events listed for each required component.
- Problem 2: interface-required `JTable` methods listed.
- Problem 3: layout differences with resize behavior addressed.
- Problem 4: deadlock flaw explained, deadlock fix provided, starvation-freedom discussed.
- Problem 5: all `Lock` interface methods listed with expected characteristics.
- Problem 6: JVM synchronization behavior explained with monitor semantics.
- Problem 7: `Lock` vs `synchronized` differences clearly explained.
