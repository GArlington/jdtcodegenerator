package org.gap.eclipse.jdtcodegenerator.generator;


/**
 * Factory API which defines factory methods to create CodeGenerator instances.
 * 
 * @author gayanper
 * 
 */
public interface CodeGeneatorFactory {
    /**
     * Create a code generator for building a builder pattern class which uses
     * the given template to generate source code.
     * 
     * @param templateFQN The fully qualified name of the external template.
     *        Pass null to use the default template.
     * @param outputPath A valid directory path where the generated source code
     *        files will be copied to.
     * @return An instance of CodeGenerator will be returned which is configured
     *         with the given template.
     */
    CodeGenerator<Void> createBuilderClassGenerator(String templateFQN, String outputPath);
}
