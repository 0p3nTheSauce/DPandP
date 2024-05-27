import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MyRemoteImplementation extends UnicastRemoteObject implements MyRemoteInterface {
    public MyRemoteImplementation() throws RemoteException {
        // Constructor
    }

    public String doSomething() throws RemoteException {
        // Implementation of the remote method
        return "Result of remote method";
    }

    public static void main(String[] args) {
        try {
            MyRemoteImplementation server = new MyRemoteImplementation();
            //MyRemoteInterface stub = (MyRemoteInterface)UnicastRemoteObject.exportObject(server, 0);
            MyRemoteImplementation stub = new MyRemoteImplementation();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("MyRemoteService", stub);

            System.err.println("MY server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }


}
