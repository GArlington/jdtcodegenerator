package org.gap.eclipse.jdtcodegenerator.commands;

import org.eclipse.jdt.core.ICompilationUnit;
import org.gap.eclipse.jdtcodegenerator.model.JavaBeanModel;
import org.gap.eclipse.jdtcodegenerator.model.ModelCreationException;

/**
 * An IHandler implementation which triggers the creation of public field
 * builder class for the selection class.
 * 
 * @author gayanper
 * 
 */
public class GenerateBuilderPublicFields extends BaseBuilderCommand {

    /**
     * Creates a new instance.
     */
    public GenerateBuilderPublicFields() {
        super();
    }

    @Override
    protected JavaBeanModel createSourceModelForCompilationUnit(ICompilationUnit compilationUnit)
            throws ModelCreationException {
        return getModelFactory().createModelForPublicFieldProperties(compilationUnit);
    }
}
