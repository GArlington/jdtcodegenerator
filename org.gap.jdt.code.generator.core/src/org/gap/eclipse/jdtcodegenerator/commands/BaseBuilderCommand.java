package org.gap.eclipse.jdtcodegenerator.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
 * Base builder command for builder class command handlers.
 * 
 * @author gayanper
 * 
 */
public abstract class BaseBuilderCommand extends AbstractHandler {

    private final CodeGeneatorFactory geneatorFactory;

    private final JavaBeanModelFactory modelFactory;

    private final CodeGeneratorModelFactory codeGeneratorModelFactory;

    public BaseBuilderCommand() {
        super();
        geneatorFactory = Activator.getDefault().getCodeGeneatorFactory();
        modelFactory = Activator.getDefault().getJavaBeanModelFactory();
        codeGeneratorModelFactory = Activator.getDefault().getCodeGeneratorModelFactory();
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

    /**
     * Returns the instance of JavaBeanModelFactory instance to be used.
     * 
     * @return An instance of JavaBeanModelFactory which will not be null.
     */
    public JavaBeanModelFactory getModelFactory() {
        return modelFactory;
    }

    @Override
    public final Object execute(ExecutionEvent event) throws ExecutionException {
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
                JavaBeanModel beanModel = createSourceModelForCompilationUnit(compilationUnit);

                // Search for existing builder if any and find properties
                final Collection<String> existingBuilderProperties = findExistingBuilderProperties(compilationUnit,
                        packageFragment);
                final List<JavaBeanProperty> initialSelection = new ArrayList<JavaBeanProperty>(
                        existingBuilderProperties.size());

                for (JavaBeanProperty property : beanModel.getProperties()) {
                    if (existingBuilderProperties.contains(property.getName())) {
                        initialSelection.add(property);
                    }
                }

                // Show the selection UI
                GeneratorElementSelectionDialog elementSelectionDialog = new GeneratorElementSelectionDialog(PlatformUI
                        .getWorkbench().getActiveWorkbenchWindow().getShell(), beanModel, initialSelection);
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

    private Collection<String> findExistingBuilderProperties(ICompilationUnit src, IPackageFragment packageFragment)
            throws ModelCreationException {
        final ICompilationUnit destUnit = packageFragment.getCompilationUnit(src.getElementName().replaceFirst(
                "\\.java", "Builder.java"));
        if ((destUnit != null) && destUnit.exists()) {
            final List<JavaBeanProperty> propertyList = getModelFactory().createModelForStandardBean(destUnit)
                    .getProperties();
            final List<String> propertyNameList = new ArrayList<String>(propertyList.size());
            for (JavaBeanProperty property : propertyList) {
                propertyNameList.add(property.getName());
            }
            return propertyNameList;
        }
        return Collections.emptyList();
    }

    /**
     * Creates and return a JavaBeanModel instance for the given compilation
     * unit to be processed by the base command when creating a builder class.
     * 
     * @param compilationUnit The compilation unit which will not be null to be
     *        processed when creating the java bean model.
     * @return An none null instance of a JavaBeanModel.
     * @throws ModelCreationException If the model creation failed.
     */
    protected abstract JavaBeanModel createSourceModelForCompilationUnit(ICompilationUnit compilationUnit)
            throws ModelCreationException;
}