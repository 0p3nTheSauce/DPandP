import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class SumArrayTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 2;
    private int[] array;
    private int start;
    private int end;

    public SumArrayTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            int mid = (start + end) / 2;
            SumArrayTask left = new SumArrayTask(array, start, mid);
            SumArrayTask right = new SumArrayTask(array, mid, end);
            invokeAll(left, right);
            return left.join() + right.join();
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8};
        SumArrayTask task = new SumArrayTask(array, 0, array.length);
        int result = pool.invoke(task);
        System.out.println("Sum: " + result);
    }
}
