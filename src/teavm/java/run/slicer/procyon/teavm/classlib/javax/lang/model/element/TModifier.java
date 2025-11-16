package run.slicer.procyon.teavm.classlib.javax.lang.model.element;

import java.util.Locale;

public enum TModifier {
    PUBLIC,
    PROTECTED,
    PRIVATE,
    ABSTRACT,
    DEFAULT,
    STATIC,
    SEALED,
    NON_SEALED("non-sealed"),
    FINAL,
    TRANSIENT,
    VOLATILE,
    SYNCHRONIZED,
    NATIVE,
    STRICTFP;

    private final String value;

    TModifier() {
        this.value = name().toLowerCase(Locale.US);
    }

    TModifier(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
