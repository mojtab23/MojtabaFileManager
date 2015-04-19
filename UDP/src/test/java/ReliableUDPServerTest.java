import reliableudp.MyTask;
import reliableudp.ReliableUDPServer;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by mojtab23 on 4/4/15.
 */
public class ReliableUDPServerTest {

//    @Test


    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(10);
        ReliableUDPServer reliableUDPServer = null;
        try {
            reliableUDPServer = new ReliableUDPServer(10024);
            reliableUDPServer.setConnectionHandler(
                    connection -> executor.execute(
                            new MyTask(connection) {
                                @Override
                                public void run() {
                                    while (true) {

                                        try {
                                            System.out.println(connection);
                                            System.out.println(connection.getInputStream().read());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            }));
            reliableUDPServer.accept();
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

}