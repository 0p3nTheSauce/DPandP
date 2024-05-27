import java.util.concurrent.*;

public class CallableExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Create a Callable task that returns a string
        Callable<String> task = () -> {
            Thread.sleep(1000); // Simulate some computation
            return "Task Completed";
        };

        // Submit the task to the executor
        Future<String> future = executor.submit(task);

        // Do some other work
        System.out.println("Doing some other work...");

        // Get the result of the Callable task
        String result = future.get(); // This blocks until the task completes
        System.out.println(result); // Output: Task Completed

        // Shut down the executor
        executor.shutdown();
    }
}
