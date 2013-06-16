package org.gap.eclipse.jdtcodegenerator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;

/**
 * The implementation of CodeGeneratorModelFactory.
 * 
 * @author gayanper
 * 
 */
public final class CodeGeneratorModelFactoryImpl implements CodeGeneratorModelFactory {

    private final JavaBeanModelFactory beanModelFactory;

    /**
     * Creates a new instance of this factory.
     * 
     * @param beanModelFactory An instance of JavaBeanModelFactory.
     */
    public CodeGeneratorModelFactoryImpl(JavaBeanModelFactory beanModelFactory) {
        this.beanModelFactory = beanModelFactory;
    }

    @Override
    public CodeGeneratorModel createBuilderClassGeneratorModel(JavaBeanModel beanModel, IPackageFragment targetPackage)
            throws ModelCreationException {
        try {
            return new SimpleCodeGeneratorModel(fixImports(beanModel), targetPackage.getElementName(), targetPackage
                    .getUnderlyingResource().getRawLocation().toString());
        } catch (JavaModelException ex) {
            throw new ModelCreationException(ex);
        }
    }

    private JavaBeanModel fixImports(JavaBeanModel model) throws ModelCreationException {
        final List<JavaBeanProperty> properties = model.getProperties();
        final List<JavaImport> imports = model.getImports();
        final Map<String, JavaImport> importsMap = toMap(imports);
        final List<JavaImport> newImportList = new ArrayList<JavaImport>();

        for (JavaBeanProperty javaBeanProperty : properties) {
            final JavaImport removedImport = importsMap.remove(javaBeanProperty.getType());
            if (removedImport != null) {
                newImportList.add(removedImport);
            }
        }

        // Handle wild card imports.
        for (JavaImport im : imports) {
            if (im.getImportDefinition().endsWith("*")) {
                newImportList.add(im);
            }
        }

        return beanModelFactory.createModelWithTemplate(model, newImportList);
    }

    private Map<String, JavaImport> toMap(List<JavaImport> imports) {
        final HashMap<String, JavaImport> importMap = new HashMap<String, JavaImport>();
        for (JavaImport javaImport : imports) {
            importMap.put(
                    javaImport.getImportDefinition().substring(javaImport.getImportDefinition().lastIndexOf(".") + 1),
                    javaImport);
        }
        return importMap;
    }
}
