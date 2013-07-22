package org.gap.eclipse.jdtcodegenerator.ui;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.gap.eclipse.jdtcodegenerator.model.JavaBeanModel;
import org.gap.eclipse.jdtcodegenerator.model.JavaBeanProperty;

public class GeneratorElementSelectionDialog extends ListSelectionDialog {

    public GeneratorElementSelectionDialog(Shell parent, JavaBeanModel input) {
        super(parent, input, new JavaBeanPropertyContentProvider(), new GeneratorElementLabelProvider(),
                "Select Bean Properties for builder methods");
    }

    private static class GeneratorElementLabelProvider extends LabelProvider {

        @Override
        public String getText(Object element) {
            if (element instanceof JavaBeanProperty) {
                return ((JavaBeanProperty) element).getName();
            }
            return element.toString();
        }
    }
}
