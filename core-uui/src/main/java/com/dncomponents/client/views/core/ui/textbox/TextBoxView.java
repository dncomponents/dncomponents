package com.dncomponents.client.views.core.ui.textbox;

import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.dom.handlers.OnBlurHandler;
import com.dncomponents.client.views.FocusComponentView;
import elemental2.dom.EventListener;

/**
 * @author nikolasavic
 */
public interface TextBoxView extends FocusComponentView {

    String getValue();

    void setValue(String value);

    void addOnInputChangeHandler(EventListener listener);

    void addOnBlurHandler(OnBlurHandler handler);

    void addOnKeyUpHandler(KeyUpHandler handler);

    void setError(boolean b);
}
