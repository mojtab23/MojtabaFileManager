import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Mojtaba on 3/29/2015.
 */
public class ReliableUDPServer {

    private final static int PORT = 10024;
    private Map<Integer, Connection> clients = new ConcurrentHashMap<>();
    private ConnectionHandler connectionHandler;

    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public void accept() {
        Thread thread = new Thread(() -> {


            try (DatagramSocket socket = new DatagramSocket(PORT)) {
                while (true) {
                    try {
                        DataPacket packet = new DataPacket();
                        DatagramPacket request = receive(socket, packet);
                        //when received create new session whit new Thread.

                        if (packet.getConnection() == 0)
                            createNewConnection(socket, request.getAddress(), request.getPort(), packet);
//                    else sendToSession(packet);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            } catch (SocketException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private DatagramPacket receive(DatagramSocket socket, DataPacket packet) throws IOException {
        DatagramPacket request = new DatagramPacket(packet.getBytes(), DataPacket.PACKET_SIZE);
        socket.receive(request);
        System.out.println("data received");
        return request;
    }

    private void createNewConnection(DatagramSocket socket, InetAddress address, int port, DataPacket packet) {
        Random random = new Random();
        final int[] connectionId = new int[1];
        Connection connection = new Connection(address, port);
        //if connection exist.
        final boolean[] exist = {false};
        exist[0] = clients.entrySet().stream().anyMatch(integerConnectionEntry -> {
            connectionId[0] = integerConnectionEntry.getKey();
            return integerConnectionEntry.getValue().equals(connection);
        });


        if (!exist[0]) {
            while ((connectionId[0] = random.nextInt(Integer.MAX_VALUE)) == 0 || clients.containsKey(connectionId[0])) ;
            clients.put(connectionId[0], connection);
            connectionHandler.handleConnection(connection);
        }

        sendConnectionAccept(socket, address, port, connectionId[0]);

    }

    private void sendConnectionAccept(DatagramSocket socket, InetAddress address, int port, int connectionId) {

        try {
            DataPacket dataPacket = new DataPacket(0, 0, connectionId, null);
            DatagramPacket packet = new DatagramPacket(dataPacket.getBytes(), dataPacket.getLimit(), address, port);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}