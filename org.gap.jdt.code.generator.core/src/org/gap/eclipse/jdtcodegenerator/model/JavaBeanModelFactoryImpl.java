package org.gap.eclipse.jdtcodegenerator.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IImportDeclaration;
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
            final List<IMethod> allMethods = getMethodsFromHierarchy(classType);
            final List<IImportDeclaration> imports = getImportsFromHierarchy(classType);
            final ArrayList<JavaBeanProperty> beanProperties = new ArrayList<JavaBeanProperty>(allMethods.size());
            final ArrayList<JavaImport> javaImports = new ArrayList<JavaImport>(imports.size());

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

            return new JavaBeanModelImpl(classType.getElementName(), classType.getPackageFragment().getElementName(),
                    beanProperties, javaImports);
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

            return new JavaBeanModelImpl(classType.getElementName(), classType.getPackageFragment().getElementName(),
                    beanProperties, javaImports);
        } catch (JavaModelException ex) {
            throw new ModelCreationException(ex);
        } catch (IllegalArgumentException ex) {
            throw new ModelCreationException(ex);
        }
    }

    @Override
    public JavaBeanModel createModelWithTemplate(JavaBeanModel template, List<JavaImport> imports)
            throws ModelCreationException {
        return new JavaBeanModelImpl(template.getClassName(), template.getPackageName(),
                new ArrayList<JavaBeanProperty>(template.getProperties()), imports);
    }

    private boolean isArrayType(String signature) {
        return Signature.getTypeSignatureKind(signature) == Signature.ARRAY_TYPE_SIGNATURE;
    }

    private List<IMethod> getMethodsFromHierarchy(IType type) throws JavaModelException {
        final IMethod[] methods = type.getMethods();
        final List<IMethod> methodList = new ArrayList<IMethod>(methods.length);
        for (final IMethod method : methods) {
            methodList.add(method);
        }

        final IType[] superTypes = type.newSupertypeHierarchy(new NullProgressMonitor()).getAllSupertypes(type);
        for (final IType superType : superTypes) {
            methodList.addAll(Arrays.asList(superType.getMethods()));
        }

        return methodList;
    }

    private List<IImportDeclaration> getImportsFromHierarchy(IType type) throws JavaModelException {
        final IImportDeclaration[] imports = type.getCompilationUnit().getImports();
        final List<IImportDeclaration> importList = new ArrayList<IImportDeclaration>(imports.length);
        for (final IImportDeclaration importDef : imports) {
            importList.add(importDef);
        }

        final IType[] superTypes = type.newSupertypeHierarchy(new NullProgressMonitor()).getAllSupertypes(type);
        for (final IType superType : superTypes) {
            if (superType.getCompilationUnit() != null) {
                importList.addAll(Arrays.asList(superType.getCompilationUnit().getImports()));
            }
        }

        return importList;
    }

}
