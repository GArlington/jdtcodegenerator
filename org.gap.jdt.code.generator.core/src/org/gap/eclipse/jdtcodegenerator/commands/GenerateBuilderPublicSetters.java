package org.gap.eclipse.jdtcodegenerator.commands;

import org.eclipse.jdt.core.ICompilationUnit;
import org.gap.eclipse.jdtcodegenerator.model.JavaBeanModel;
import org.gap.eclipse.jdtcodegenerator.model.ModelCreationException;

/**
 * An IHandler implementation which triggers the creation of public setter based
 * builder class for the selection class.
 * 
 * @author gayanper
 * 
 */
public class GenerateBuilderPublicSetters extends BaseBuilderCommand {
    /**
     * Creates a new instance.
     */
    public GenerateBuilderPublicSetters() {
        super();
    }

    @Override
    protected JavaBeanModel createSourceModelForCompilationUnit(ICompilationUnit compilationUnit)
            throws ModelCreationException {
        return getModelFactory().createModelForStandardBean(compilationUnit);
    }
}
