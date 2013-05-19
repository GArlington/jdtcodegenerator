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
 * Implementation of {@link JavaBeanModelFactory}.
 * 
 * @author gayanper
 * 
 */
public class JavaBeanModelFactoryImpl implements JavaBeanModelFactory {

    private final Pattern setterPattern = Pattern.compile("^set(.*)$");

    /*
     * (non-Javadoc)
     * 
     * @see org.gap.eclipse.jdtcodegenerator.model.JavaBeanModelFactory#
     * createModelForStandardBean(org.eclipse.jdt.core.ICompilationUnit)
     */
    @Override
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

    /*
     * (non-Javadoc)
     * 
     * @see org.gap.eclipse.jdtcodegenerator.model.JavaBeanModelFactory#
     * createModelForPublicFieldProperties
     * (org.eclipse.jdt.core.ICompilationUnit)
     */
    @Override
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
