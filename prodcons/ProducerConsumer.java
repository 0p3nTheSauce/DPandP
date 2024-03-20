import java.util.concurrent.Exchanger;

public class ProducerConsumer {
    private static final int NUM_ITERS = 10;

    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();

        // Create threads
        Producer producer = new Producer(exchanger);
        Consumer consumer = new Consumer(exchanger);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        System.out.println("Starting threads...");
        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Threads finished.");
    }

    private static class Producer implements Runnable {
        private final Exchanger<Integer> exchanger;

        public Producer(Exchanger<Integer> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_ITERS; i++) {
                int producedData = (int)(Math.random() * 100);
                System.out.println(Thread.currentThread().getName() + ": Produced " + producedData);

                try {
                    // Exchange data with the consumer
                    exchanger.exchange(producedData);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Consumer implements Runnable {
        private final Exchanger<Integer> exchanger;

        public Consumer(Exchanger<Integer> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_ITERS; i++) {
                try {
                    // Exchange data with the producer
                    int consumedData = exchanger.exchange(null);
                    System.out.println(Thread.currentThread().getName() + ": Consumed " + consumedData);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
