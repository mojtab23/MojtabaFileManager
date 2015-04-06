import java.io.*;
import java.net.InetAddress;

/**
 * Created by Mojtaba on 3/29/2015.
 */
public class Connection {

    private InetAddress address;
    private int port;
    private MyBuffer buffer;
    private Thread serviceThread;
    private String requestPath;
    private String putType;
    private long putSize;

    public Connection(InetAddress address, int port) {
        this.address = address;
        this.port = port;
        buffer = new MyBuffer();

        // run service in new thread.

//        serviceThread = new Thread(doService());

    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    private Runnable doService() {
        return () -> {

//            StringBuffer header = new StringBuffer();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            boolean isHeader = true;
            int nextByte = 0;
            try {
                nextByte = buffer.read();
                BufferedReader in = new BufferedReader(new InputStreamReader(buffer));
                String s = in.readLine();
                System.out.println(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

    }

//    private int readHeader(ByteArrayOutputStream stream) {
//        String header = new String(stream.toByteArray(), StandardCharsets.UTF_8);
//        String[] lines = header.split("\\n");
//        String[] lineOne = lines[0].split(" ");
//        if (lineOne[0].equals("GET")) {
//
//            requestPath = lineOne[1];
//
//        } else if (lineOne[0].equals("PUT")) {
//
//            requestPath = lineOne[1];
//            putType = lines[1];
//            putSize = Long.parseLong(lines[2].trim());
//
//        }
//    }


    public void put(DataPacket packet) {


        if (buffer.put(packet.getSeq(), packet.getData())) {
            sendAck(packet.getSeq());
        }
    }

    private void sendAck(long seq) {
        System.out.println("packet:" + seq + "received.");
    }


    public InputStream getInputStream() {
        return buffer;
    }

    @Override
    public boolean equals(Object obj) {
        Connection obj1 = (Connection) obj;
        InetAddress address = obj1.getAddress();
        int port = obj1.getPort();
        return address.equals(this.getAddress()) && port == this.getPort();
    }
}
