import org.jcsp.lang.*;
import org.jcsp.util.*;
/** Main program class for Producer/Consumer example.
  * Sets up channel, creates one of each process then
  * executes them in parallel, using JCSP.
  */
public final class PCMain
  {
    public static void main (String[] args)
      { new PCMain();
      } // main

    public PCMain ()
      { // Create channel object
        OverWriteOldestBuffer buffer = new OverWriteOldestBuffer(10);
        final Any2AnyChannel channel = Channel.any2any(buffer, 10);
        int numRandoms = 5;

        // Create and run parallel construct with a list of processes
        CSProcess[] procList = { 
          new Producer(channel.out(), numRandoms),
          new Producer(channel.out(), numRandoms), 
          //new Consumer(channel.in(), numRandoms),
          new Consumer(channel.in(), numRandoms) 
        }; // Processes
        Parallel par = new Parallel(procList); // PAR construct
        long startTime = System.currentTimeMillis();
        par.run(); // Execute processes in parallel
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;
        System.out.println("Total time taken: "+ timeTaken);
      } // PCMain constructor

  } // class PCMain
