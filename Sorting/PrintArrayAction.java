import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class PrintArrayAction extends RecursiveAction {
    private static final int THRESHOLD = 2;
    private int[] array;
    private int start;
    private int end;

    public PrintArrayAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                System.out.println(array[i]);
            }
        } else {
            int mid = (start + end) / 2;
            PrintArrayAction left = new PrintArrayAction(array, start, mid);
            PrintArrayAction right = new PrintArrayAction(array, mid, end);
            invokeAll(left, right);
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8};
        PrintArrayAction action = new PrintArrayAction(array, 0, array.length);
        pool.invoke(action);
    }
}
