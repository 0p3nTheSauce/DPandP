import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorWithResultExample {

    public static void main(String[] args) {
        // Create a thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // List to store Future objects representing the results of each task
        List<Future<String>> futures = new ArrayList<>();

        // Submit tasks that return results
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            Future<String> future = executor.submit(() -> {
                // Simulate some time-consuming task
                Thread.sleep(1000);
                return "Result of Task " + taskId;
            });

            futures.add(future);
        }

        // Wait for all tasks to complete and retrieve the results
        for (Future<String> future : futures) {
            try {
                String result = future.get(); // This blocks until the result is available
                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Shutdown the executor when no more tasks will be submitted
        executor.shutdown();
    }
}
