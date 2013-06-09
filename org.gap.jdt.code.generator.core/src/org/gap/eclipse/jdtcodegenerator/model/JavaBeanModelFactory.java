package org.gap.eclipse.jdtcodegenerator.model;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;

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
     * @param destPackage The destination package where the builder class will
     *        be placed. Pass null if the builder class needs to be created in
     *        the same package as the given <code>compilationUnit</code>.
     * @return A populated model.
     * @throws ModelCreationException If an error occurred while creating the
     *         model.
     */
    JavaBeanModel createModelForStandardBean(ICompilationUnit compilationUnit, IPackageFragment destPackage)
            throws ModelCreationException;

    /**
     * Creates a {@link JavaBeanModel} for generating a builder class. This will
     * populate the model using public mutable fields in the given source file.
     * This can be used for creating builders for legacy value objects. <br>
     * <br>
     * <b>Note : </b> Only the first class definition will be processed if the
     * source file contains multiple class definitions.
     * 
     * @param compilationUnit The java source file which needs to be analyzed.
     * @param destPackage The destination package where the builder class will
     *        be placed. Pass null if the builder class needs to be created in
     *        the same package as the given <code>compilationUnit</code>.
     * @return A populated model.
     * @throws ModelCreationException If an error occurred while creating the
     *         model.
     */
    JavaBeanModel createModelForPublicFieldProperties(ICompilationUnit compilationUnit, IPackageFragment destPackage)
            throws ModelCreationException;

}