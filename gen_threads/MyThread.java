public class MyThread implements Runnable {
    public void run() {
        //System.out.println("hello from a thread!");
        String threadName = Thread.currentThread().getName();
        for (int i = 0; i < 10; i++) {
            System.out.println(threadName);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //interupted 
                return;
            }
        }
    }

    public static void main(String args[]) 
        throws InterruptedException {
        Thread thread1 = new Thread(new MyThread());
        thread1.setName("Thread-1");
        thread1.setPriority(3);
        Thread thread2 = new Thread(new MyThread());
        thread2.setName("Thread-2");
        thread2.setPriority(2);
        Thread thread3 = new Thread(new MyThread(), "Thread-4");
        thread3.setName("Thread-3");
        thread3.setPriority(1);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Main thread finished");
    }
}