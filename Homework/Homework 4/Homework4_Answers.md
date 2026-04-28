# Homework 4: Thread Behavior and Output Analysis

## Problem 1: Interesting Elements Related to Threads (10 pts)

The program demonstrates several fundamental threading concepts:

**Thread Creation and Startup:** The program creates four separate threads, each executing a `PrintChar` Runnable instance. Each thread is initialized with a character string and a count of 200 iterations.

**Concurrent Execution:** When `ts.start()` is called, each thread begins running independently in parallel. The operating system's thread scheduler allocates CPU time to each thread, causing their execution to interleave.

**Non-Deterministic Output:** The output order is unpredictable because thread scheduling is non-deterministic. A typical output might be:
```
aX.a+X.a+X+.aX+.a....
```
Instead of the sequential output that would occur if threads ran one after another. You might see characters from different threads interleaved randomly.

**Race Condition (Viewing Output):** While there's no synchronization issue in this program (each thread only prints from its own character), the `System.out.print()` calls may interleave in various ways, creating different visual patterns each run.

**Resource Sharing:** All four threads share access to the same `System.out` stream and the console, but this doesn't cause data corruption because `print()` is atomic at the byte level for individual characters.

## Problem 2: Changing start() to run() (10 pts)

**What Changes:** If `run()` is called instead of `start()`, the program no longer executes threads concurrently. Instead, `run()` executes synchronously in the main thread, blocking each iteration until it completes.

**Output Difference:** The output becomes completely sequential and deterministic:
```
aaaaaaaaaa...aaaa (200 times) XXXXXXXXXX... (200 times) ++++++++++ (200 times) ........ (200 times)
```

**Explanation:** When you call `start()`, Java creates a new thread that calls `run()` in that new thread. When you directly call `run()`, you're calling it in the current (main) thread. The loop continues in sequence: all 200 'a's, then all 200 'X's, then all 200 '+' characters, then all 200 periods. No interleaving occurs because there's only one thread executing the loop sequentially.

**Performance Impact:** The program runs much faster because there's no context-switching overhead, but you lose all concurrency benefits.

## Problem 3: Adding Thread.yield() Between Lines 23 and 24 (10 pts)

**What Changes:** Adding `Thread.yield()` after each character print suggests to the thread scheduler that the current thread is willing to yield its CPU time to other threads.

**Output Difference:** The output becomes more evenly distributed among the characters. You're more likely to see:
```
aX+.aX+.aX+.aX+.aX+.aX+.aX+.aX+.aX+.
```
Rather than long runs of a single character.

**Explanation:** `Thread.yield()` doesn't guarantee that another thread will run (the same thread might resume immediately), but it makes it more likely by politely suggesting to the scheduler "I don't need all the CPU time right now." This creates a more balanced round-robin-like execution pattern.

**Why It Helps:** Most thread schedulers respect the yield hint and give other waiting threads a chance to run. This prevents one thread from monopolizing the CPU and creates more interleaving.

## Problem 4: Adding Thread.sleep(500) After Each Character (10 pts)

**Modification:**
```java
public void run () {
    for (int i = 0; i < times; i++) {
        System.out.print (ch);
        Thread.sleep(500);  // Sleep for 500 milliseconds
    }
}
```

**Output Difference:** The entire program takes much longer to execute (200 iterations × 500ms = 100 seconds per thread). The output appears ONE CHARACTER AT A TIME with half-second pauses between them:
```
a
X
+
.
a
X
+
.
...
```

**Explanation:** When a thread sleeps, it relinquishes the CPU for the specified duration. All four threads sleep for 500ms after printing each character, so the total execution takes approximately 100 seconds. The sleep makes it very likely that different threads will alternate printing before resuming, resulting in much more even distribution (often exactly one character per thread per round due to the long sleep periods).

**Why the Effect:** The sleep creates natural synchronization points. By the time one thread wakes up from its 500ms sleep, the other threads have typically completed their print+sleep cycle, allowing fair turn-taking.

## Problem 5: Adding Thread.sleep(500) After Each Thread Creation (10 pts)

**Modification:**
```java
public static void main (String args []) {
    String [] sa = {"a", "X", "+", "."};
    for (String s: sa) {
        Runnable ps = new PrintChar (s, 200);
        Thread ts = new Thread (ps, s);
        ts.start ();
        Thread.sleep(500);  // Sleep for 500 milliseconds
    }
}
```

**Output Difference:** The output shows mostly sequential behavior at the start, gradually becoming more interleaved. The first 200 characters printed will be all 'a's before switching to 'X', but concurrent threads will eventually interleave:
```
aaaaaaaaaa...aaaa (all 200 'a's mostly) XXXXXXXXXX... (X's start, some 'a's still printing) +++++... (interleaving intensifies)
```

**Explanation:** The 500ms sleep is in the *main thread* between creating threads, not within the worker threads. This creates threads one at a time, with 500ms between each creation. So:
- Thread for 'a' starts and runs for 500ms before Thread for 'X' is created (allowing 'a' to print 100-200 times)
- Thread for 'X' starts while 'a' is still running after 500ms
- Thread for '+' starts after another 500ms
- Thread for '.' starts after another 500ms
- By the time '.' thread starts (~1.5 seconds), all other threads have been running concurrently for a while

**Why the Effect:** The staggered thread creation gives each thread a head start before competition begins, creating output that appears sequential initially then becomes increasingly interleaved as more threads activate.

