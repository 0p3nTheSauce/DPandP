import org.jcsp.lang.*;

/** Consumer class: reads one int from input channel, displays it, then
  * terminates.
  */
public class Consumer implements CSProcess
  { private ChannelInput channel;
    private int numRandoms;

    public Consumer (final ChannelInput in, int num)
      { channel = in;
        numRandoms = num*2;
      } // constructor

    public void run ()
      {   System.out.println("Beginning Consumption");
          for (int i = 0; i < numRandoms; i++) {
          System.out.println("Recieving input");
          Integer item = (Integer)channel.read();
          System.out.println("Processing input...");

          try {
            Thread.sleep(1000); // Sleep for 2000 milliseconds (2 seconds)
          } catch (InterruptedException e) {
              e.printStackTrace();
          }

          System.out.println(item);
         }

         System.out.println("Finishing Consumption");
      } // run

  } // class Consumer
