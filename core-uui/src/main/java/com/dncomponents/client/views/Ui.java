package com.dncomponents.client.views;

/**
 * @author nikolasavic
 */
public class Ui {

    private static boolean debug = true;

    protected static ComponentsViews implementation;

    public static void set(ComponentsViews implementation) {
        Ui.implementation = implementation;
    }

    public static ComponentsViews get() {
        return implementation;
    }

    public static void setDebug(boolean debug) {
        Ui.debug = debug;
    }

    public static boolean isDebug() {
        return debug;
    }

}