package org.gap.eclipse.jdtcodegenerator.model;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Locale;

import org.eclipse.jdt.core.JavaModelException;
import org.gap.eclipse.jdtcodegenerator.model.testdata.TestData;
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
        javaBeanModelFactory = new JavaBeanModelFactory();
    }

    @Test
    private void testCreateModelForBuilderClass_StandardBean() throws ModelCreationException, JavaModelException {
        final JavaBeanModel model = javaBeanModelFactory.createModelForStandardBean(TestData
                .createSetterMethodDataSet());
        assertThat(model).as("Model").isNotNull();
        assertThat(model.getClassName()).as("Model.ClassName").isNotNull().isEqualTo("SetterMethodDataSet");
        assertThat(model.getPackageName()).as("Model.PackageName").isNotNull()
                .isEqualTo("org.gap.eclipse.jdtcodegenerator.model.testdata");

        assertThat(model.getProperties()).as("Model.Properties").isNotNull().hasSize(3);
        assertThat(model.getProperties().get(0).getName()).as("Property 0 Name").isEqualTo("name");
        assertThat(model.getProperties().get(0).getType()).as("Property 0 Type").isEqualTo(String.class.getName());
        assertThat(model.getProperties().get(0).getGetterName()).as("Property 0 Getter").isEmpty();
        assertThat(model.getProperties().get(0).getSetterName()).as("Property 0 Setter").isEqualTo("setName");

        assertThat(model.getProperties().get(1).getName()).as("Property 1 Name").isEqualTo("locale");
        assertThat(model.getProperties().get(1).getType()).as("Property 1 Type").isEqualTo(Locale.class.getName());
        assertThat(model.getProperties().get(1).getGetterName()).as("Property 1 Getter").isEmpty();
        assertThat(model.getProperties().get(1).getSetterName()).as("Property 1 Setter").isEqualTo("setLocale");

        assertThat(model.getProperties().get(2).getName()).as("Property 2 Name").isEqualTo("age");
        assertThat(model.getProperties().get(2).getType()).as("Property 3 Type").isEqualTo(Integer.TYPE.getName());
        assertThat(model.getProperties().get(2).getGetterName()).as("Property 2 Getter").isEmpty();
        assertThat(model.getProperties().get(2).getSetterName()).as("Property 2 Setter").isEqualTo("setAge");

    }

    @Test
    private void testCreateModelForBuilderClass_PublicField() throws ModelCreationException, JavaModelException {
        final JavaBeanModel model = javaBeanModelFactory.createModelForPublicFieldProperties(TestData
                .createPublicFieldDataSet());
        assertThat(model).as("Model").isNotNull();
        assertThat(model.getClassName()).as("Model.ClassName").isNotNull().isEqualTo("PublicFieldDataSet");
        assertThat(model.getPackageName()).as("Model.PackageName").isNotNull()
                .isEqualTo("org.gap.eclipse.jdtcodegenerator.model.testdata");

        assertThat(model.getProperties()).as("Model.Properties").isNotNull().hasSize(3);
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
    }
}
