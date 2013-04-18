package org.gap.eclipse.jdtcodegenerator.model;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Locale;

import org.gap.eclipse.jdtcodegenerator.model.testdata.StandardSampleBean;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test case for {@link JavaBeanModelFactoryTest}
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
    private void testCreateModelForBuilderClass() throws ModelCreationException {
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

}
