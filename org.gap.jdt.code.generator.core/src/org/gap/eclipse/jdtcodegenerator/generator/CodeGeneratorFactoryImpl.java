package org.gap.eclipse.jdtcodegenerator.generator;

/**
 * The implementation of CodeGeneatorFactory.
 * 
 * @author gayanper
 * 
 */
public class CodeGeneratorFactoryImpl implements CodeGeneatorFactory {

    @Override
    public CodeGenerator<Void> createBuilderClassGenerator(String templateFQN) {
        return new BuilderClassCodeGenerator(templateFQN);
    }
}
