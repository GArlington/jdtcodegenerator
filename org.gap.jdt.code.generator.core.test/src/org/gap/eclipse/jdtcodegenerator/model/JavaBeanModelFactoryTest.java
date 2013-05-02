package org.gap.eclipse.jdtcodegenerator.model;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Locale;

import org.gap.eclipse.jdtcodegenerator.model.testdata.PublicFieldSampleBean;
import org.gap.eclipse.jdtcodegenerator.model.testdata.StandardSampleBean;
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
    private void testCreateModelForBuilderClass_StandardBean() throws ModelCreationException {
        final JavaBeanModel model = javaBeanModelFactory.createModelForStandardBean(StandardSampleBean.class);
        assertThat(model).as("Model").isNotNull();
        assertThat(model.getProperties()).as("Model.Properties").isNotNull().hasSize(3);
        assertThat(model.getProperties().get(0).getName()).as("Property 0").isEqualTo("age");
        assertThat(model.getProperties().get(0).getType()).as("Property 0").isEqualTo(Integer.TYPE);
        assertThat(model.getProperties().get(1).getName()).as("Property 1").isEqualTo("locale");
        assertThat(model.getProperties().get(1).getType()).as("Property 1").isEqualTo(Locale.class);
        assertThat(model.getProperties().get(2).getName()).as("Property 2").isEqualTo("name");
        assertThat(model.getProperties().get(2).getType()).as("Property 2").isEqualTo(String.class);
    }

    @Test
    private void testCreateModelForBuilderClass_PublicField() throws ModelCreationException {
        final JavaBeanModel model = javaBeanModelFactory
                .createModelForPublicFieldProperties(PublicFieldSampleBean.class);
        assertThat(model).as("Model").isNotNull();
        assertThat(model.getProperties()).as("Model.Properties").isNotNull().hasSize(3);

        assertThat(model.getProperties().get(0).getName()).as("Property 0 Name").isEqualTo("name");
        assertThat(model.getProperties().get(0).getType()).as("Property 0 Type").isEqualTo(String.class);
        assertThat(model.getProperties().get(0).getGetterName()).as("Property 0 Getter").isEmpty();
        assertThat(model.getProperties().get(0).getSetterName()).as("Property 0 Setter").isEmpty();

        assertThat(model.getProperties().get(1).getName()).as("Property 1 Name").isEqualTo("age");
        assertThat(model.getProperties().get(1).getType()).as("Property 1 Type").isEqualTo(Integer.TYPE);
        assertThat(model.getProperties().get(1).getGetterName()).as("Property 1 Getter").isEmpty();
        assertThat(model.getProperties().get(1).getSetterName()).as("Property 1 Setter").isEmpty();

        assertThat(model.getProperties().get(2).getName()).as("Property 2 Name").isEqualTo("locale");
        assertThat(model.getProperties().get(2).getType()).as("Property 2 Type").isEqualTo(Locale.class);
        assertThat(model.getProperties().get(2).getGetterName()).as("Property 2 Getter").isEmpty();
        assertThat(model.getProperties().get(2).getSetterName()).as("Property 2 Setter").isEmpty();
    }
}
