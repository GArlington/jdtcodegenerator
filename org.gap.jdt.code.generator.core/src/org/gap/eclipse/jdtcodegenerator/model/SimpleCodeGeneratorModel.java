package org.gap.eclipse.jdtcodegenerator.model;

/**
 * The simple code generator model which is suitable for many code generations.
 * 
 * @author gayanper
 * 
 */
public class SimpleCodeGeneratorModel implements CodeGeneratorModel {
    private final JavaBeanModel beanModel;

    private final String targetPackage;

    private final String outputDir;

    /**
     * Creates a new instance with the given parameters.
     * 
     * @param beanModel The java bean model to be used by the code generator.
     * @param targetPackage The target package to be used, or null if not
     *        specified.
     * @param outputDir A directory path where the generated code will be saved.
     */
    public SimpleCodeGeneratorModel(JavaBeanModel beanModel, String targetPackage, String outputDir) {
        this.beanModel = beanModel;
        this.targetPackage = targetPackage;
        this.outputDir = outputDir;
    }

    @Override
    public JavaBeanModel getBeanModel() {
        return beanModel;
    }

    @Override
    public String getTargetPackage() {
        return targetPackage;
    }

    @Override
    public String getOutputDir() {
        return outputDir;
    }

}
