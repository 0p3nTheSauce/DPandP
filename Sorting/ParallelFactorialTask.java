import java.util.concurrent.RecursiveAction;

public class ParallelFactorialTask extends RecursiveAction {
    private static final int THRESHOLD = 5; // Threshold to determine when to stop parallelizing
    private int start, end;
    private long[] result;

    public ParallelFactorialTask(int start, int end, long[] result) {
        this.start = start;
        this.end = end;
        this.result = result;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            // If the range is small enough, compute factorials sequentially
            for (int i = start; i <= end; i++) {
                result[i] = factorial(i);
            }
        } else {
            // Split the range into smaller sub-ranges
            int mid = (start + end) / 2;
            ParallelFactorialTask leftTask = new ParallelFactorialTask(start, mid, result);
            ParallelFactorialTask rightTask = new ParallelFactorialTask(mid + 1, end, result);

            // Fork the sub-tasks
            leftTask.fork();
            rightTask.fork();

            // Wait for the sub-tasks to complete
            leftTask.join();
            rightTask.join();
        }
    }

    // Helper method to compute factorial
    private long factorial(int n) {
        long fact = 1;
        for (int i = 2; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    public static void main(String[] args) {
        int n = 10; // Calculate factorials up to n
        long[] result = new long[n + 1]; // Array to store factorial results

        ParallelFactorialTask task = new ParallelFactorialTask(0, n, result);
        task.invoke(); // Execute the task in the current thread

        // Output the factorial results
        for (int i = 0; i <= n; i++) {
            System.out.println(i + "! = " + result[i]);
        }
    }
}
