import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Interface extends Remote {
    // Remote methods declaration
    //public String doSomething(int opt, String message) throws RemoteException;
    public String loginUser(String username) throws RemoteException;
    public String sendMessage(String username, String message) throws RemoteException;
    public List<String> recieveMessages(String username) throws RemoteException;
    public String deleteMessages(String username) throws RemoteException;

}
