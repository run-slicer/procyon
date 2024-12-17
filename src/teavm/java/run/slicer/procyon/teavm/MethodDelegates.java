package run.slicer.procyon.teavm;

import com.strobel.assembler.metadata.Buffer;
import com.strobel.decompiler.languages.java.ast.Keys;

import java.util.List;

import static run.slicer.procyon.teavm.MetaAccessors.setAllKeys;

public final class MethodDelegates {
    private MethodDelegates() {
    }

    public static boolean com_strobel_assembler_metadata_ClasspathTypeLoader_tryLoadType(String name, Buffer buffer) {
        byte[] b = switch (name) {
            case "java/lang/Object" -> BuildConstants.OBJECT_CLASS;
            case "java/lang/Class" -> BuildConstants.CLASS_CLASS;
            default -> null;
        };

        if (b == null) {
            return false;
        }

        buffer.position(0);
        buffer.putByteArray(b, 0, b.length);
        buffer.position(0);
        return true;
    }

    public static void com_strobel_decompiler_languages_java_ast_Keys_LTclinitGT() {
        try {
            Keys.ALL_KEYS.size(); // initialize class
        } catch (Throwable ignored) {
            // initialization failed, hijack and complete it on our own
            setAllKeys(Keys.class, List.of(
                    Keys.VARIABLE,
                    Keys.VARIABLE_DEFINITION,
                    Keys.PARAMETER_DEFINITION,
                    Keys.MEMBER_REFERENCE,
                    Keys.PACKAGE_REFERENCE,
                    Keys.FIELD_DEFINITION,
                    Keys.METHOD_DEFINITION,
                    Keys.TYPE_DEFINITION,
                    Keys.MODULE_REFERENCE,
                    Keys.TYPE_REFERENCE,
                    Keys.ANONYMOUS_BASE_TYPE_REFERENCE,
                    Keys.DYNAMIC_CALL_SITE,
                    Keys.AST_BUILDER,
                    Keys.CONSTANT_VALUE,
                    Keys.NAME_VARIABLES
            ));
        }
    }
}
