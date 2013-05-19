package org.gap.eclipse.jdtcodegenerator.model.testdata;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

public final class TestData {

    public static ICompilationUnit createPublicFieldDataSet() throws JavaModelException {
        final ICompilationUnit mockCompilationUnit = mock(ICompilationUnit.class);
        final IType mockType = mock(IType.class);
        final IPackageFragment mockPackageFragment = mock(IPackageFragment.class);
        final IField[] mockFields = new IField[8];

        when(mockCompilationUnit.getAllTypes()).thenReturn(new IType[] { mockType });
        when(mockType.getElementName()).thenReturn("PublicFieldDataSet");
        when(mockType.getPackageFragment()).thenReturn(mockPackageFragment);
        when(mockPackageFragment.getElementName()).thenReturn("org.gap.eclipse.jdtcodegenerator.model.testdata");

        // mock the fields one by one.
        mockFields[0] = mock(IField.class);
        when(mockFields[0].getFlags()).thenReturn(Flags.AccPublic);
        when(mockFields[0].getElementName()).thenReturn("name");
        when(mockFields[0].getTypeSignature()).thenReturn(Signature.createTypeSignature("java.lang.String", true));

        mockFields[1] = mock(IField.class);
        when(mockFields[1].getFlags()).thenReturn(Flags.AccPublic);
        when(mockFields[1].getElementName()).thenReturn("locale");
        when(mockFields[1].getTypeSignature()).thenReturn(Signature.createTypeSignature("java.util.Locale", true));

        mockFields[2] = mock(IField.class);
        when(mockFields[2].getFlags()).thenReturn(Flags.AccPublic | Flags.AccStatic);
        when(mockFields[2].getElementName()).thenReturn("staticLocale");
        when(mockFields[2].getTypeSignature()).thenReturn(Signature.createTypeSignature("java.util.Locale", true));

        mockFields[3] = mock(IField.class);
        when(mockFields[3].getFlags()).thenReturn(Flags.AccPublic | Flags.AccStatic | Flags.AccFinal);
        when(mockFields[3].getElementName()).thenReturn("constant");
        when(mockFields[3].getTypeSignature()).thenReturn(Signature.createTypeSignature("java.lang.String", true));

        mockFields[4] = mock(IField.class);
        when(mockFields[4].getFlags()).thenReturn(Flags.AccPublic | Flags.AccFinal);
        when(mockFields[4].getElementName()).thenReturn("finalOnly");
        when(mockFields[4].getTypeSignature()).thenReturn(Signature.createTypeSignature("java.lang.String", true));

        mockFields[5] = mock(IField.class);
        when(mockFields[5].getFlags()).thenReturn(Flags.AccPublic);
        when(mockFields[5].getElementName()).thenReturn("age");
        when(mockFields[5].getTypeSignature()).thenReturn(Signature.createTypeSignature("int", true));

        mockFields[6] = mock(IField.class);
        when(mockFields[6].getFlags()).thenReturn(Flags.AccPrivate);
        when(mockFields[6].getElementName()).thenReturn("privateAge");
        when(mockFields[6].getTypeSignature()).thenReturn(Signature.createTypeSignature("int", true));

        mockFields[7] = mock(IField.class);
        when(mockFields[7].getFlags()).thenReturn(Flags.AccPrivate);
        when(mockFields[7].getElementName()).thenReturn("protectedAge");
        when(mockFields[7].getTypeSignature()).thenReturn(Signature.createTypeSignature("int", true));

        when(mockType.getFields()).thenReturn(mockFields);

        return mockCompilationUnit;
    }

    public static ICompilationUnit createSetterMethodDataSet() throws JavaModelException {
        final ICompilationUnit mockCompilationUnit = mock(ICompilationUnit.class);
        final IType mockType = mock(IType.class);
        final IPackageFragment mockPackageFragment = mock(IPackageFragment.class);
        final IMethod[] mockMethods = new IMethod[8];

        when(mockCompilationUnit.getAllTypes()).thenReturn(new IType[] { mockType });
        when(mockType.getElementName()).thenReturn("SetterMethodDataSet");
        when(mockType.getPackageFragment()).thenReturn(mockPackageFragment);
        when(mockPackageFragment.getElementName()).thenReturn("org.gap.eclipse.jdtcodegenerator.model.testdata");

        // mock the fields one by one.
        mockMethods[0] = mock(IMethod.class);
        when(mockMethods[0].getFlags()).thenReturn(Flags.AccPublic);
        when(mockMethods[0].getElementName()).thenReturn("setName");
        when(mockMethods[0].getParameterTypes()).thenReturn(
                new String[] { Signature.createTypeSignature("java.lang.String", true) });

        mockMethods[1] = mock(IMethod.class);
        when(mockMethods[1].getFlags()).thenReturn(Flags.AccPublic);
        when(mockMethods[1].getElementName()).thenReturn("setLocale");
        when(mockMethods[1].getParameterTypes()).thenReturn(
                new String[] { Signature.createTypeSignature("java.util.Locale", true) });

        mockMethods[2] = mock(IMethod.class);
        when(mockMethods[2].getFlags()).thenReturn(Flags.AccPublic | Flags.AccStatic);
        when(mockMethods[2].getElementName()).thenReturn("staticLocale");
        when(mockMethods[2].getParameterTypes()).thenReturn(
                new String[] { Signature.createTypeSignature("java.util.Locale", true) });

        mockMethods[3] = mock(IMethod.class);
        when(mockMethods[3].getFlags()).thenReturn(Flags.AccPublic | Flags.AccStatic);
        when(mockMethods[3].getElementName()).thenReturn("setConstant");
        when(mockMethods[3].getParameterTypes()).thenReturn(
                new String[] { Signature.createTypeSignature("java.lang.String", true) });

        mockMethods[4] = mock(IMethod.class);
        when(mockMethods[4].getFlags()).thenReturn(Flags.AccPrivate);
        when(mockMethods[4].getElementName()).thenReturn("setStatic");
        when(mockMethods[4].getParameterTypes()).thenReturn(
                new String[] { Signature.createTypeSignature("java.lang.String", true) });

        mockMethods[5] = mock(IMethod.class);
        when(mockMethods[5].getFlags()).thenReturn(Flags.AccPublic);
        when(mockMethods[5].getElementName()).thenReturn("setAge");
        when(mockMethods[5].getParameterTypes())
                .thenReturn(new String[] { Signature.createTypeSignature("int", true) });

        mockMethods[6] = mock(IMethod.class);
        when(mockMethods[6].getFlags()).thenReturn(Flags.AccPublic);
        when(mockMethods[6].getElementName()).thenReturn("setMulti");
        when(mockMethods[6].getParameterTypes())
                .thenReturn(
                        new String[] { Signature.createTypeSignature("int", true),
                                Signature.createTypeSignature("short", true) });

        mockMethods[7] = mock(IMethod.class);
        when(mockMethods[7].getFlags()).thenReturn(Flags.AccPublic);
        when(mockMethods[7].getElementName()).thenReturn("setNone");
        when(mockMethods[7].getParameterTypes()).thenReturn(new String[0]);

        when(mockType.getMethods()).thenReturn(mockMethods);

        return mockCompilationUnit;
    }
}
