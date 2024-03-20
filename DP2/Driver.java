import org.jcsp.lang.*;
import org.jcsp.util.*;

public class Driver {
    public static void main(String[] args) {
        //buffer
        OverWriteOldestBuffer buffer = new OverWriteOldestBuffer(10);
        //Philosophers to room channel
        final Any2OneChannel philRoom = Channel.any2one(buffer, 10);
        //Room to philosophers channel
        final One2AnyChannel roomPhil = Channel.one2any(buffer, 10);
        //Philosophers to forks channels
        //phil 1
        final One2OneChannel phil1ToForkA = Channel.one2one();
        final One2OneChannel phil1ToForkE = Channel.one2one();
        //phil 2
        final One2OneChannel phil2ToForkA = Channel.one2one();
        final One2OneChannel phil2ToForkB = Channel.one2one();
        //phil 3
        final One2OneChannel phil3ToForkB = Channel.one2one();
        final One2OneChannel phil3ToForkC = Channel.one2one();
        //phil 4
        final One2OneChannel phil4ToForkC = Channel.one2one();
        final One2OneChannel phil4ToForkD = Channel.one2one();
        //phil 5
        final One2OneChannel phil5ToForkD = Channel.one2one();
        final One2OneChannel phil5ToForkE = Channel.one2one();
        //Fork alting input channels
        //fork A
        AltingChannelInput[] philsA = {
            phil1ToForkA.in(),
            phil2ToForkA.in()
        };
        //fork B
        AltingChannelInput[] philsB = {
            phil2ToForkB.in(),
            phil3ToForkB.in()
        };
        //fork C
        AltingChannelInput[] philsC = {
            phil3ToForkC.in(),
            phil4ToForkC.in()
        };
        //fork D
        AltingChannelInput[] philsD = {
            phil4ToForkD.in(),
            phil5ToForkD.in()
        };
        //fork E
        AltingChannelInput[] philsE = {
            phil5ToForkE.in(),
            phil1ToForkE.in()
        };

        CSProcess[] procList = {
            new Phil(1, phil1ToForkA.out(), phil1ToForkE.out(), philRoom.out(), roomPhil.in()),
            new Phil(2, phil2ToForkB.out(), phil2ToForkA.out(), philRoom.out(), roomPhil.in()),
            new Phil(3, phil3ToForkC.out(), phil3ToForkB.out(), philRoom.out(), roomPhil.in()),
            new Phil(4, phil4ToForkD.out(), phil4ToForkC.out(), philRoom.out(), roomPhil.in()),
            new Phil(5, phil5ToForkE.out(), phil5ToForkD.out(), philRoom.out(), roomPhil.in()),
            new Fork(philsA, "A"),
            new Fork(philsB, "B"),
            new Fork(philsC, "C"),
            new Fork(philsD, "D"),
            new Fork(philsE, "E"),
            new Room(philRoom.in(), roomPhil.out(), 5) 
        };

        Parallel par = new Parallel(procList);
        par.run();
    }
}