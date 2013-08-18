package org.gap.eclipse.jdtcodegenerator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private final Set<String> primitiveTypeNames;

    /**
     * Creates a new instance of this factory.
     * 
     * @param beanModelFactory An instance of JavaBeanModelFactory.
     */
    public CodeGeneratorModelFactoryImpl(JavaBeanModelFactory beanModelFactory) {
        this.beanModelFactory = beanModelFactory;
        primitiveTypeNames = new HashSet<String>();

        primitiveTypeNames.add(Boolean.TYPE.getName());
        primitiveTypeNames.add(Byte.TYPE.getName());
        primitiveTypeNames.add(Short.TYPE.getName());
        primitiveTypeNames.add(Integer.TYPE.getName());
        primitiveTypeNames.add(Long.TYPE.getName());
        primitiveTypeNames.add(Float.TYPE.getName());
        primitiveTypeNames.add(Double.TYPE.getName());
        primitiveTypeNames.add(Character.TYPE.getName());
    }

    @Override
    public CodeGeneratorModel createBuilderClassGeneratorModel(JavaBeanModel beanModel, IPackageFragment targetPackage)
            throws ModelCreationException {
        try {
            return new SimpleCodeGeneratorModel(fixImports(beanModel, targetPackage.getElementName()),
                    targetPackage.getElementName(), targetPackage.getUnderlyingResource().getRawLocation().toString());
        } catch (JavaModelException ex) {
            throw new ModelCreationException(ex);
        }
    }

    private JavaBeanModel fixImports(JavaBeanModel model, String targetPkg) throws ModelCreationException {
        final List<JavaBeanProperty> clonedProperties = new ArrayList<JavaBeanProperty>(model.getProperties());
        final List<JavaImport> imports = model.getImports();
        final Map<String, JavaImport> importsMap = toMap(imports);
        final List<JavaImport> newImportList = new ArrayList<JavaImport>();
        final List<String> innerTypes = model.getInnerTypeNames(); // TODO: Make this a set for increase performance.
        final String innerTypePrefix = model.getPackageName().concat(".").concat(model.getClassName()).concat(".");

        for (Iterator<JavaBeanProperty> iterator = clonedProperties.iterator(); iterator.hasNext();) {
            final JavaBeanProperty javaBeanProperty = iterator.next();
            final JavaImport removedImport = importsMap.remove(javaBeanProperty.getType());

            if (removedImport != null) {
                newImportList.add(removedImport);
                iterator.remove();
            }
        }

        // If the target and source packages are different add all property types excluding java.lang.*
        if (!model.getPackageName().equals(targetPkg)) {
            // Iterate over the remaining properties and try to find any required imports.
            for (JavaBeanProperty javaBeanProperty : clonedProperties) {
                final String type = (javaBeanProperty.isArrayType()) ? javaBeanProperty.getComponentType()
                        : javaBeanProperty.getType();

                if (notFQN(type) && !primitiveTypeNames.contains(type) && notJavaLang(type)) {
                    // Check for inner types.
                    if (innerTypes.contains(type)) {
                        newImportList.add(new JavaImport(innerTypePrefix.concat(type)));
                    } else {
                        newImportList.add(new JavaImport(model.getPackageName() + "." + type));
                    }
                }
            }

        }

        // Handle wild card imports.
        for (JavaImport im : imports) {
            if (im.getImportDefinition().endsWith("*")) {
                newImportList.add(im);
            }
        }

        return beanModelFactory.createModelWithImports(model, newImportList);
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

    private boolean notJavaLang(String type) {
        try {
            Class.forName("java.lang." + type);
            return false;
        } catch (ClassNotFoundException ex) {
            return true;
        }
    }

    private boolean notFQN(String type) {
        return !type.contains(".");
    }

}
