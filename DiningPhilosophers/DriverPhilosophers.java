import org.jcsp.lang.*;
import org.jcsp.util.*;

public class DriverPhilosophers  {

    public static void main(String[] args) {
        //room channels
        final One2OneChannel phil1 = Channel.one2one();
        final One2OneChannel phil2 = Channel.one2one();
        final One2OneChannel forkA = Channel.one2one();
        final One2OneChannel forkB = Channel.one2one();

        //phil and fork channels
        final One2OneChannel phil1ToForkA = Channel.one2one();
        final One2OneChannel phil1ToForkB = Channel.one2one();
        final One2OneChannel phil2ToForkA = Channel.one2one();
        final One2OneChannel phil2ToForkB = Channel.one2one();


        CSProcess[] procList = {
            new Philosopher(1, phil1ToForkA.out(), phil1ToForkB.out(), phil1.in()),
            new Philosopher(2, phil2ToForkA.out(), phil2ToForkB.out(), phil2.in()),
            new Fork("A", phil1ToForkA.in(), phil2ToForkA.in(), forkA.out()),
            new Fork("B", phil1ToForkB.in(), phil2ToForkB.in(), forkB.out()),
            new Room(phil1.out(), phil2.out(), forkA.in(), forkB.in())
        };

        Parallel par = new Parallel(procList);
        par.run();
    }
}