package org.gap.eclipse.jdtcodegenerator.model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

/**
 * Factory class which contains factory methods to create {@link JavaBeanModel}
 * instance for given code generation scenarios such as <b>Builder</b>
 * generation.
 * 
 * @author gayanper
 * 
 */
public class JavaBeanModelFactory {

    private final Pattern setterPattern = Pattern.compile("^set(.*)$");

    /**
     * Creates a {@link JavaBeanModel} for generating a builder class. This will
     * populate the model using setter methods with a single parameter.
     * 
     * @param compilationUnit The java source file which needs to be analyzed.
     * @return A populated model.
     * @throws ModelCreationException If an error occurred while creating the
     *         model.
     */
    public JavaBeanModel createModelForStandardBean(ICompilationUnit compilationUnit) throws ModelCreationException {
        try {
            final IType classType = compilationUnit.getAllTypes()[0];
            final IMethod[] allMethods = classType.getMethods();
            final ArrayList<JavaBeanProperty> beanProperties = new ArrayList<JavaBeanProperty>(allMethods.length);

            for (IMethod method : allMethods) {
                if (!Flags.isStatic(method.getFlags()) && Flags.isPublic(method.getFlags())
                        && (method.getParameterTypes().length == 1)) {
                    // Lets check the pattern and extract the name.
                    final Matcher matcher = setterPattern.matcher(method.getElementName());
                    if (matcher.matches()) {
                        final StringBuilder nameBuilder = new StringBuilder(matcher.group(1));
                        nameBuilder.replace(0, 1, nameBuilder.substring(0, 1).toLowerCase());

                        beanProperties.add(new JavaBeanProperty(true, nameBuilder.toString(), Signature.toString(method
                                .getParameterTypes()[0]), method.getElementName(), ""));
                    }
                }
            }

            return new JavaBeanModelImpl(classType.getElementName(), classType.getPackageFragment().getElementName(),
                    beanProperties);
        } catch (JavaModelException ex) {
            throw new ModelCreationException(ex);
        } catch (IllegalArgumentException ex) {
            throw new ModelCreationException(ex);
        }
    }

    /**
     * Creates a {@link JavaBeanModel} for generating a builder class. This will
     * populate the model using public mutable fields in the given source file.
     * This can be used for creating builders for legacy value objects. <br>
     * <br>
     * <b>Note : </b> Only the first class definition will be processed if the
     * source file contains multiple class definitions.
     * 
     * @param compilationUnit The java source file which needs to be analyzed.
     * @return A populated model.
     * @throws ModelCreationException If an error occurred while creating the
     *         model.
     */
    public JavaBeanModel createModelForPublicFieldProperties(ICompilationUnit compilationUnit)
            throws ModelCreationException {

        try {
            final IType classType = compilationUnit.getAllTypes()[0];
            final IField[] allFields = classType.getFields();
            final ArrayList<JavaBeanProperty> beanProperties = new ArrayList<JavaBeanProperty>(allFields.length);

            for (IField field : allFields) {
                if (!Flags.isFinal(field.getFlags()) && !Flags.isStatic(field.getFlags())
                        && Flags.isPublic(field.getFlags())) {
                    beanProperties.add(new JavaBeanProperty(true, field.getElementName(), Signature.toString(field
                            .getTypeSignature()), "", ""));
                }
            }

            return new JavaBeanModelImpl(classType.getElementName(), classType.getPackageFragment().getElementName(),
                    beanProperties);
        } catch (JavaModelException ex) {
            throw new ModelCreationException(ex);
        } catch (IllegalArgumentException ex) {
            throw new ModelCreationException(ex);
        }
    }
}
