package net.anotheria.moskito.core.dynamic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dheid on 28.05.16.
 */
public class ProxyUtilsTest {

    private interface TestInterfaceA {

    }

    private interface TestInterfaceB {

    }

    private interface TestInterfaceC {

    }

    @Test
    public void createInstance() throws Exception {
        Class interf = TestInterfaceA.class;
        Class[] additionalClasses = new Class<?>[] {
                TestInterfaceB.class,
                TestInterfaceC.class
        };
        Class[] mergedInterfaces = ProxyUtils.mergeInterfaces(interf, additionalClasses);

        assertEquals(3, mergedInterfaces.length);
        assertSame(TestInterfaceA.class, mergedInterfaces[0]);
        assertSame(TestInterfaceB.class, mergedInterfaces[1]);
        assertSame(TestInterfaceC.class, mergedInterfaces[2]);
    }

}