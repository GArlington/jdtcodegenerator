package org.gap.eclipse.jdtcodegenerator.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.gap.eclipse.jdtcodegenerator.Activator;
import org.gap.eclipse.jdtcodegenerator.generator.CodeGeneatorFactory;
import org.gap.eclipse.jdtcodegenerator.generator.CodeGenerationException;
import org.gap.eclipse.jdtcodegenerator.generator.CodeGenerator;
import org.gap.eclipse.jdtcodegenerator.model.CodeGeneratorModel;
import org.gap.eclipse.jdtcodegenerator.model.CodeGeneratorModelFactory;
import org.gap.eclipse.jdtcodegenerator.model.JavaBeanModel;
import org.gap.eclipse.jdtcodegenerator.model.JavaBeanModelFactory;
import org.gap.eclipse.jdtcodegenerator.model.JavaBeanProperty;
import org.gap.eclipse.jdtcodegenerator.model.ModelCreationException;
import org.gap.eclipse.jdtcodegenerator.ui.GeneratorElementSelectionDialog;
import org.gap.eclipse.jdtcodegenerator.ui.PackageSelectionDialog;

/**
 * An IHandler implementation which triggers the creation of public setter based
 * builder class for the selection class.
 * 
 * @author gayanper
 * 
 */
public class GenerateBuilderPublicSetters extends AbstractHandler {
    private final JavaBeanModelFactory modelFactory;
    private final CodeGeneatorFactory geneatorFactory;
    private final CodeGeneratorModelFactory codeGeneratorModelFactory;

    public GenerateBuilderPublicSetters() {
        modelFactory = Activator.getDefault().getJavaBeanModelFactory();
        geneatorFactory = Activator.getDefault().getCodeGeneatorFactory();
        codeGeneratorModelFactory = Activator.getDefault().getCodeGeneratorModelFactory();
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        final ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getSelectionService();
        final IStructuredSelection selection = (IStructuredSelection) selectionService.getSelection();

        try {
            final ICompilationUnit compilationUnit = (ICompilationUnit) selection.getFirstElement();

            // Ask the user for builder destination
            final PackageSelectionDialog selectionDialog = new PackageSelectionDialog(PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getShell(), compilationUnit.getJavaProject());

            selectionDialog.setTitle("Create builder class in");
            selectionDialog.open();

            final IPackageFragment packageFragment = selectionDialog.getSelectedPackage();
            if (packageFragment != null) {
                final IResource selectedPackage = packageFragment.getUnderlyingResource();

                // create a model and invoke generator.
                JavaBeanModel beanModel = modelFactory.createModelForStandardBean(compilationUnit);

                // Show the selection UI
                GeneratorElementSelectionDialog elementSelectionDialog = new GeneratorElementSelectionDialog(PlatformUI
                        .getWorkbench().getActiveWorkbenchWindow().getShell(), beanModel);
                elementSelectionDialog.open();
                List<JavaBeanProperty> result = toList(elementSelectionDialog.getResult());
                if (!result.isEmpty()) {
                    beanModel = modelFactory.createModelWithProperties(beanModel, result);

                    final CodeGeneratorModel codeGeneratorModel = codeGeneratorModelFactory
                            .createBuilderClassGeneratorModel(beanModel, packageFragment);
                    final CodeGenerator<Void> generator = geneatorFactory.createBuilderClassGenerator(null);

                    generator.generate(codeGeneratorModel);

                    // refresh the package folder.
                    selectedPackage.refreshLocal(IContainer.DEPTH_ONE, null);
                }

            }

        } catch (ModelCreationException ex) {
            throw new ExecutionException(ex.getMessage(), ex);
        } catch (CodeGenerationException ex) {
            throw new ExecutionException(ex.getMessage(), ex);
        } catch (JavaModelException ex) {
            throw new ExecutionException(ex.getMessage(), ex);
        } catch (CoreException ex) {
            throw new ExecutionException(ex.getMessage(), ex);
        }

        return null;
    }

    private List<JavaBeanProperty> toList(Object[] objects) {
        final List<JavaBeanProperty> list = new ArrayList<JavaBeanProperty>();
        if (objects != null) {
            for (Object object : objects) {
                list.add((JavaBeanProperty) object);
            }
        }
        return list;
    }

}
