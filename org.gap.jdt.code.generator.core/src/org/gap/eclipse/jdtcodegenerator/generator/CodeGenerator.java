package org.gap.eclipse.jdtcodegenerator.generator;

import org.gap.eclipse.jdtcodegenerator.model.CodeGeneratorModel;

/**
 * This interface defines the API of the code generator which will transform a
 * given JavaBeanModel into source code.
 * 
 * @author gayanper
 * 
 * @param <R> The type of the generated result.
 */
public interface CodeGenerator<R> {
    /**
     * Generate source code for given model.
     * 
     * @param model The code generator model which cannot be null.
     * @return The result of the generation which depends on the actual
     *         implementation of the code generator.
     * @throws CodeGenerationException If the code generation failed due to a
     *         error.
     */
    R generate(CodeGeneratorModel model) throws CodeGenerationException;
}
