import org.jcsp.lang.*;

public class Phil implements CSProcess {
    private ChannelOutput roomOut;
    private ChannelInput roomIn;
    private ChannelOutput forkL;
    private ChannelOutput forkR;
    private int name;

    public Phil (final int name, final ChannelOutput forkL,
        final ChannelOutput forkR, final ChannelOutput roomOut,
        final ChannelInput roomIn) {
        this.roomOut = roomOut;
        this.roomIn = roomIn;
        this.forkL = forkL;
        this.forkR = forkR;
        this.name = name;
    }

    private void think() {
        System.out.println("Philosopher: " + name + " hmmmmm...");
        try {
            Thread.sleep(2000); // Think for 2000 milliseconds (2 seconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void enter() {
        System.out.println("Phil: "+name+" entering room.");
    }

    private void pickUp(String fork) {
        if (fork == "Left")
            forkL.write(name);
        else if (fork == "Right")
            forkR.write(name); 
        else 
            System.out.println("Error can't pick up " + fork); 
    }

    private void putDown (String fork) {
        if (fork == "Left")
            forkL.write(0);
        else if (fork == "Right")
            forkR.write(0); 
        else 
            System.out.println("Error can't put down " + fork); 
    }

    private void eat() {
        System.out.println("Philosopher: " + name + " nom nom nom...");
        try {
            Thread.sleep(2000); // Eat for 2000 milliseconds (2 seconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        System.out.println("Phil: "+name+" exiting room.");
    }

    public void run() {
        boolean requestEnter = true; 
        boolean requestExit = false;
        boolean canEnter = false;
        while (true) {
            think();
            roomOut.write(requestEnter); //request to enter the room 
            canEnter = (boolean)roomIn.read();
            if (canEnter){
                //access granted 
                enter(); 
            } else {
                //access denied, wait for permission to enter 
                canEnter = (boolean)roomIn.read();
                enter();
            }
            pickUp("Left");
            pickUp("Right");
            eat();
            putDown("Left");
            putDown("Right");
            roomOut.write(requestExit); //notify room of departure 
            exit();      
            
        }

    }



}