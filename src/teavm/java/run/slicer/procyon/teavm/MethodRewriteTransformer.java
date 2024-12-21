package run.slicer.procyon.teavm;

import com.strobel.assembler.metadata.Buffer;
import org.teavm.model.*;
import org.teavm.model.instructions.ExitInstruction;
import org.teavm.model.instructions.InvocationType;
import org.teavm.model.instructions.InvokeInstruction;

public class MethodRewriteTransformer implements ClassHolderTransformer {
    @Override
    public void transformClass(ClassHolder cls, ClassHolderTransformerContext context) {
        switch (cls.getName()) {
            case "com.strobel.compilerservices.RuntimeHelpers" -> {
                // take advantage of the method to do some initialization
                this.stubWithCall(
                        cls.getMethod(new MethodDescriptor("ensureClassInitialized", Class.class, void.class)),
                        new MethodReference(
                                "run.slicer.procyon.teavm.MethodDelegates",
                                "com_strobel_decompiler_languages_java_ast_Keys__clinit_",
                                ValueType.VOID
                        )
                );
            }
            case "com.strobel.assembler.metadata.ClasspathTypeLoader" -> {
                final MethodHolder method = cls.getMethod(
                        new MethodDescriptor("tryLoadType", String.class, Buffer.class, boolean.class)
                );

                final var program = new Program();
                program.createVariable(); // type var
                
                final Variable nameVar = program.createVariable();
                final Variable bufferVar = program.createVariable();
                final Variable returnVal = program.createVariable();
                
                final BasicBlock block = program.createBasicBlock();

                final var invokeInsn = new InvokeInstruction();
                invokeInsn.setType(InvocationType.VIRTUAL);
                invokeInsn.setMethod(new MethodReference(
                        "run.slicer.procyon.teavm.MethodDelegates",
                        new MethodDescriptor(
                                "com_strobel_assembler_metadata_ClasspathTypeLoader_tryLoadType",
                                String.class,
                                Buffer.class,
                                boolean.class
                        )
                ));
                invokeInsn.setArguments(nameVar, bufferVar);
                invokeInsn.setReceiver(returnVal);
                block.add(invokeInsn);

                final var returnInsn = new ExitInstruction();
                returnInsn.setValueToReturn(returnVal);
                block.add(returnInsn);

                method.setProgram(program);
            }
        }
    }

    private void stubWithCall(MethodHolder method, MethodReference target) {
        final Program program = this.newProgram(method.parameterCount());

        final BasicBlock block = program.createBasicBlock();

        final var invokeInsn = new InvokeInstruction();
        invokeInsn.setType(InvocationType.VIRTUAL);
        invokeInsn.setMethod(target);
        block.add(invokeInsn);

        block.add(new ExitInstruction());

        method.setProgram(program);
    }

    private Program newProgram(int parameterCount) {
        parameterCount++; // type var

        final Program program = new Program();
        for (int i = 0; i < parameterCount; i++) {
            program.createVariable();
        }

        return program;
    }
}
