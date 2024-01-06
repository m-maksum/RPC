import java.rmi.Remote;
import java.rmi.RemoteException;

// Server chat menggunakan RMI
public interface ServerInt extends Remote {

    // Mendaftarkan klien baru ke server
    void registerClient(ServerInt client, String namapengguna) throws RemoteException;

    // Mengirim pesan ke klien 
    void sendMessage(String message, String namapengguna) throws RemoteException;
}