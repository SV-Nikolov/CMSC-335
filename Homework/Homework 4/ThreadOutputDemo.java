public class ThreadOutputDemo {
    private static final String[] CHARACTERS = {"a", "X", "+", "."};
    private static final int TIMES = 20;

    public static void main(String[] args) throws Exception {
        String mode = args.length > 0 ? args[0] : "baseline";

        switch (mode) {
            case "baseline" -> runWithStart(false, false, false);
            case "run" -> runWithRun();
            case "yield" -> runWithStart(true, false, false);
            case "sleepChar" -> runWithStart(false, true, false);
            case "sleepMain" -> runWithStart(false, false, true);
            default -> throw new IllegalArgumentException("Unknown mode: " + mode);
        }
    }

    private static void runWithStart(boolean useYield, boolean sleepAfterPrint, boolean sleepInMain) throws Exception {
        for (String character : CHARACTERS) {
            Runnable task = new PrintChar(character, TIMES, useYield, sleepAfterPrint);
            Thread thread = new Thread(task, character);
            thread.start();
            if (sleepInMain) {
                Thread.sleep(500);
            }
        }
    }

    private static void runWithRun() {
        for (String character : CHARACTERS) {
            Runnable task = new PrintChar(character, TIMES, false, false);
            task.run();
        }
    }

    private static class PrintChar implements Runnable {
        private final String ch;
        private final int times;
        private final boolean useYield;
        private final boolean sleepAfterPrint;

        private PrintChar(String ch, int times, boolean useYield, boolean sleepAfterPrint) {
            this.ch = ch;
            this.times = times;
            this.useYield = useYield;
            this.sleepAfterPrint = sleepAfterPrint;
        }

        @Override
        public void run() {
            for (int i = 0; i < times; i++) {
                System.out.print(ch);
                if (useYield) {
                    Thread.yield();
                }
                if (sleepAfterPrint) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        }
    }
}
