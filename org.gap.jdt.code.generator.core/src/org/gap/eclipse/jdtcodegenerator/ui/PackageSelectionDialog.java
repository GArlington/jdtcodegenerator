package org.gap.eclipse.jdtcodegenerator.ui;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * A dialog implementation which is used to list all the source packages for a
 * given project. This implementation only support single selection and the
 * selected package is returned by {@link #getSelectedPackage()}.
 * 
 * @author gayanper
 * 
 */
public class PackageSelectionDialog extends ElementTreeSelectionDialog {

    /**
     * Creates a new instance with the given parameters.
     * 
     * @param parent The parent shell for this dialog.
     * @param javaProject The java project this dialog will used to list source
     *        packages.
     */
    public PackageSelectionDialog(Shell parent, IJavaProject javaProject) {
        super(parent, new WorkbenchLabelProvider(), new ProjectPackageContentProvider());
        setInput(javaProject);
        setAllowMultiple(false);
    }

    /**
     * Returns the selection.
     * 
     * @return The selected package.
     */
    public IPackageFragment getSelectedPackage() {
        return (IPackageFragment) super.getFirstResult();
    }
}
