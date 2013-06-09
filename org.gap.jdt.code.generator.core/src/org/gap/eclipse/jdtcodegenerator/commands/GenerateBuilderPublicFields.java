package org.gap.eclipse.jdtcodegenerator.commands;

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
import org.gap.eclipse.jdtcodegenerator.model.ModelCreationException;
import org.gap.eclipse.jdtcodegenerator.ui.PackageSelectionDialog;

/**
 * An IHandler implementation which triggers the creation of public field
 * builder class for the selection class.
 * 
 * @author gayanper
 * 
 */
public class GenerateBuilderPublicFields extends AbstractHandler {
    private final JavaBeanModelFactory modelFactory;
    private final CodeGeneatorFactory geneatorFactory;
    private final CodeGeneratorModelFactory codeGeneratorModelFactory;

    public GenerateBuilderPublicFields() {
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
                final JavaBeanModel beanModel = modelFactory.createModelForPublicFieldProperties(compilationUnit);
                final CodeGeneratorModel codeGeneratorModel = codeGeneratorModelFactory
                        .createBuilderClassGeneratorModel(beanModel, packageFragment);
                final CodeGenerator<Void> generator = geneatorFactory.createBuilderClassGenerator(null);

                generator.generate(codeGeneratorModel);

                // refresh the package folder.
                selectedPackage.refreshLocal(IContainer.DEPTH_ONE, null);
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

}
