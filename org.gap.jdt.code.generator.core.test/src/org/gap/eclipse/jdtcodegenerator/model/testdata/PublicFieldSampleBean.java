package org.gap.eclipse.jdtcodegenerator.model.testdata;

import java.util.Locale;

public class PublicFieldSampleBean {
    public String name;

    public int age;

    public Locale locale;
    
    public static PublicFieldSampleBean instance;

    public static final String constant = "CONST";

    public final String finalOnly = "Final";
}
