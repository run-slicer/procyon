package run.slicer.procyon.teavm;

import org.teavm.vm.spi.TeaVMHost;
import org.teavm.vm.spi.TeaVMPlugin;

public class ProcyonPlugin implements TeaVMPlugin {
    @Override
    public void install(TeaVMHost host) {
        host.add(new MethodRewriteTransformer());
    }
}
