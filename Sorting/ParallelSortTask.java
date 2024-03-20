import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.util.Random;

public class ParallelSortTask extends RecursiveAction {
    private static final int THRESHOLD = 1000;
    private Integer[] array;
    private int start, end;

    public ParallelSortTask(Integer[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    public static Integer[] fillArr(int size) { //fill array with random numbers
        Random random = new Random();
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) 
            arr[i] = random.nextInt(size);
        return arr;
    } //fillArr

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            // If the task is small enough, perform sequential sort
            Arrays.sort(array, start, end);
        } else {
            // Split the task into two sub-tasks
            int mid = (start + end) >>> 1;
            ParallelSortTask leftTask = new ParallelSortTask(array, start, mid);
            ParallelSortTask rightTask = new ParallelSortTask(array, mid, end);

            // Fork the sub-tasks
            leftTask.fork();
            rightTask.fork();

            // Join to wait for the completion of the sub-tasks
            leftTask.join();
            rightTask.join();

            // Merge the results (not applicable for sorting)
        }
    }

    public static void main(String[] args) {
        // Example usage
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        Integer[] array = fillArr(1000);

        ParallelSortTask sortTask = new ParallelSortTask(array, 0, array.length);
        forkJoinPool.invoke(sortTask);

        // Now the array is sorted in parallel
        System.out.println(Arrays.toString(array));
    }
}
