import org.jcsp.lang.*;

/** Producer class: produces one random integer and sends on
  * output channel, then terminates.
  */
public class Producer implements CSProcess
  { private ChannelOutput channel;
    private int numRandoms;

    public Producer (final ChannelOutput out, final int num)
      { channel = out;
        numRandoms = num;
      } // constructor

    public void run ()
      { System.out.println("Beginning production");
        for (int i = 0; i < numRandoms; i++) {
          System.out.println("Sending input");
          int item = (int)(Math.random()*100)+1;
          channel.write(item);
        }
        System.out.println("Finished production");
      } // run

  } // class Producer
