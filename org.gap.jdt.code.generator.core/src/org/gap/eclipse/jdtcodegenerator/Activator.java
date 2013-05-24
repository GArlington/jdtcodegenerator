package org.gap.eclipse.jdtcodegenerator;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.gap.eclipse.jdtcodegenerator.generator.CodeGeneatorFactory;
import org.gap.eclipse.jdtcodegenerator.generator.CodeGeneratorFactoryImpl;
import org.gap.eclipse.jdtcodegenerator.model.JavaBeanModelFactory;
import org.gap.eclipse.jdtcodegenerator.model.JavaBeanModelFactoryImpl;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "jdt-code-generator"; //$NON-NLS-1$

    // The shared instance
    private static Activator plugin;

    private JavaBeanModelFactory javaBeanModelFactory;

    private CodeGeneatorFactory codeGeneatorFactory;

    /**
     * The constructor
     */
    public Activator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        javaBeanModelFactory = new JavaBeanModelFactoryImpl();
        codeGeneatorFactory = new CodeGeneratorFactoryImpl();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        javaBeanModelFactory = null;
        codeGeneatorFactory = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    /**
     * Returns the java bean model factory instance to be used.
     * 
     * @return An instance of JavaBeanModelFactory which cannot be null if the
     *         plugin is loaded.
     */
    public JavaBeanModelFactory getJavaBeanModelFactory() {
        return javaBeanModelFactory;
    }

    /**
     * Returns the code generator factory instance to be used.
     * 
     * @return An instance of CodeGeneatorFactory which cannot be null if the
     *         plugin is loaded.
     */
    public CodeGeneatorFactory getCodeGeneatorFactory() {
        return codeGeneatorFactory;
    }

}
