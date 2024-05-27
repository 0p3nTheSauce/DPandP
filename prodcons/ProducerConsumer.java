import java.util.concurrent.Exchanger;

public class ProducerConsumer {
    private static final int NUM_ITERS = 10;

    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();

        // Create threads
        Producer producer = new Producer(exchanger);
        Consumer consumer = new Consumer(exchanger);
        //Creat another producer
        Producer producer2 = new Producer(exchanger);
        //Creat another consuemr
        Consumer consumer2 = new Consumer(exchanger);

        Thread producerThread = new Thread(producer);
        Thread producThread2 = new Thread(producer2);
        Thread consumerThread = new Thread(consumer);
        Thread consumerThread2 = new Thread(consumer2);

        System.out.println("Starting threads...");
        producerThread.start();
        producThread2.start();
        consumerThread.start();
        consumerThread2.start();
        try {
            producerThread.join();
            producThread2.join();
            consumerThread.join();
            consumerThread2.join();
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
                    //Slow down producer
                    // Thread.sleep(1000);
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
                    //Slow down consumer
                    // Thread.sleep(1000);
                    int consumedData = exchanger.exchange(null);
                    System.out.println(Thread.currentThread().getName() + ": Consumed " + consumedData);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
