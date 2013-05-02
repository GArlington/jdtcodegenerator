package org.gap.eclipse.jdtcodegenerator.model;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
     * populate the model using standard java bean introspection.
     * 
     * @param clazz The class which needs to be analyzed.
     * @return A populated model.
     * @throws ModelCreationException If an error occurred while creating the
     *         model.
     * @see Introspector
     */
    public JavaBeanModel createModelForStandardBean(Class<?> clazz) throws ModelCreationException {
        try {
            // Following code handles classes which are written to the java bean standard. 
            final BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
            final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            final ArrayList<JavaBeanProperty> beanProperties = new ArrayList<JavaBeanProperty>(
                    propertyDescriptors.length);
            for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (propertyDescriptor.getWriteMethod() != null) {
                    beanProperties.add(new JavaBeanProperty(true, propertyDescriptor.getName(), propertyDescriptor
                            .getPropertyType(), propertyDescriptor.getWriteMethod().getName(), ""));
                }
            }

            return new JavaBeanModelImpl(clazz.getSimpleName(), clazz.getPackage().getName(), beanProperties);

        } catch (final IntrospectionException ex) {
            throw new ModelCreationException(
                    "An error occurred while generating JavaBeanModel for builder generation.", ex);
        }
    }

    /**
     * Creates a {@link JavaBeanModel} for generating a builder class. This will
     * populate the model using public mutable fields in the given class. This
     * is used for creating builders for legacy value objects.
     * 
     * @param clazz The class which needs to be analyzed.
     * @return A populated model.
     * @throws ModelCreationException If an error occurred while creating the
     *         model.
     */
    public JavaBeanModel createModelForPublicFieldProperties(Class<?> clazz) throws ModelCreationException {

        final Field[] allFields = clazz.getFields();
        final ArrayList<JavaBeanProperty> beanProperties = new ArrayList<JavaBeanProperty>(allFields.length);

        for (Field field : allFields) {
            if (!(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers()))) {
                beanProperties.add(new JavaBeanProperty(true, field.getName(), field.getType(), "", ""));
            }
        }

        return new JavaBeanModelImpl(clazz.getSimpleName(), clazz.getPackage().getName(), beanProperties);
    }

}
