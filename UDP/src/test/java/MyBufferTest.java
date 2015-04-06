import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Mojtaba on 3/29/2015.
 */
public class MyBufferTest {
    @Test
    public void testSimplePut() throws Exception {

        MyBuffer myBuffer = new MyBuffer();
        for (int i = 0; i < 20; i++) {
            boolean b = myBuffer.put(i, new byte[1]);
            Assert.assertTrue(b);


        }
    }

    @Test
    public void test2() throws Exception {
        MyBuffer myBuffer = new MyBuffer();

        boolean b = myBuffer.put(20, new byte[1]);
        Assert.assertFalse(b);


    }

    @Test
    public void testSimpleIO() throws Exception {
        MyBuffer myBuffer = new MyBuffer();
        myBuffer.put(0, new byte[]{10, 15});
        myBuffer.put(1, new byte[]{20, 25});
        int read = myBuffer.read();
        Assert.assertEquals(10, read);
        read = myBuffer.read();
        Assert.assertEquals(15, read);
        read = myBuffer.read();
        Assert.assertEquals(20, read);
        read = myBuffer.read();
        Assert.assertEquals(25, read);

    }
}