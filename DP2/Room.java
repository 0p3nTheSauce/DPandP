import org.jcsp.lang.*;
//import java.util.Arrays;

public class Room implements CSProcess {
    private ChannelInput philsIn;
    private ChannelOutput philsOut;
    int numPhilsInside;
    int totalPhils;

    public Room (final ChannelInput philsIn, 
    final ChannelOutput philsOut, int totalPhils){
        this.philsIn = philsIn;
        this.philsOut = philsOut;
        this.numPhilsInside = 0;
        this.totalPhils = totalPhils; 
    }

    public void run() {
        boolean entering = false; //true is a request to enter, false a request to leave
        int index = 0;
        boolean enter = true;
        while(true) {
            entering = (boolean) philsIn.read();
            if (entering){ // a request to enter
                if (numPhilsInside < 4){ 
                    //there is space, access granted
                    numPhilsInside++;
                    System.out.println("Room: numPhils: "+numPhilsInside);
                } else {
                    //access denied
                    enter = false;
                }
                
            } else { // a request to leave
                numPhilsInside--;
                enter = true; // open up a space 
                System.out.println("Room: numPhils: "+numPhilsInside);
            }
            philsOut.write(enter); // notify philosophers
        }
    }
}