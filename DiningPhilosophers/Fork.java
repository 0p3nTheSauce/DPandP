import org.jcsp.lang.*;

public class Fork implements CSProcess {
    private ChannelInput in1;
    private ChannelInput in2;
    private ChannelOutput room;
    private boolean inUse;
    private int heldBy;
    private String name;

    public Fork(final String name, final ChannelInput in1, final ChannelInput in2,
        final ChannelOutput room) {
        this.in1 = in1;
        this.in2 = in2;
        this.room = room;
        this.name = name;
        this.inUse = false;
        this.heldBy = 0;
    } //constructor 

    public void run() {
        System.out.println("Fork: "+name+"is on table");
        PickUp request1 = new PickUp(0, false);
        PickUp request2 = new PickUp(0, false);
        PickUp notify = new PickUp(0, false);
        while (true) {
            request1 = (PickUp)in1.read();
            request2 = (PickUp)in2.read();
            if (request1.isUp() && !request2.isUp()) {
                //picked up by phil 1
                heldBy = request1.getPhilosopher();
                inUse = true;
            } else if (!request1.isUp() && request2.isUp()) {
                //picked up by phil 2
                heldBy = request2.getPhilosopher();
                inUse = true;
            } else {
                //attempted pick up by both phil, read again
                System.out.println("something went wrong");
            }
            //notify the room
            notify.setState(inUse);
            notify.setPhilosopher(heldBy);
            room.write(notify);
        }
    }
}