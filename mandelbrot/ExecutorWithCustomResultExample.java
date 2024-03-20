import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

class TaskResult {
    private final byte[][] byteArray;
    private final int intValue;

    public TaskResult(byte[][] byteArray, int intValue) {
        this.byteArray = byteArray;
        this.intValue = intValue;
    }

    public byte[][] getByteArray() {
        return byteArray;
    }

    public int getIntValue() {
        return intValue;
    }
}

public class ExecutorWithCustomResultExample {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<TaskResult>> futures = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            Future<TaskResult> future = executor.submit(() -> {
                // Simulate some time-consuming task
                Thread.sleep(1000);

                // Replace this with your actual logic to generate byte[][] and int
                byte[][] byteArray = {{1, 2}, {3, 4}};
                int intValue = taskId * 10;

                return new TaskResult(byteArray, intValue);
            });

            futures.add(future);
        }

        for (Future<TaskResult> future : futures) {
            try {
                TaskResult result = future.get();
                byte[][] byteArray = result.getByteArray();
                int intValue = result.getIntValue();

                // Process the results as needed
                System.out.println("Received result with intValue: " + intValue);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }
}
