package net.anotheria.moskito.aop;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.aop.annotation.Accumulates;
import net.anotheria.moskito.aop.annotation.Monitor;

/**
 * Created by Roman Stetsiuk on 9/29/15.
 */
@Monitor
@Accumulates({
        @Accumulate(valueName = "Second", intervalName = "1m"),
        @Accumulate(valueName = "Third", intervalName = "1m")})

@Accumulate(valueName = "First", intervalName = "1m")
public class AccumulatesAnnotatedClass {

    public void notAnnotatedFunc(){};

    @Accumulate(valueName = "AnnotatedFunc", intervalName = "1m")
    public void annotatedFunc(){};
}
