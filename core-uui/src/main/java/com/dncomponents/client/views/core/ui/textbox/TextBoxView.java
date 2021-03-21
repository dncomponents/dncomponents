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

    void setPlaceHolder(String placeHolder);

    void setError(boolean b);

    void setErrorMessage(String errorMessage);

    default void setValid(boolean b) {

    }

    default void setHelperText(String helperText) {

    }

    default void setLabel(String labelText) {

    }
}
