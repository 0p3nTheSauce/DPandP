import java.util.Random;

public class Producer implements Runnable {

    private int numNums; // number of random numbers produced 

    public Producer(int numNums) {
        this.numNums = numNums;
    }

    
    public void run() {
        //Create a Random object 
        Random random = new Random();

        // Generate a random integer 
        int randomInt = random.nextInt(100);

        for (int i = 0; i < numNums; i++) {
            System.out.println(randomInt);
            randomInt = random.nextInt(100);
        }
    }

}