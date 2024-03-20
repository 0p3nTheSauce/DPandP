public class PickUp {
    private int philosopher;
    private boolean up;

    public PickUp(int namePhil, boolean pickedUp) {
        this.philosopher = namePhil;
        this.up = pickedUp;
    }

    public int getPhilosopher() {
        return philosopher;
    }

    public boolean isUp() {
        return up;
    }

    public void setState(boolean held){
        this.up = held;
    }

    public void setPhilosopher(int phil){
        this.philosopher = phil;
    }
}