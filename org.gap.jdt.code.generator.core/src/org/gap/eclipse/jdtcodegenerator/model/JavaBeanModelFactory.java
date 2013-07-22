package org.gap.eclipse.jdtcodegenerator.model;

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;

/**
 * Factory API which defines factory methods to create {@link JavaBeanModel}
 * instance for given code generation scenarios such as <b>Builder</b>
 * generation.
 * 
 * @author gayanper
 * 
 */
public interface JavaBeanModelFactory {

    /**
     * Creates a {@link JavaBeanModel} for generating a builder class. This will
     * populate the model using setter methods with a single parameter.
     * 
     * @param compilationUnit The java source file which needs to be analyzed.
     * @return A populated model.
     * @throws ModelCreationException If an error occurred while creating the
     *         model.
     */
    JavaBeanModel createModelForStandardBean(ICompilationUnit compilationUnit) throws ModelCreationException;

    /**
     * Creates a {@link JavaBeanModel} for generating a builder class. This will
     * populate the model using public mutable fields in the given source file.
     * This can be used for creating builders for legacy value objects. <br>
     * <br>
     * <b>Note : </b> Only the first class definition will be processed if the
     * source file contains multiple class definitions.
     * 
     * @param compilationUnit The java source file which needs to be analyzed.
     * @return A populated model.
     * @throws ModelCreationException If an error occurred while creating the
     *         model.
     */
    JavaBeanModel createModelForPublicFieldProperties(ICompilationUnit compilationUnit) throws ModelCreationException;

    /**
     * Creates a new JavaBeanModel with the given template instance and update
     * the given imports into the new model.
     * 
     * @param template The template instance which cannot be null.
     * @param imports An list of imports to update in the new model which cannot
     *        be null.
     * @return The new model instance.
     * @throws ModelCreationException If the model creation failed.
     */
    JavaBeanModel createModelWithImports(JavaBeanModel template, List<JavaImport> imports)
            throws ModelCreationException;

    /**
     * Creates a new JavaBeanModel with the given template instance and update
     * the given properties into the new model.
     * 
     * @param template The template instance which cannot be null.
     * @param properties An list of properties to update in the new model which
     *        cannot be null.
     * @return The new model instance.
     * @throws ModelCreationException If the model creation failed.
     */
    JavaBeanModel createModelWithProperties(JavaBeanModel template, List<JavaBeanProperty> properties)
            throws ModelCreationException;

}