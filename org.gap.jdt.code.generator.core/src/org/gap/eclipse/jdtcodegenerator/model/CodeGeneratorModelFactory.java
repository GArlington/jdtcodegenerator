package org.gap.eclipse.jdtcodegenerator.model;

import org.eclipse.jdt.core.IPackageFragment;

public interface CodeGeneratorModelFactory {
    /**
     * Creates a new CodeGeneratorModel for generating a builder class for given
     * java bean model.
     * 
     * @param beanModel The source class for creating a builder class.
     * @param targetPackage The target package where the builder class will be
     *        placed.
     * @return An instance of {@link CodeGeneratorModel}.
     * @throws ModelCreationException If an error occurred while creating the
     *         model.
     */
    CodeGeneratorModel createBuilderClassGeneratorModel(JavaBeanModel beanModel, IPackageFragment targetPackage)
            throws ModelCreationException;
}
