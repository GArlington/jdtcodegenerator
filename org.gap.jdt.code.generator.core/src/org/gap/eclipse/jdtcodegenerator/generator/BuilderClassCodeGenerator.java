package org.gap.eclipse.jdtcodegenerator.generator;

import java.util.Arrays;

import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext;
import org.eclipse.emf.mwe2.runtime.workflow.WorkflowContextImpl;
import org.eclipse.xpand2.Generator;
import org.eclipse.xpand2.output.Outlet;
import org.eclipse.xpand2.output.Output;
import org.eclipse.xpand2.output.OutputImpl;
import org.eclipse.xtend.type.impl.java.JavaMetaModel;
import org.gap.eclipse.jdtcodegenerator.model.CodeGeneratorModel;

/**
 * Build class generator implementation of CodeGenerator API.
 * 
 * @author gayanper
 * 
 */
public class BuilderClassCodeGenerator implements CodeGenerator<Void> {
    private static final String DEFAULT_TEMPLATE = "org::gap::eclipse::jdtcodegenerator::templates::builderclass";
    @SuppressWarnings("unused")
    private final String templateFQN;

    /**
     * Creates a new instance with the given fqn of template which points to a
     * xpand template in the external template configuration.
     * 
     * @param templateFQN The fully qualified name of the external template.
     *        Passing null will ignore this parameter.
     */
    public BuilderClassCodeGenerator(String templateFQN) {
        if (templateFQN != null && templateFQN.isEmpty()) {
            throw new IllegalArgumentException("templateFQN cannot be empty.");
        }
        this.templateFQN = templateFQN;
    }

    @Override
    public Void generate(CodeGeneratorModel model) throws CodeGenerationException {
        final Outlet outlet = new Outlet();
        outlet.setPath(model.getOutputDir());

        final Output output = new OutputImpl();
        output.addOutlet(outlet);

        final org.eclipse.xpand2.Generator generator = new org.eclipse.xpand2.Generator();
        generator.addMetaModel(new JavaMetaModel());

        final IWorkflowContext contextImpl = new WorkflowContextImpl();
        contextImpl.put("model", model);

        updateGeneratorTemplateConfiguration(generator);

        generator.setOutput(output);
        generator.setBeautifier(Arrays.asList("org.eclipse.xpand2.output.JavaBeautifier"));
        generator.setPrSrcPaths(model.getOutputDir());
        generator.invoke(contextImpl);

        return null;
    }

    private void updateGeneratorTemplateConfiguration(Generator generator) {
        generator.setExpand(DEFAULT_TEMPLATE + "::model FOR model");
    }

}
