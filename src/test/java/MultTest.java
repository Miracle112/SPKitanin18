import junit.framework.Assert;
import junit.framework.TestCase;

public class MultTest extends TestCase {

    public void testMultiply() {
        String[] arr = {"1", "2"};
        Mult mult = new Mult();
        int res = (int) mult.multiply(arr);
        Assert.assertEquals(2, res);
    }
    public void testBigNum() {
        String[] arr = {"1230000", "12"};
        Mult mult = new Mult();
        int res = (int) mult.multiply(arr);
        Assert.assertEquals(2, res);
    }
}