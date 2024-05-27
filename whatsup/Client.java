import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Client {

    private static Scanner scanner = new Scanner(System.in);

    public static String takeInput(String description) {
        String str = "";
        boolean haveStr = false;


        while (!haveStr) {
            System.out.println(description);
            str = scanner.nextLine();
            if (str.length() > 0) {
                haveStr = true;
            } else {
                System.out.println("No input detected.");
            }
        }
        return str;
        
    }

    public static void send(Interface server){
        String feedback = "error";
        String username = takeInput("Enter username of receipient: ");
        String message = takeInput("Enter your message: ");
        try {
            feedback = server.sendMessage(username, message);
        } catch (RemoteException e){
            e.printStackTrace();
        }
        
        System.out.println(feedback);
    }

    public static void receive(String username, Interface server){
        List<String> chat = new ArrayList<>();
        try {
            chat = server.recieveMessages(username);
        } catch(RemoteException e) {
            e.printStackTrace();
        }
        for (String item : chat) {
            System.out.println(item);
        }
    }

    public static void delete(String username, Interface server) {
        String feedback = "error";
        try {
            feedback = server.deleteMessages(username);
        } catch (RemoteException e){
            e.printStackTrace();
        }
        System.out.println(feedback);
    }

    public static void main(String[] args) {
        String message = "";
        boolean busy = true;
        System.out.println("Client started");
        System.out.println("Instructions: ");
        System.out.println("To send messages: type send");
        System.out.println("To receive messages: type receive");
        System.out.println("To delete received messages: type delete");
        System.out.println("To close program: type quit");
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            Interface server = (Interface)registry.lookup("MessageService");
            String username = takeInput("Please enter username: ");
            String feedback = server.loginUser(username);
            System.out.println(feedback);
            while(busy) {
                String action = takeInput("");
                switch (action){
                    case "send":
                        send(server);
                        break;
                    case "receive":
                        receive(username, server);
                        break;
                    case "delete":
                        delete(username, server);
                        break;
                    case "quit":
                        busy = false;
                        break;
                    default:
                        System.out.println("Action not recognised");
                }
            }
            

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}