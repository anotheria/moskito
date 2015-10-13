package net.anotheria.moskito.aop;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.aop.annotation.Accumulates;
import net.anotheria.moskito.aop.annotation.Monitor;

/**
 * Created by Roman Stetsiuk on 9/29/15.
 */
@Monitor
@Accumulates({
        @Accumulate(valueName = "Class1", intervalName = "1m"),
        @Accumulate(valueName = "Class2", intervalName = "5m")
})
@Accumulate(valueName = "Class3", intervalName = "1m")
public class AccumulatesAnnotatedClass {

    public void notAnnotatedFunc(){};

    @Accumulates({
        @Accumulate(valueName = "All1", intervalName = "1m"),
        @Accumulate(valueName = "All2", intervalName = "1m")
    })
    @Accumulate(valueName = "All3", intervalName = "1m")
    public void annotatedFuncAll(){};

    @Accumulate(valueName = "Single", intervalName = "1m")
    public void annotatedFuncSingle(){};

    @Accumulates({
            @Accumulate(valueName = "Holder1", intervalName = "1m"),
            @Accumulate(valueName = "Holder2", intervalName = "5m")
    })
    public void annotatedFuncHolder(){};

}
