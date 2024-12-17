package run.slicer.procyon.teavm.classlib.java.lang.reflect;

public class TUndeclaredThrowableException extends RuntimeException {
    public TUndeclaredThrowableException(Throwable undeclaredThrowable) {
        super(undeclaredThrowable);
    }

    public TUndeclaredThrowableException(Throwable undeclaredThrowable, String message) {
        super(message, undeclaredThrowable);
    }
}
