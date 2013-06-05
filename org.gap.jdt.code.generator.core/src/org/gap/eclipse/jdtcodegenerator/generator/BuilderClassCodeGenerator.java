package org.gap.eclipse.jdtcodegenerator.generator;

import java.util.Arrays;

import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext;
import org.eclipse.emf.mwe2.runtime.workflow.WorkflowContextImpl;
import org.eclipse.xpand2.Generator;
import org.eclipse.xpand2.output.Outlet;
import org.eclipse.xpand2.output.Output;
import org.eclipse.xpand2.output.OutputImpl;
import org.eclipse.xtend.type.impl.java.JavaMetaModel;
import org.gap.eclipse.jdtcodegenerator.model.JavaBeanModel;

/**
 * Build class generator implementation of CodeGenerator API.
 * 
 * @author gayanper
 * 
 */
public class BuilderClassCodeGenerator implements CodeGenerator<Void> {
    private static final String DEFAULT_TEMPLATE = "org::gap::eclipse::jdtcodegenerator::templates::builderclass";
    private final String templateFQN;
    private final String outputPath;

    /**
     * Creates a new instance with the given fqn of template which points to a
     * xpand template in the external template configuration.
     * 
     * @param templateFQN The fully qualified name of the external template.
     *        Passing null will ignore this parameter.
     * @param outputPath
     */
    public BuilderClassCodeGenerator(String templateFQN, String outputPath) {
        this.outputPath = outputPath;
        if (templateFQN != null && templateFQN.isEmpty()) {
            throw new IllegalArgumentException("templateFQN cannot be empty.");
        }
        this.templateFQN = templateFQN;
    }

    @Override
    public Void generate(JavaBeanModel model) throws CodeGenerationException {
        final Outlet outlet = new Outlet();
        outlet.setPath(outputPath);
        
        final Output output = new OutputImpl();
        output.addOutlet(outlet);

        final org.eclipse.xpand2.Generator generator = new org.eclipse.xpand2.Generator();
        generator.addMetaModel(new JavaMetaModel());

        final IWorkflowContext contextImpl = new WorkflowContextImpl();
        contextImpl.put("model", model);
        
        updateGeneratorTemplateConfiguration(generator);

        generator.setOutput(output);
        generator.setBeautifier(Arrays.asList("org.eclipse.xpand2.output.JavaBeautifier"));
        generator.setPrSrcPaths(outputPath);
        generator.invoke(contextImpl);

        return null;
    }

    private void updateGeneratorTemplateConfiguration(Generator generator) {
        generator.setExpand(DEFAULT_TEMPLATE + "::model FOR model");
    }

}
