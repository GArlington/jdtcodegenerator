package org.gap.eclipse.jdtcodegenerator.model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
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
    public JavaBeanModel createModelForStandardBean(ICompilationUnit compilationUnit, IPackageFragment destPackage)
            throws ModelCreationException {
        try {
            final IType classType = compilationUnit.getAllTypes()[0];
            final IMethod[] allMethods = classType.getMethods();
            final IImportDeclaration[] imports = compilationUnit.getImports();
            final ArrayList<JavaBeanProperty> beanProperties = new ArrayList<JavaBeanProperty>(allMethods.length);
            final ArrayList<JavaImport> javaImports = new ArrayList<JavaImport>(imports.length);

            // Collect imports
            for (IImportDeclaration importDef : imports) {
                javaImports.add(new JavaImport(importDef.getElementName()));
            }

            for (IMethod method : allMethods) {
                if (!Flags.isStatic(method.getFlags()) && Flags.isPublic(method.getFlags())
                        && (method.getParameterTypes().length == 1)) {
                    // Lets check the pattern and extract the name.
                    final Matcher matcher = setterPattern.matcher(method.getElementName());
                    if (matcher.matches()) {
                        final StringBuilder nameBuilder = new StringBuilder(matcher.group(1));
                        nameBuilder.replace(0, 1, nameBuilder.substring(0, 1).toLowerCase());
                        final String paramSignature = method.getParameterTypes()[0];

                        if (isArrayType(paramSignature)) {
                            beanProperties.add(new JavaBeanProperty(true, nameBuilder.toString(), Signature
                                    .toString(paramSignature), method.getElementName(), "", true, Signature
                                    .toString(Signature.getElementType(paramSignature))));
                        } else {
                            beanProperties.add(new JavaBeanProperty(true, nameBuilder.toString(), Signature
                                    .toString(paramSignature), method.getElementName(), ""));
                        }
                    }
                }
            }

            final String packageName = (destPackage != null) ? destPackage.getElementName() : classType
                    .getPackageFragment().getElementName();
            return new JavaBeanModelImpl(classType.getElementName(), packageName, beanProperties, javaImports);
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
    public JavaBeanModel createModelForPublicFieldProperties(ICompilationUnit compilationUnit,
            IPackageFragment destPackage) throws ModelCreationException {

        try {
            final IType classType = compilationUnit.getAllTypes()[0];
            final IField[] allFields = classType.getFields();
            final IImportDeclaration[] imports = compilationUnit.getImports();

            final ArrayList<JavaBeanProperty> beanProperties = new ArrayList<JavaBeanProperty>(allFields.length);
            final ArrayList<JavaImport> javaImports = new ArrayList<JavaImport>(imports.length);

            // Collect imports
            for (IImportDeclaration importDef : imports) {
                javaImports.add(new JavaImport(importDef.getElementName()));
            }

            // Collect fields
            for (IField field : allFields) {
                if (!Flags.isFinal(field.getFlags()) && !Flags.isStatic(field.getFlags())
                        && Flags.isPublic(field.getFlags())) {

                    if (isArrayType(field.getTypeSignature())) {
                        beanProperties.add(new JavaBeanProperty(true, field.getElementName(), Signature.toString(field
                                .getTypeSignature()), "", "", true, Signature.toString(Signature.getElementType(field
                                .getTypeSignature()))));

                    } else {
                        beanProperties.add(new JavaBeanProperty(true, field.getElementName(), Signature.toString(field
                                .getTypeSignature()), "", ""));
                    }
                }
            }

            final String packageName = (destPackage != null) ? destPackage.getElementName() : classType
                    .getPackageFragment().getElementName();
            return new JavaBeanModelImpl(classType.getElementName(), packageName, beanProperties, javaImports);
        } catch (JavaModelException ex) {
            throw new ModelCreationException(ex);
        } catch (IllegalArgumentException ex) {
            throw new ModelCreationException(ex);
        }
    }

    private boolean isArrayType(String signature) {
        return Signature.getTypeSignatureKind(signature) == Signature.ARRAY_TYPE_SIGNATURE;
    }

}
