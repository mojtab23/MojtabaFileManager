import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Mojtaba on 3/26/2015.
 */
public class MyBuffer extends InputStream {

    public static final int WINDOW_SIZE = 20;
    private byte[][] window = new byte[WINDOW_SIZE][];
    private int period = 0;//
    private int empty = WINDOW_SIZE;//جا خالی
    private Queue<Long> missed = new ConcurrentLinkedQueue<>();
    private int mark = 0;

    private int dataMark = 0;

    @Override
    public int read() throws IOException {

        byte[] bytes = window[mark];
        if (dataMark >= bytes.length) {
            dataMark = 0;
            mark++;
        }
        bytes = window[mark];

        return bytes[dataMark++];
    }


    public int getEmpty() {
        return empty;
    }

    public long getNextMissed() {
        if (missed.peek() == null)
            return -1;
        return missed.poll();
    }

    public boolean put(long seq, byte[] data) {

        if (seq < mark || seq >= mark + WINDOW_SIZE) {
            return false;
        }
        window[((int) (seq % WINDOW_SIZE))] = data;

        return true;
    }

}
