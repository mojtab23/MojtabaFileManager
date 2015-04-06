import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Created by Mojtaba on 4/1/2015.
 */
public class Main {

    public static void main(String[] args) {
        Executor executor = new ThreadPoolTaskExecutor();
        ReliableUDPServer reliableUDPServer = new ReliableUDPServer();
        reliableUDPServer.setConnectionHandler(connection -> executor.execute(() -> {
            //code FTP
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
