import java.rmi.Remote;
import java.rmi.RemoteException;

// Antarmuka dengan menggunakan RMI
public interface ClientInt extends Remote {

    void receiveMessage(String message, String username) throws RemoteException;
}