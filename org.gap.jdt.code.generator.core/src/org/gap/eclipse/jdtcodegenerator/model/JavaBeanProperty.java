package org.gap.eclipse.jdtcodegenerator.model;

/**
 * This class represents a java bean property.
 * 
 * @author gayanper
 * 
 */
public class JavaBeanProperty {

    private final boolean mutable;

    private final String name, setterName, getterName;

    private final Class<?> type;

    /**
     * Creates a new property instance with the given parameters.
     * 
     * @param mutable Flags whether the property is mutable.
     * @param name The name of the property.
     * @param setterName The setter method name. Empty string if the setter
     *        method is not available.
     * @param getterName The getter method name. Empty string if the getter
     *        method is not available.
     */
    public JavaBeanProperty(boolean mutable, String name, Class<?> type, String setterName, String getterName) {
        this.mutable = mutable;
        this.name = name;
        this.type = type;
        this.setterName = setterName;
        this.getterName = getterName;
    }

    /**
     * Returns the name of the property.
     * 
     * @return name of the property which will not be null.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns whether this property represents a mutable property.
     * 
     * @return <code>true</code> if the property is mutable.
     */
    public boolean isMutable() {
        return mutable;
    }

    /**
     * Returns the getter method name in class.
     * 
     * @return Empty string if there is no getter method.
     */
    public String getGetterName() {
        return getterName;
    }

    /**
     * Returns the setter method name in class.
     * 
     * @return Empty string if there is no setter method.
     */
    public String getSetterName() {
        return setterName;
    }

    /**
     * Returns the type of the property.
     * 
     * @return The class object representing the type of the property.
     */
    public Class<?> getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JavaBeanProperty other = (JavaBeanProperty) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "JavaBeanProperty [name=" + name + "]";
    }

}
