package org.gap.eclipse.jdtcodegenerator.generator;


/**
 * Signals that the code generation failed due to an error in CodeGenerator.
 * 
 * @author gayanper
 * 
 */
public class CodeGenerationException extends Exception {

    private static final long serialVersionUID = -290658501149681161L;

    public CodeGenerationException() {
        super();
    }

    public CodeGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeGenerationException(String message) {
        super(message);
    }

    public CodeGenerationException(Throwable cause) {
        super(cause);
    }

}
