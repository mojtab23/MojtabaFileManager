import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Mojtaba on 3/26/2015.
 */
public class UDP {

    private static final int TIMEOUT = 10000;
    private int port;
    private DatagramSocket sendSocket;
    private DatagramSocket receiveSocket;
    private InetAddress address;

    public UDP(String hostName, int port) throws UnknownHostException, SocketException {
        this.address = InetAddress.getByName(hostName);
        this.port = port;
        sendSocket = new DatagramSocket(port);
    }

    public boolean send(BufferedInputStream stream, int size) throws FileNotFoundException {

        return false;
    }

}
