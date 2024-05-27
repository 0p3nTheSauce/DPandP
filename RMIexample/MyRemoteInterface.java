import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyRemoteInterface extends Remote {
    // Remote methods declaration
    public String doSomething() throws RemoteException;
}
