import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) {
        String result = "error";
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            MyRemoteInterface impl = (MyRemoteInterface)registry.lookup("MyRemoteService");
            result = impl.doSomething();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
        System.out.println(result);
        
    }
}