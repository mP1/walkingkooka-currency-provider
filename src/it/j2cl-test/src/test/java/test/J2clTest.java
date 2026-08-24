package test;


import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Assert;
import org.junit.Test;

@J2clTestInput(J2clTest.class)
public class J2clTest {

    // TODO verify Class.cast is emulated
    @Test
    public void testAssertEquals() {
        Assert.assertEquals(
            1,
            1
        );
    }
}
