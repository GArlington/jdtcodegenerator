package org.gap.eclipse.jdtcodegenerator.model;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;

/**
 * The implementation of CodeGeneratorModelFactory.
 * 
 * @author gayanper
 * 
 */
public final class CodeGeneratorModelFactoryImpl implements CodeGeneratorModelFactory {

    @Override
    public CodeGeneratorModel createBuilderClassGeneratorModel(JavaBeanModel beanModel, IPackageFragment targetPackage)
            throws ModelCreationException {
        try {
            return new SimpleCodeGeneratorModel(beanModel, targetPackage.getElementName(), targetPackage
                    .getUnderlyingResource().getRawLocation().toString());
        } catch (JavaModelException ex) {
            throw new ModelCreationException(ex);
        }
    }

}
