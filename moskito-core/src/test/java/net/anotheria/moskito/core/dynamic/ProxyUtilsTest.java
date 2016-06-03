package net.anotheria.moskito.core.dynamic;

import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.junit.Test;

import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.*;

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

        assertThat(mergedInterfaces, arrayWithSize(3));
        assertThat(mergedInterfaces[0] == TestInterfaceA.class, is(true));
        assertThat(mergedInterfaces[1] == TestInterfaceB.class, is(true));
        assertThat(mergedInterfaces[2] == TestInterfaceC.class, is(true));
    }

}