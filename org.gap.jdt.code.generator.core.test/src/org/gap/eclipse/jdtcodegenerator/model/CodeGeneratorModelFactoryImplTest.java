package org.gap.eclipse.jdtcodegenerator.model;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.eclipse.jdt.core.JavaModelException;
import org.fest.assertions.Condition;
import org.gap.eclipse.jdtcodegenerator.model.testdata.CodeGeneratorModelFactoryTestData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CodeGeneratorModelFactoryImplTest {
    private CodeGeneratorModelFactoryImpl generatorModelFactory;

    @BeforeMethod
    public void beforeMethod() {
        generatorModelFactory = new CodeGeneratorModelFactoryImpl(new JavaBeanModelFactoryImpl());
    }

    @AfterMethod
    public void afterMethod() {
        generatorModelFactory = null;
    }

    @Test
    public void testCreateBuilderClassGeneratorModel() throws ModelCreationException, JavaModelException {

        final JavaBeanModel inputModel = CodeGeneratorModelFactoryTestData.createModel();
        final CodeGeneratorModel result = generatorModelFactory.createBuilderClassGeneratorModel(inputModel,
                CodeGeneratorModelFactoryTestData.createTargetPackage());

        assertThat(result).as("Result").isNotNull();
        assertThat(result.getTargetPackage()).as("Target Package").isEqualTo(
                CodeGeneratorModelFactoryTestData.TARGET_PKG);
        assertThat(result.getOutputDir()).as("OutDir").isEqualTo(CodeGeneratorModelFactoryTestData.OUT_DIR);
        assertThat(result.getBeanModel()).as("Bean Model").isNotNull().isNotSameAs(inputModel);

        assertThat(result.getBeanModel().getImports()).hasSize(1).isNot(new Condition<List<?>>() {

            @SuppressWarnings("unchecked")
            @Override
            public boolean matches(List<?> value) {
                return ((List<JavaImport>) value).get(0).getImportDefinition()
                        .equals(CodeGeneratorModelFactoryTestData.UNWANTED_IMPORT);
            }
        });
    }

    @Test
    public void testCreateBuilderClassGeneratorModel_UnWantedImports() throws ModelCreationException,
            JavaModelException {

        final JavaBeanModel inputModel = CodeGeneratorModelFactoryTestData.createModelWithUnwantedImports();
        final CodeGeneratorModel result = generatorModelFactory.createBuilderClassGeneratorModel(inputModel,
                CodeGeneratorModelFactoryTestData.createTargetPackage());

        assertThat(result).as("Result").isNotNull();
        assertThat(result.getTargetPackage()).as("Target Package").isEqualTo(
                CodeGeneratorModelFactoryTestData.TARGET_PKG);
        assertThat(result.getOutputDir()).as("OutDir").isEqualTo(CodeGeneratorModelFactoryTestData.OUT_DIR);
        assertThat(result.getBeanModel()).as("Bean Model").isNotNull().isNotSameAs(inputModel);

        assertThat(result.getBeanModel().getImports()).hasSize(1).isNot(new Condition<List<?>>() {

            @SuppressWarnings("unchecked")
            @Override
            public boolean matches(List<?> value) {
                return ((List<JavaImport>) value).get(0).getImportDefinition()
                        .equals(CodeGeneratorModelFactoryTestData.UNWANTED_IMPORT);
            }
        });
    }

    @Test
    public void testCreateBuilderClassGeneratorModel_WildCardImports() throws ModelCreationException,
            JavaModelException {

        final JavaBeanModel inputModel = CodeGeneratorModelFactoryTestData.createModelWithWildCardImports();
        final CodeGeneratorModel result = generatorModelFactory.createBuilderClassGeneratorModel(inputModel,
                CodeGeneratorModelFactoryTestData.createTargetPackage());

        assertThat(result).as("Result").isNotNull();
        assertThat(result.getTargetPackage()).as("Target Package").isEqualTo(
                CodeGeneratorModelFactoryTestData.TARGET_PKG);
        assertThat(result.getOutputDir()).as("OutDir").isEqualTo(CodeGeneratorModelFactoryTestData.OUT_DIR);
        assertThat(result.getBeanModel()).as("Bean Model").isNotNull().isNotSameAs(inputModel);

        assertThat(result.getBeanModel().getImports()).hasSize(3).is(new Condition<List<?>>() {

            @SuppressWarnings("unchecked")
            @Override
            public boolean matches(List<?> value) {
                return ((List<JavaImport>) value).get(1).getImportDefinition()
                        .equals(CodeGeneratorModelFactoryTestData.WILD_CARD_IMPORT_ONE)
                        && ((List<JavaImport>) value).get(2).getImportDefinition()
                                .equals(CodeGeneratorModelFactoryTestData.WILD_CARD_IMPORT_TWO);
            }
        });
    }

    @Test
    public void testCreateBuilderClassGeneratorModel_SamePkgImport_SameTarget() throws ModelCreationException,
            JavaModelException {

        final JavaBeanModel inputModel = CodeGeneratorModelFactoryTestData
                .createModelWithSamePackageImports_SameTargetPackage();
        final CodeGeneratorModel result = generatorModelFactory.createBuilderClassGeneratorModel(inputModel,
                CodeGeneratorModelFactoryTestData.createTargetPackage());

        assertThat(result).as("Result").isNotNull();
        assertThat(result.getTargetPackage()).as("Target Package").isEqualTo(
                CodeGeneratorModelFactoryTestData.TARGET_PKG);
        assertThat(result.getOutputDir()).as("OutDir").isEqualTo(CodeGeneratorModelFactoryTestData.OUT_DIR);
        assertThat(result.getBeanModel()).as("Bean Model").isNotNull().isNotSameAs(inputModel);

        assertThat(result.getBeanModel().getImports()).hasSize(1).is(new Condition<List<?>>() {

            @SuppressWarnings("unchecked")
            @Override
            public boolean matches(List<?> value) {
                return ((List<JavaImport>) value).get(0).getImportDefinition().equals("java.util.Locale");
            }
        });
    }

    @Test
    public void testCreateBuilderClassGeneratorModel_SamePkgImport_DiffTarget() throws ModelCreationException,
            JavaModelException {

        final JavaBeanModel inputModel = CodeGeneratorModelFactoryTestData
                .createModelWithSamePackageImports_DiffTargetPackage();
        final CodeGeneratorModel result = generatorModelFactory.createBuilderClassGeneratorModel(inputModel,
                CodeGeneratorModelFactoryTestData.createTargetPackage());

        assertThat(result).as("Result").isNotNull();
        assertThat(result.getTargetPackage()).as("Target Package").isEqualTo(
                CodeGeneratorModelFactoryTestData.TARGET_PKG);
        assertThat(result.getOutputDir()).as("OutDir").isEqualTo(CodeGeneratorModelFactoryTestData.OUT_DIR);
        assertThat(result.getBeanModel()).as("Bean Model").isNotNull().isNotSameAs(inputModel);

        assertThat(result.getBeanModel().getImports()).hasSize(2).is(new Condition<List<?>>() {

            @SuppressWarnings("unchecked")
            @Override
            public boolean matches(List<?> value) {
                return ((List<JavaImport>) value).get(0).getImportDefinition().equals("java.util.Locale")
                        && ((List<JavaImport>) value).get(1).getImportDefinition().equals("sample.pkg.SamePkgData");
            }
        });
    }

    @Test
    public void testCreateBuilderClassGeneratorModel_WithEnumsInClass() throws ModelCreationException,
            JavaModelException {

        final JavaBeanModel inputModel = CodeGeneratorModelFactoryTestData.createModelWithEnums();
        final CodeGeneratorModel result = generatorModelFactory.createBuilderClassGeneratorModel(inputModel,
                CodeGeneratorModelFactoryTestData.createTargetPackage());

        assertThat(result).as("Result").isNotNull();
        assertThat(result.getTargetPackage()).as("Target Package").isEqualTo(
                CodeGeneratorModelFactoryTestData.TARGET_PKG);
        assertThat(result.getOutputDir()).as("OutDir").isEqualTo(CodeGeneratorModelFactoryTestData.OUT_DIR);
        assertThat(result.getBeanModel()).as("Bean Model").isNotNull().isNotSameAs(inputModel);
        assertThat(result.getBeanModel().getProperties().get(1).getType()).as("Enum Property Type 1").isEqualTo("State");
        assertThat(result.getBeanModel().getProperties().get(2).getType()).as("Enum Property Type 2").isEqualTo("java.lang.Thread.State");
        assertThat(result.getBeanModel().getImports()).hasSize(2).is(new Condition<List<?>>() {

            @SuppressWarnings("unchecked")
            @Override
            public boolean matches(List<?> value) {
                return ((List<JavaImport>) value).get(0).getImportDefinition().equals("java.util.Locale")
                        && ((List<JavaImport>) value)
                                .get(1)
                                .getImportDefinition()
                                .equals(CodeGeneratorModelFactoryTestData.CLASS_PACKAGE.concat(".")
                                        .concat(CodeGeneratorModelFactoryTestData.CLASS_NAME).concat(".State"));
            }
        });

    }

}
