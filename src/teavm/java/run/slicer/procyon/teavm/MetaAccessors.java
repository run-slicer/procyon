package run.slicer.procyon.teavm;

import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Meta;
import org.teavm.metaprogramming.ReflectClass;
import org.teavm.metaprogramming.Value;
import org.teavm.metaprogramming.reflect.ReflectField;

import static org.teavm.metaprogramming.Metaprogramming.emit;
import static org.teavm.metaprogramming.Metaprogramming.unsupportedCase;

@CompileTime
public final class MetaAccessors {
    private MetaAccessors() {
    }

    @Meta
    public static native void setAllKeys(Class<?> cls, Object val);

    private static void setAllKeys(ReflectClass<Object> cls, Value<Object> val) {
        if (!cls.getName().equals("com.strobel.decompiler.languages.java.ast.Keys")) {
            unsupportedCase();
            return;
        }

        final ReflectField field = cls.getDeclaredField("ALL_KEYS");
        if (field != null) {
            // TeaVM doesn't care about the final modifier thankfully
            emit(() -> field.set(null, val));
        } else {
            throw new RuntimeException("Could not find ALL_KEYS field");
        }
    }
}
