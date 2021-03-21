package com.dncomponents.client.components.form;

import com.dncomponents.client.components.core.events.HasEnabled;
import com.dncomponents.client.components.core.events.focus.Focusable;
import com.dncomponents.client.components.core.events.focus.HasBlurHandlers;
import com.dncomponents.client.components.core.events.focus.HasFocusHandlers;
import com.dncomponents.client.components.core.events.validator.CanShowError;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.validation.HasHelperText;
import com.dncomponents.client.components.core.validation.HasLabel;
import com.dncomponents.client.components.core.validation.HasModel;
import com.dncomponents.client.components.core.validation.HasValidation;
import com.dncomponents.client.views.IsElement;

/**
 * @author nikolasavic
 */
public interface IsForm<T> extends
        HasValue<T>,
        IsElement,
        Focusable,
        HasFocusHandlers,
        HasBlurHandlers,
        HasEnabled,
        HasHelperText,
        HasLabel,
        HasValidation<T>,
        HasModel,
        CanShowError {
}
