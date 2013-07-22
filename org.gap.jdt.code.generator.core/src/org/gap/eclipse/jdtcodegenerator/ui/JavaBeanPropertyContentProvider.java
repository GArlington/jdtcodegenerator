package org.gap.eclipse.jdtcodegenerator.ui;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.gap.eclipse.jdtcodegenerator.model.JavaBeanModel;
import org.gap.eclipse.jdtcodegenerator.model.JavaBeanProperty;

public class JavaBeanPropertyContentProvider implements IStructuredContentProvider {

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
    }

    @Override
    public Object[] getElements(Object input) {
        if (input instanceof JavaBeanModel) {
            final JavaBeanModel model = (JavaBeanModel) input;
            final List<JavaBeanProperty> properties = model.getProperties();
            return properties.toArray(new JavaBeanProperty[properties.size()]);
        }
        return new Object[0];
    }

}
