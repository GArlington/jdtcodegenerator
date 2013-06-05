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

    private final String type;

    private boolean arrayType;

    private String componentType;

    /**
     * Creates a new property instance with the given parameters.
     * 
     * @param mutable Flags whether the property is mutable.
     * @param name The name of the property.
     * @param type The type of the property.
     * @param setterName The setter method name. Empty string if the setter
     *        method is not available.
     * @param getterName The getter method name. Empty string if the getter
     *        method is not available.
     */
    public JavaBeanProperty(boolean mutable, String name, String type, String setterName, String getterName) {
        this(mutable, name, type, setterName, getterName, false, "");
    }

    /**
     * Creates a new property instance with the given parameters.
     * 
     * @param mutable Flags whether the property is mutable.
     * @param name The name of the property.
     * @param type The type of the property.
     * @param setterName The setter method name. Empty string if the setter
     *        method is not available.
     * @param getterName The getter method name. Empty string if the getter
     *        method is not available.
     * @param arrayType If the property is an array type property pass
     *        <code>true</code>.
     * @param componentType The array component type, ex: for a string array the
     *        component type would be String.
     */
    public JavaBeanProperty(boolean mutable, String name, String type, String setterName, String getterName,
            boolean arrayType, String componentType) {
        this.mutable = mutable;
        this.name = name;
        this.type = type;
        this.setterName = setterName;
        this.getterName = getterName;
        this.arrayType = arrayType;
        this.componentType = componentType;
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
    public String getType() {
        return type;
    }

    /**
     * Returns the array component type if the current property is an array
     * type.
     * 
     * @return Returns the type signature for the array component or empty if
     *         the current property is not an array type.
     */
    public String getComponentType() {
        return componentType;
    }

    /**
     * Returns whether the current property type is an array type.
     * 
     * @return <code>true</code> if the property is an array type.
     */
    public boolean isArrayType() {
        return arrayType;
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
