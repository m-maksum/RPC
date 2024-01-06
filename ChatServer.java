import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatServer extends UnicastRemoteObject implements ServerInt {
    private Map<Clienint, String> clients;

    protected ChatServer() throws RemoteException {
        clients = new HashMap<>();
    }

    public void sendMessage(String message, String username) throws RemoteException {
        System.out.println(username + " : " + message);

        // Mengirim pesan ke clien 
        Iterator<Map.Entry<ClienInt, String>> iterator = clients.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ClienInt, String> entry = iterator.next();
            ClienInt client = entry.getKey();
            try {
                String clientUsername = entry.getValue();

                // Hanya kirim pesan ke clien 
                if (!clientUsername.equals(username)) {
                    client.receiveMessage(message, username);
                }
            } catch (RemoteException e) {
                iterator.remove();
                System.out.println("Disconect.");
            }
        }
    }

    public void registerClient(ClienInt client, String username) throws RemoteException {
        clients.put(client, username);
        System.out.println(username + " Connected.");
    }
    
    public static void main(String[] args) {
        try {
            ChatServer chatServer = new ChatServer();
            java.rmi.registry.LocateRegistry.createRegistry(1099).rebind("ChatServer", chatServer);
            System.out.println("ChatServer is running.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}