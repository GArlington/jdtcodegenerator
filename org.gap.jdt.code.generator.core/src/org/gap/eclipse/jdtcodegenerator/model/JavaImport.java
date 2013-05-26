package org.gap.eclipse.jdtcodegenerator.model;

/**
 * This class represents a java import statement.
 * 
 * @author gayanper
 * 
 */
public class JavaImport {
    private final String importDefinition;

    /**
     * Creates a new instance with the given parameters.
     * 
     * @param importDefinition The import definition. ex: java.util.List
     */
    public JavaImport(String importDefinition) {
        this.importDefinition = importDefinition;
    }

    /**
     * Returns the import definition.
     * 
     * @return A string value which cannot be null.
     */
    public String getImportDefinition() {
        return importDefinition;
    }

}
