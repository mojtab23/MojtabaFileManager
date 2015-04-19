import org.junit.Assert;
import org.junit.Test;
import reliableudp.MyInputStream;

import java.util.Arrays;

/**
 * Created by Mojtaba on 3/29/2015.
 */
public class MyBufferTest {


    @Test
    public void testSimpleIO() throws Exception {
        MyInputStream myInputStream = new MyInputStream();
        myInputStream.put(true, toBytesObject(new byte[]{10, 15}));
        int read = myInputStream.read();


        Assert.assertEquals(10, read);
        read = myInputStream.read();
        Assert.assertEquals(15, read);
        myInputStream.put(false, toBytesObject(new byte[]{20, 25}));
        read = myInputStream.read();
        Assert.assertEquals(20, read);
        read = myInputStream.read();
        Assert.assertEquals(25, read);
        read = myInputStream.read();
        Assert.assertEquals(-1, read);


    }

    Byte[] toBytesObject(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        Arrays.setAll(bytes, n -> bytesPrim[n]);
        return bytes;
    }
}