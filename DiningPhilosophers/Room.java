import org.jcsp.lang.*;

public class Room implements CSProcess{
    final ChannelOutput phil1;
    final ChannelOutput phil2;
    final ChannelInput forkA;
    final ChannelInput forkB;
    

    public Room(final ChannelOutput phil1, final ChannelOutput phil2,
        final ChannelInput forkA, final ChannelInput forkB){
        this.phil1 = phil1;
        this.phil2 = phil2;
        this.forkA = forkA;
        this.forkB = forkB;
    }

    public void run(){
        int nextToEat = 1;
        int currentlyEating = 1;
        PickUp noteA = new PickUp(0, false);
        PickUp noteB = new PickUp(0, false);
        while(true){
            phil1.write(nextToEat);
            phil2.write(nextToEat);
            noteA = (PickUp)forkA.read();
            noteB = (PickUp)forkB.read();
            if (noteA.getPhilosopher() == 1 && noteB.getPhilosopher() == 1
                && noteA.isUp() && noteB.isUp()) {
                //philosopher 1 is eating
                currentlyEating = 1;
            } else if (noteA.getPhilosopher() == 2 && noteB.getPhilosopher() == 2
            && noteA.isUp() && noteB.isUp()) {
                //philosopher 2 is eating 
                currentlyEating = 2;
            } else {
                System.out.println("Something went wrong...");
                System.out.println("ForkA: up, held by "+ noteA.isUp() + noteA.getPhilosopher());
                System.out.println("ForkB: up, held by "+ noteB.isUp() + noteB.getPhilosopher());
            }
            phil1.write(currentlyEating);
            phil2.write(currentlyEating);
            if(nextToEat == 1) {
                nextToEat = 2;
            } else {
                nextToEat = 1;
            }
        }
    } 
}

    




