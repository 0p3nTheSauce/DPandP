import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class Server extends UnicastRemoteObject implements Interface {
    private Map<String, List<String>> users;
    public Server() throws RemoteException {
        this.users = new HashMap<>();
    }// Constructor

    public String loginUser(String username) {
        if (users.containsKey(username)) {
            return "User "+username+" logged in";
        }else {
            List<String> chat = new ArrayList<>();
            this.users.put(username, chat);
            return "User created successfully";
        }
        
    }

    public String sendMessage(String username, String message) {
        if (users.containsKey(username)) {
            List<String> chat = users.get(username);
            chat.add(message);
            return "Message sent";
        } else {
            return "No user with that username exists";
        }
    }

    public List<String> recieveMessages(String username) {
        List<String> chat = new ArrayList<>();
        if (users.containsKey(username)) {
            chat = users.get(username);
        } else {
            String err = "No user with that username exists";
            chat.add(err);
        }
        return chat;
    }

    public String deleteMessages(String username) {
        if (users.containsKey(username)) {
            List<String> chat = users.get(username);
            if (chat.isEmpty()) {
                return "Chat is empty";
            } else {
                Iterator<String> iterator = chat.iterator();
                while (iterator.hasNext()) {
                    iterator.next();
                    iterator.remove();
                }
                return "Chat cleared";
            }
        } else {
            return "No user with that username exists";
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            //MyRemoteInterface stub = (MyRemoteInterface)UnicastRemoteObject.exportObject(server, 0);
            Server stub = new Server();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("MessageService", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }


}
