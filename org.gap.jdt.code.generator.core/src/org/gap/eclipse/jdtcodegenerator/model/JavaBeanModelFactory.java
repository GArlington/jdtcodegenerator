package org.gap.eclipse.jdtcodegenerator.model;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;

/**
 * Factory class which contains factory methods to create {@link JavaBeanModel}
 * instance for given code generation scenarios such as <b>Builder</b>
 * generation.
 * 
 * @author gayanper
 * 
 */
public class JavaBeanModelFactory {

    /**
     * Creates a {@link JavaBeanModel} for generating a builder class. This will
     * populate the model with the following <br>
     * <ul>
     * <li>Public Setter methods (Including inherited)</li>
     * <li>Public fields (Mutable)</li>
     * </ul>
     * 
     * @param clazz The class which needs to be analyzed for builder generation.
     * @return A populated model.
     * @throws ModelCreationException If an error occurred while creating the
     *         model.
     */
    public JavaBeanModel createModelForBuilderClass(Class<?> clazz) throws ModelCreationException {
        try {
            // Following code handles classes which are written to the java bean standard. 
            final BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
            final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            final ArrayList<JavaBeanProperty> beanProperties = new ArrayList<JavaBeanProperty>(
                    propertyDescriptors.length);
            for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (propertyDescriptor.getWriteMethod() != null) {
                    beanProperties.add(new JavaBeanProperty(true, propertyDescriptor.getName(), propertyDescriptor
                            .getWriteMethod().getName(), ""));
                }
            }

            return new JavaBeanModelImpl(clazz.getSimpleName(), clazz.getPackage().getName(), beanProperties);

        } catch (final IntrospectionException ex) {
            throw new ModelCreationException(
                    "An error occurred while generating JavaBeanModel for builder generation.", ex);
        }
    }

}
