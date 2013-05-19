package org.gap.eclipse.jdtcodegenerator.model;

/**
 * Signals the model creation failed due to an error in
 * {@link JavaBeanModelFactoryImpl}.
 * 
 * @author gayanper
 * 
 */
public class ModelCreationException extends Exception {

    private static final long serialVersionUID = -7090032661829770783L;

    /**
     * Creates a new exception instance.
     */
    public ModelCreationException() {
        super();
    }

    /**
     * Creates a new instance with the given message and the cause.
     * 
     * @param message The error message.
     * @param cause The error which cause this exception to be thrown.
     */
    public ModelCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance with the given message.
     * 
     * @param message The error message.
     */
    public ModelCreationException(String message) {
        super(message);
    }

    /**
     * Creates a new instance with the given cause.
     * 
     * @param cause The error which cause this exception to be thrown.
     */
    public ModelCreationException(Throwable cause) {
        super(cause);
    }

}
