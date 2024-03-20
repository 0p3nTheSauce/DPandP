import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

public class ParallelSort<T extends Comparable<T>> extends RecursiveAction {
    private static final int THRESHOLD = 1000;
    private T[] list;
    private int start, end;

    public ParallelSort(int start, int end, T[] list) {
        this.start = start;
        this.end = end;
        this.list = list;
    }

    @SuppressWarnings("unchecked")
    private static int partition (Comparable[] list, int start, int end)
      { int left = start,
            right = end;
        Comparable tmp;
        while (left < right)
          { // Work from right end first
            while (list[right].compareTo(list[start]) > 0)
              right--;
            // Now work up from start
            while (left < right && list[left].compareTo(list[start]) <= 0)
              left++;
            if (left < right)
              { tmp = list[left];
                list[left] = list[right];
                list[right] = tmp;
              }
          }
        // Exchange the partition element with list[right]
        tmp = list[start];
        list[start] = list[right];
        list[right] = tmp;
        return right;
      } // partition

    private static void recursiveQS (Comparable[] list, int start, int end)
      { if (start < end)
          { int partitionPoint = partition(list, start, end);
            recursiveQS(list, start, partitionPoint-1);
            recursiveQS(list, partitionPoint+1, end);
          }
      } // recursiveQS

    @Override
    protected void compute() {
        if (start < end) {
            if (end - start <= THRESHOLD) {
                recursiveQS(list, start, end);
            } else {
                int partitionPoint = partition(list, start, end);
                ParallelSort<T> leftTask = new ParallelSort<>(start, partitionPoint-1, list);
                ParallelSort<T> rightTask = new ParallelSort<>(partitionPoint+1, end, list);

                leftTask.fork();
                rightTask.fork();

                leftTask.join();
                rightTask.join();
            }
        }
    }


    public static <T extends Comparable<T>> void quickSort(T[] list) {
        ForkJoinPool pool = new ForkJoinPool();
        ParallelSort<T> task = new ParallelSort<>(0, list.length - 1, list);
        pool.invoke(task);
        pool.shutdown();
    }
}
