import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {
    private String namapengguna;
    private ServerInt chatServer; // untuk menyimpan referensi ke server tambahkan atribut 

    public void receiveMessage(String message, String senderUsername) throws RemoteException {
        if (!senderUsername.equals(namapengguna)) {
            System.out.println(senderUsername + ": " + message);
        }
    }

    protected ChatClient(String namapengguna, ServerInt chatServer) throws RemoteException {
        this.namauser = namapengguna;
        this.chatServer = chatServer;
    }
    public static void main(String[] args) {
        try {
            java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry("localhost", 1099);
            ServerInt chatServer = (ServerInt) registry.lookup("ChatServer");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nama pengguna: ");
            String namapengguna = scanner.nextLine();

            ChatClient client = new ChatClient(namapengguna, chatServer);

            // Daftarkan klien 
            try {
                chatServer.registerClient(client, namapengguna);
            } catch (RemoteException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }

            while (true) {
                System.out.print(namapengguna + ": ");
                String message = scanner.nextLine();
                client.sendMessage(message);
            }
            

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        try {
            // Panggil metode sendMessage pada server
            chatServer.sendMessage(message, namapengguna);
        } catch (RemoteException e) {
            System.err.println("Tidak dapat mengirim pesan: " + e.getMessage());
            e.printStackTrace();
        }
    }
}