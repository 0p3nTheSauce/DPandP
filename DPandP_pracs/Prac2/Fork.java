import org.jcsp.lang.*;

public class Fork implements CSProcess {
    private AltingChannelInput[] phils;
    private String name;
    private int heldBy;


    public Fork (final AltingChannelInput[] phils, String name){
        this.phils = phils;
        this.name = name;    
    }

    private void pickUp(int phil) {
        heldBy = phil;
        System.out.println("Fork: "+name+" held by Phil: "+heldBy);
    }
    
    private void putDown() {
        heldBy = 0;
        System.out.println("Fork: "+name+" put down.");
    }

    public void run(){
        int phil;
        while(true){
            Alternative alt = new Alternative(phils);
            int index = alt.fairSelect(); //choose which philosopher gets to pick up fork
            phil = (Integer) phils[index].read(); pickUp(phil); //philosopher picks up fork
            phil = (Integer) phils[index].read(); putDown(); //same philosopher puts down fork
        }
    }
}