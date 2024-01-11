package me.folgue.jabu.utils;

/**
 * @author folgue
 */
public class Time {
    public static interface RunnableWithResult<T> {
        T run();
    }

    public record RunnableResults<T>(T result, long timeTaken) {
    }
    /**
     * Measures the time that has taken to execute a given operation.
     * @param target Operation to run.
     * @return Milliseconds that the operation has taken.
     */
    public static long measureTime(Runnable target) {
        long startTime = System.currentTimeMillis();
        target.run();
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
   
    public static <T> RunnableResults<T> measureTime(RunnableWithResult<T> target) {
        long startTime = System.currentTimeMillis();
        T result = target.run();
        long endTime = System.currentTimeMillis();

        return new RunnableResults(result, endTime - startTime);
    }
}