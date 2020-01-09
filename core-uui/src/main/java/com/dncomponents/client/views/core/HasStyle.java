package com.dncomponents.client.views.core;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author nikolasavic
 */
public interface HasStyle {
    static String appendString(HasStyle hasStyle) {
        if (hasStyle != null) {
            return " " + hasStyle.getStyle();
        } else {
            return "";
        }
    }

    static String appendString(String style) {
        if (style != null) {
            return " " + style;
        } else {
            return "";
        }
    }

    String getStyle();

    interface StyleCmd extends IsSerializable {
        String getStyle();
    }

}
