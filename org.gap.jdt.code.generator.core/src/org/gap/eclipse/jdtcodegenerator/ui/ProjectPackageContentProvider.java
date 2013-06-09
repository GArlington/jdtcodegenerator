package org.gap.eclipse.jdtcodegenerator.ui;

import java.util.ArrayList;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * An implementation of ITreeContentProvider which only display source folders
 * of the project and it's packages.
 * 
 * @author gayanper
 * 
 */
class ProjectPackageContentProvider implements ITreeContentProvider {

    @Override
    public void dispose() {

    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

    }

    @Override
    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof IJavaProject) {
            try {
                final IPackageFragmentRoot[] packageFragmentRoots = ((IJavaProject) inputElement)
                        .getPackageFragmentRoots();
                final ArrayList<IPackageFragmentRoot> sourceRoots = new ArrayList<IPackageFragmentRoot>();

                for (IPackageFragmentRoot fragmentRoot : packageFragmentRoots) {
                    if (fragmentRoot.getKind() == IPackageFragmentRoot.K_SOURCE) {
                        sourceRoots.add(fragmentRoot);
                    }
                }
                return sourceRoots.toArray(new IPackageFragmentRoot[sourceRoots.size()]);
            } catch (JavaModelException ex) {
                // TODO: Add logging.
            }
        }
        return new Object[0];
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof IPackageFragmentRoot) {
            try {
                return ((IPackageFragmentRoot) parentElement).getChildren();
            } catch (JavaModelException ex) {
                // TODO: Add logging.
            }
        }
        return new Object[0];
    }

    @Override
    public Object getParent(Object element) {
        if (element instanceof IJavaElement) {
            return ((IJavaElement) element).getParent();
        }
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        return getChildren(element).length > 0;
    }

}
