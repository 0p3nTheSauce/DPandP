import org.jcsp.lang.*;

public class Philosopher implements CSProcess {
    private ChannelOutput pickLeft;
    private ChannelOutput pickRight;
    private ChannelInput readRoom;
    private int name;

    public Philosopher (final int id, final ChannelOutput pickUpLeft, 
        final ChannelOutput pickUpRight, final ChannelInput room) {
            name = id;
            pickLeft = pickUpLeft;
            pickRight = pickUpRight;
            readRoom = room;
        } //constructor 

    
    
    public void run() {
        //anounce entrance
        System.out.println("Philosopher: " + name + " has entered room");
        int nextToEat = 0;
        int pickedUpBy = 0;
        PickUp request = new PickUp(name, true);
        //begin philosophising
        while(true) {
            //read room to see if I should pick up my forks
            nextToEat = (Integer)readRoom.read();
            if (nextToEat == name){
                //try pick up forks
                request.setState(true);
                pickLeft.write(request);
                pickRight.write(request);
            } else {
                //dont try to pick up forks
                request.setState(false);
                pickLeft.write(request);
                pickRight.write(request);
            }
            pickedUpBy = (Integer)readRoom.read();
            if (pickedUpBy == name) {
                //i picked them up
                System.out.println("Philosopher: " + name + " nom nom nom...");
                try {
                    Thread.sleep(2000); // Sleep for 2000 milliseconds (2 seconds)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Philosopher: " + name + " finished eating");
                //put down my forks
                request.setState(false);
                pickLeft.write(request);
                pickRight.write(request);
            } else {
                // someone else is holding them ill think instead
                System.out.println("Philosopher: " + name + " hmmmmm...");
                try {
                    Thread.sleep(2000); // Sleep for 2000 milliseconds (2 seconds)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Philosopher: " + name + " finished thinking");
            }
        } 
    }
}