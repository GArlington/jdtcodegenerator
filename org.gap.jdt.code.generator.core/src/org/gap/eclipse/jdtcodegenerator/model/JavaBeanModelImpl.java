package org.gap.eclipse.jdtcodegenerator.model;

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

    /**
     * Creates a new model instance with the given parameters.
     * 
     * @param className The simple class name of the class which this model
     *        represents.
     * @param packageName The fully qualified package name of the class.
     * @param beanProperties List of properties this class has.
     */
    public JavaBeanModelImpl(String className, String packageName, List<JavaBeanProperty> beanProperties) {
        this.className = className;
        this.packageName = packageName;
        this.beanProperties = beanProperties;
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

}
