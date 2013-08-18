package org.gap.eclipse.jdtcodegenerator.model;

import java.util.Collections;
import java.util.List;

/**
 * Default implementation of {@link JavaBeanModel}.
 * 
 * @author gayanper
 * 
 */
public class JavaBeanModelImpl implements JavaBeanModel {

    private final String className, packageName;
    private final List<JavaBeanProperty> beanProperties;
    private final List<JavaImport> javaImports;
    private final List<String> innerTypeNames;

    /**
     * Creates a new model instance with the given parameters.
     * 
     * @param className The simple class name of the class which this model
     *        represents.
     * @param packageName The fully qualified package name of the class.
     * @param beanProperties List of properties this class has.
     * @param javaImports List of imports definitions this class has.
     * @param innerTypeNames List of inner class type names.
     */
    public JavaBeanModelImpl(String className, String packageName, List<JavaBeanProperty> beanProperties,
            List<JavaImport> javaImports, List<String> innerTypeNames) {
        this.className = className;
        this.packageName = packageName;
        this.beanProperties = Collections.unmodifiableList(beanProperties);
        this.javaImports = Collections.unmodifiableList(javaImports);
        this.innerTypeNames = Collections.unmodifiableList(innerTypeNames);
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public List<JavaBeanProperty> getProperties() {
        return beanProperties;
    }

    @Override
    public List<JavaImport> getImports() {
        return javaImports;
    }

    @Override
    public List<String> getInnerTypeNames() {
        return innerTypeNames;
    }

}
