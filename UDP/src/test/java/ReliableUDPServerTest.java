import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by mojtab23 on 4/4/15.
 */
public class ReliableUDPServerTest {

//    @Test


    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(10);
        ReliableUDPServer reliableUDPServer = new ReliableUDPServer();
        reliableUDPServer.setConnectionHandler(
                connection ->
                        executor.execute(
                                () -> {
                                    while (true) {

                                        try {
                                            System.out.println(connection.getInputStream().read());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }));
        reliableUDPServer.accept();
    }

}