package org.gap.eclipse.jdtcodegenerator.model;

/**
 * This interface defines the API of the model which is used for code
 * generation.
 * 
 * @author gayanper
 * 
 */
public interface CodeGeneratorModel {

    /**
     * Returns the bean model to be used for the code generation.
     * 
     * @return An instance of JavaBeanModel.
     */
    JavaBeanModel getBeanModel();

    /**
     * Returns the package name to be used for generated code.
     * 
     * @return The name of the package or null if there is no such package
     *         specified.
     */
    String getTargetPackage();

    /**
     * The system location where the generated code will be saved.
     * 
     * @return A existing system directory path.
     */
    String getOutputDir();
}
