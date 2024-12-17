package run.slicer.procyon;

import com.strobel.assembler.metadata.ITypeLoader;
import com.strobel.assembler.metadata.MetadataSystem;
import com.strobel.assembler.metadata.TypeReference;
import com.strobel.decompiler.DecompilationOptions;
import com.strobel.decompiler.DecompilerSettings;
import com.strobel.decompiler.PlainTextOutput;
import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSByRef;
import org.teavm.jso.JSExport;
import org.teavm.jso.core.JSObjects;
import org.teavm.jso.core.JSPromise;
import org.teavm.jso.core.JSString;
import org.teavm.jso.typedarrays.Uint8Array;
import run.slicer.procyon.impl.ProcyonOptions;
import run.slicer.procyon.impl.TypeLoaderImpl;

import java.io.StringWriter;

public class Main {
    @JSExport
    public static JSPromise<JSString> decompile(String name, Options options) {
        return decompile0(name, options == null || JSObjects.isUndefined(options) ? JSObjects.create() : options);
    }

    private static JSPromise<JSString> decompile0(String name, Options options) {
        return new JSPromise<>((resolve, reject) -> {
            new Thread(() -> {
                try {
                    final ITypeLoader loader = new TypeLoaderImpl(name0 -> source0(options, name0));
                    final DecompilerSettings settings = ProcyonOptions.toProcyonWithDefaults(options.rawOptions());
                    settings.setTypeLoader(loader);

                    final var system = new MetadataSystem(loader);
                    final TypeReference ref = system.lookupType(name);

                    final var procyonOptions = new DecompilationOptions();
                    procyonOptions.setSettings(settings);

                    final var writer = new StringWriter();
                    settings.getLanguage().decompileType(ref.resolve(), new PlainTextOutput(writer), procyonOptions);

                    resolve.accept(JSString.valueOf(writer.toString()));
                } catch (Throwable e) {
                    reject.accept(e);
                }
            }).start();
        });
    }

    @Async
    private static native byte[] source0(Options options, String name);

    private static void source0(Options options, String name, AsyncCallback<byte[]> callback) {
        options.source(name)
                .then(b -> {
                    callback.complete(b == null || JSObjects.isUndefined(b) ? null : unwrapByteArray(b));
                    return null;
                })
                .catchError(err -> {
                    callback.error(new Exception(err.toString()));
                    return null;
                });
    }

    @JSBody(params = {"data"}, script = "return data;")
    private static native @JSByRef byte[] unwrapByteArray(Uint8Array data);
}
