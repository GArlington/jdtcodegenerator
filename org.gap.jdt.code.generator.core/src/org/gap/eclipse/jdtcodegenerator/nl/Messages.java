package org.gap.eclipse.jdtcodegenerator.nl;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "org.gap.eclipse.jdtcodegenerator.nl.messages"; //$NON-NLS-1$
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }

    public static String BBC_GEN_FAILURE;
}
