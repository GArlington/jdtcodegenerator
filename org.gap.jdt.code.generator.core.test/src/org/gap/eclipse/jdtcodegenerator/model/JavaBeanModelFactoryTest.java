package org.gap.eclipse.jdtcodegenerator.model;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Locale;

import org.eclipse.jdt.core.JavaModelException;
import org.gap.eclipse.jdtcodegenerator.model.testdata.JavaBeanModelFactoryTestData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test case for {@link JavaBeanModelFactoryTest}
 * 
 * @author gayanper
 * 
 */
@Test
public class JavaBeanModelFactoryTest {

    private JavaBeanModelFactory javaBeanModelFactory;

    @BeforeMethod
    public void beforeMethod() {
        javaBeanModelFactory = new JavaBeanModelFactoryImpl();
    }

    @Test
    private void testCreateModelForBuilderClass_SetterMethods() throws ModelCreationException, JavaModelException {
        final JavaBeanModel model = javaBeanModelFactory.createModelForStandardBean(JavaBeanModelFactoryTestData
                .createSetterMethodDataSet());
        assertThat(model).as("Model").isNotNull();
        assertThat(model.getClassName()).as("Model.ClassName").isNotNull().isEqualTo("SetterMethodDataSet");
        assertThat(model.getPackageName()).as("Model.PackageName").isNotNull()
                .isEqualTo("org.gap.eclipse.jdtcodegenerator.model.testdata");
        assertThat(model.getImports()).as("Import List").hasSize(2);
        assertThat(model.getImports().get(0).getImportDefinition()).as("Import").isEqualTo("java.util.Locale");
        assertThat(model.getImports().get(1).getImportDefinition()).as("Import Super").isEqualTo("java.util.Map");

        assertThat(model.getProperties()).as("Model.Properties").isNotNull().hasSize(5);
        assertThat(model.getProperties().get(0).getName()).as("Property 0 Name").isEqualTo("name");
        assertThat(model.getProperties().get(0).getType()).as("Property 0 Type").isEqualTo(String.class.getName());
        assertThat(model.getProperties().get(0).getGetterName()).as("Property 0 Getter").isEmpty();
        assertThat(model.getProperties().get(0).getSetterName()).as("Property 0 Setter").isEqualTo("setName");

        assertThat(model.getProperties().get(1).getName()).as("Property 1 Name").isEqualTo("locale");
        assertThat(model.getProperties().get(1).getType()).as("Property 1 Type").isEqualTo(Locale.class.getName());
        assertThat(model.getProperties().get(1).getGetterName()).as("Property 1 Getter").isEmpty();
        assertThat(model.getProperties().get(1).getSetterName()).as("Property 1 Setter").isEqualTo("setLocale");

        assertThat(model.getProperties().get(2).getName()).as("Property 2 Name").isEqualTo("age");
        assertThat(model.getProperties().get(2).getType()).as("Property 2 Type").isEqualTo(Integer.TYPE.getName());
        assertThat(model.getProperties().get(2).getGetterName()).as("Property 2 Getter").isEmpty();
        assertThat(model.getProperties().get(2).getSetterName()).as("Property 2 Setter").isEqualTo("setAge");
        assertThat(model.getProperties().get(2).isArrayType()).as("Property 2 ArrayType").isFalse();

        assertThat(model.getProperties().get(3).getName()).as("Property 3 Name").isEqualTo("addresses");
        assertThat(model.getProperties().get(3).getType()).as("Property 3 Type").isEqualTo(
                String.class.getName().concat("[]"));
        assertThat(model.getProperties().get(3).getGetterName()).as("Property 3 Getter").isEmpty();
        assertThat(model.getProperties().get(3).getSetterName()).as("Property 3 Setter").isEqualTo("setAddresses");
        assertThat(model.getProperties().get(3).isArrayType()).as("Property 3 ArrayType").isTrue();
        assertThat(model.getProperties().get(3).getComponentType()).as("Property 3 ComponentType").isEqualTo(
                String.class.getName());

        assertThat(model.getProperties().get(4).getName()).as("Property Super Name").isEqualTo("map");
        assertThat(model.getProperties().get(4).getType()).as("Property Super Type").isEqualTo("Map");
        assertThat(model.getProperties().get(4).getGetterName()).as("Property Super Getter").isEmpty();
        assertThat(model.getProperties().get(4).getSetterName()).as("Property Super Setter").isEqualTo("setMap");
        assertThat(model.getProperties().get(4).isArrayType()).as("Property Super ArrayType").isFalse();
        assertThat(model.getProperties().get(4).getComponentType()).as("Property Super ComponentType").isEmpty();
    }

    @Test
    private void testCreateModelForBuilderClass_PublicField() throws ModelCreationException, JavaModelException {
        final JavaBeanModel model = javaBeanModelFactory.createModelForPublicFieldProperties(JavaBeanModelFactoryTestData
                .createPublicFieldDataSet());
        assertThat(model).as("Model").isNotNull();
        assertThat(model.getClassName()).as("Model.ClassName").isNotNull().isEqualTo("PublicFieldDataSet");
        assertThat(model.getPackageName()).as("Model.PackageName").isNotNull()
                .isEqualTo("org.gap.eclipse.jdtcodegenerator.model.testdata");
        assertThat(model.getImports()).as("Import List").hasSize(1);
        assertThat(model.getImports().get(0).getImportDefinition()).as("Import").isEqualTo("java.util.Locale");

        assertThat(model.getProperties()).as("Model.Properties").isNotNull().hasSize(4);
        assertThat(model.getProperties().get(0).getName()).as("Property 0 Name").isEqualTo("name");
        assertThat(model.getProperties().get(0).getType()).as("Property 0 Type").isEqualTo(String.class.getName());
        assertThat(model.getProperties().get(0).getGetterName()).as("Property 0 Getter").isEmpty();
        assertThat(model.getProperties().get(0).getSetterName()).as("Property 0 Setter").isEmpty();

        assertThat(model.getProperties().get(1).getName()).as("Property 1 Name").isEqualTo("locale");
        assertThat(model.getProperties().get(1).getType()).as("Property 1 Type").isEqualTo(Locale.class.getName());
        assertThat(model.getProperties().get(1).getGetterName()).as("Property 1 Getter").isEmpty();
        assertThat(model.getProperties().get(1).getSetterName()).as("Property 1 Setter").isEmpty();

        assertThat(model.getProperties().get(2).getName()).as("Property 2 Name").isEqualTo("age");
        assertThat(model.getProperties().get(2).getType()).as("Property 3 Type").isEqualTo(Integer.TYPE.getName());
        assertThat(model.getProperties().get(2).getGetterName()).as("Property 2 Getter").isEmpty();
        assertThat(model.getProperties().get(2).getSetterName()).as("Property 2 Setter").isEmpty();
        assertThat(model.getProperties().get(2).isArrayType()).as("Property 2 ArrayType").isFalse();

        assertThat(model.getProperties().get(3).getName()).as("Property 3 Name").isEqualTo("addresses");
        assertThat(model.getProperties().get(3).getType()).as("Property 3 Type").isEqualTo(
                String.class.getName().concat("[]"));
        assertThat(model.getProperties().get(3).getGetterName()).as("Property 3 Getter").isEmpty();
        assertThat(model.getProperties().get(3).getSetterName()).as("Property 3 Setter").isEmpty();
        assertThat(model.getProperties().get(3).isArrayType()).as("Property 3 ArrayType").isTrue();
        assertThat(model.getProperties().get(3).getComponentType()).as("Property 3 ComponentType").isEqualTo(
                String.class.getName());

    }
}
