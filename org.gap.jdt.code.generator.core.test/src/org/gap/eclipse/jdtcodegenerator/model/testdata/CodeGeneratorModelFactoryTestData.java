package org.gap.eclipse.jdtcodegenerator.model.testdata;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.gap.eclipse.jdtcodegenerator.model.JavaBeanModel;
import org.gap.eclipse.jdtcodegenerator.model.JavaBeanProperty;
import org.gap.eclipse.jdtcodegenerator.model.JavaImport;

public class CodeGeneratorModelFactoryTestData {
    public static final String UNWANTED_IMPORT = "java.util.List";
    public static final String WILD_CARD_IMPORT_ONE = "java.io.*";
    public static final String WILD_CARD_IMPORT_TWO = "com.sun.*";
    public static final String CLASS_NAME = "DataClass";
    public static final String CLASS_PACKAGE = "sample.pkg";
    public static final String OUT_DIR = "src/sample/builder";
    public static final String TARGET_PKG = "sample.builder";

    public static JavaBeanModel createModelWithUnwantedImports() {
        final JavaBeanModel mockBeanModel = mock(JavaBeanModel.class);
        final List<JavaImport> importList = Arrays.asList(new JavaImport("java.util.Locale"), new JavaImport(
                UNWANTED_IMPORT));
        final List<JavaBeanProperty> propertyList = Arrays.asList(new JavaBeanProperty(false, "locale", "Locale", "",
                ""));

        when(mockBeanModel.getImports()).thenReturn(importList);
        when(mockBeanModel.getProperties()).thenReturn(propertyList);
        when(mockBeanModel.getClassName()).thenReturn(CLASS_NAME);
        when(mockBeanModel.getPackageName()).thenReturn(CLASS_PACKAGE);

        return mockBeanModel;
    }

    public static JavaBeanModel createModelWithWildCardImports() {
        final JavaBeanModel mockBeanModel = mock(JavaBeanModel.class);
        final List<JavaImport> importList = Arrays.asList(new JavaImport("java.util.Locale"), new JavaImport(
                WILD_CARD_IMPORT_ONE), new JavaImport(WILD_CARD_IMPORT_TWO));
        final List<JavaBeanProperty> propertyList = Arrays.asList(new JavaBeanProperty(false, "locale", "Locale", "",
                ""));

        when(mockBeanModel.getImports()).thenReturn(importList);
        when(mockBeanModel.getProperties()).thenReturn(propertyList);
        when(mockBeanModel.getClassName()).thenReturn(CLASS_NAME);
        when(mockBeanModel.getPackageName()).thenReturn(CLASS_PACKAGE);

        return mockBeanModel;
    }

    public static JavaBeanModel createModel() {
        final JavaBeanModel mockBeanModel = mock(JavaBeanModel.class);
        final List<JavaImport> importList = Arrays.asList(new JavaImport("java.util.Locale"));
        final List<JavaBeanProperty> propertyList = Arrays.asList(new JavaBeanProperty(false, "locale", "Locale", "",
                ""));

        when(mockBeanModel.getImports()).thenReturn(importList);
        when(mockBeanModel.getProperties()).thenReturn(propertyList);
        when(mockBeanModel.getClassName()).thenReturn(CLASS_NAME);
        when(mockBeanModel.getPackageName()).thenReturn(CLASS_PACKAGE);

        return mockBeanModel;
    }

    public static JavaBeanModel createModelWithEnums() {
        final JavaBeanModel mockBeanModel = mock(JavaBeanModel.class);
        final List<JavaImport> importList = Arrays.asList(new JavaImport("java.util.Locale"));
        final List<JavaBeanProperty> propertyList = Arrays.asList(new JavaBeanProperty(false, "locale", "Locale", "",
                ""), new JavaBeanProperty(false, "state", "State", "",
                        ""), new JavaBeanProperty(false, "workerState", "java.lang.Thread.State", "",
                                ""));
        final List<String> enumTypeNames = Arrays.asList("State");
        
        when(mockBeanModel.getImports()).thenReturn(importList);
        when(mockBeanModel.getProperties()).thenReturn(propertyList);
        when(mockBeanModel.getClassName()).thenReturn(CLASS_NAME);
        when(mockBeanModel.getPackageName()).thenReturn(CLASS_PACKAGE);
        when(mockBeanModel.getInnerTypeNames()).thenReturn(enumTypeNames);

        return mockBeanModel;
    }
    
    
    public static IPackageFragment createTargetPackage() throws JavaModelException {
        final IPackageFragment mockIPackageFragment = mock(IPackageFragment.class);
        final IResource mockIResource = mock(IResource.class);
        final IPath mockIPath = mock(IPath.class);

        when(mockIPackageFragment.getElementName()).thenReturn(TARGET_PKG);
        when(mockIPackageFragment.getUnderlyingResource()).thenReturn(mockIResource);
        when(mockIResource.getRawLocation()).thenReturn(mockIPath);
        when(mockIPath.toString()).thenReturn(OUT_DIR);

        return mockIPackageFragment;
    }

    public static JavaBeanModel createModelWithSamePackageImports_SameTargetPackage() {
        final JavaBeanModel mockBeanModel = mock(JavaBeanModel.class);
        final List<JavaImport> importList = Arrays.asList(new JavaImport("java.util.Locale"));
        final List<JavaBeanProperty> propertyList = Arrays.asList(new JavaBeanProperty(false, "locale", "Locale", "",
                ""), new JavaBeanProperty(false, "samepkgData", "SamePkgData", "", ""), new JavaBeanProperty(false,
                "name", "String", "", ""), new JavaBeanProperty(false, "age", "int", "", ""), new JavaBeanProperty(
                false, "otherNames", "String[]", "", "", true, "String"));

        when(mockBeanModel.getImports()).thenReturn(importList);
        when(mockBeanModel.getProperties()).thenReturn(propertyList);
        when(mockBeanModel.getClassName()).thenReturn(CLASS_NAME);
        when(mockBeanModel.getPackageName()).thenReturn(TARGET_PKG);

        return mockBeanModel;
    }

    public static JavaBeanModel createModelWithSamePackageImports_DiffTargetPackage() {
        final JavaBeanModel mockBeanModel = mock(JavaBeanModel.class);
        final List<JavaImport> importList = Arrays.asList(new JavaImport("java.util.Locale"));
        final List<JavaBeanProperty> propertyList = Arrays.asList(new JavaBeanProperty(false, "locale", "Locale", "",
                ""), new JavaBeanProperty(false, "samepkgData", "SamePkgData", "", ""), new JavaBeanProperty(false,
                "name", "String", "", ""), new JavaBeanProperty(false, "age", "int", "", ""), new JavaBeanProperty(
                false, "otherNames", "String[]", "", "", true, "String"));

        when(mockBeanModel.getImports()).thenReturn(importList);
        when(mockBeanModel.getProperties()).thenReturn(propertyList);
        when(mockBeanModel.getClassName()).thenReturn(CLASS_NAME);
        when(mockBeanModel.getPackageName()).thenReturn(CLASS_PACKAGE);

        return mockBeanModel;
    }
}
