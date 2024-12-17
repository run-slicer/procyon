package run.slicer.procyon.impl;

import com.strobel.assembler.metadata.Buffer;
import com.strobel.assembler.metadata.ITypeLoader;

import java.util.function.Function;

public record TypeLoaderImpl(Function<String, byte[]> source) implements ITypeLoader {
    @Override
    public boolean tryLoadType(String s, Buffer buffer) {
        final byte[] b = this.source.apply(s);
        if (b == null) {
            return false;
        }

        buffer.position(0);
        buffer.putByteArray(b, 0, b.length);
        buffer.position(0);
        return true;
    }
}
