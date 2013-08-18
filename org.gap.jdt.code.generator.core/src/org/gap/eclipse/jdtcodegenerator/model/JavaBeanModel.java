package org.gap.eclipse.jdtcodegenerator.model;

import java.util.List;

/**
 * Defines the java bean model interface which can be used as a model for code
 * generation.
 * 
 * @author gayan.perera
 * 
 */
public interface JavaBeanModel {

    /**
     * Returns the bean name, which is the simple class name of the java class
     * this model instance represents.
     * 
     * @return The name of the class as string.
     */
    String getClassName();

    /**
     * Returns the name of the package which the class represented by this model
     * is in.
     * 
     * @return The package name as string.
     */
    String getPackageName();

    /**
     * Returns all bean properties in the class which represented by this model.
     * 
     * @return A list of JavaBeanProperty instances.
     */
    List<JavaBeanProperty> getProperties();

    /**
     * Returns a list java import used in bean definition.
     * 
     * @return A list of JavaImport instances.
     */
    List<JavaImport> getImports();

    /**
     * Returns the simple class names of the public inner types defined inside this
     * class.
     * 
     * @return A list of type names or empty if there is none.
     */
    List<String> getInnerTypeNames();
}
