public class ArrayQueue<T> {
    private T[] data;
    private int hd, tl;

    public ArrayQueue () { 
        data = (T[])new Object[10];
        hd = tl = -1;
    }// Constructor

    public synchronized void add (T item) throws InterruptedException {
        while (((tl + 1) % data.length) == hd)
            wait(); // No space
        tl = (tl + 1) % data.length;
        data[tl] = item;
        if (hd == -1) // First item in queue
        hd = tl;
        notifyAll();
    } // add

    public synchronized T remove () throws InterruptedException {
        while (hd == -1) // No data
            wait();
        int tmpIndex = hd;
        if (hd == tl) // Was last element
            hd = tl = -1;
        else
            hd = (hd + 1) % data.length;
        notifyAll();
        return data[tmpIndex];
    } // remove

} // class ArrayQueue