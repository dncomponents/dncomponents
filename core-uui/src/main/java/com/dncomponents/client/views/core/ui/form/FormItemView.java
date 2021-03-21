package com.dncomponents.client.views.core.ui.form;

import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface FormItemView extends View {


    void setLabelText(String labelText);

    void setError(String error);

    void setErrorStyle(boolean b);

    void setHelperText(String helperText);

    void setContent(HTMLElement element);

    <M> HasValue<M> getHasValue();

    void setSuccess(String success);

    void setSuccessStyle(boolean b);

    HTMLElement getMainPanel();


//    class FormItemViewImpl implements FormItemView {
//
//        HTMLElement root = DomUtil.createDiv();
//        HTMLElement label = DomUtil.createDiv();
//        HTMLElement helperTextLabel = DomUtil.createDiv();
//        HTMLElement mainPanel = DomUtil.createDiv();
//        HTMLElement content;
//
//        {
//            root.appendChild(label);
//            root.appendChild(mainPanel);
//            root.appendChild(helperTextLabel);
//            label.classList.add("form-label");
//            helperTextLabel.classList.add("form-text");
//        }
//
//        @Override
//        public HTMLElement asElement() {
//            return root;
//        }
//
//        @Override
//        public void setLabelText(String labelText) {
//            this.label.innerHTML = labelText;
//        }
//
//        @Override
//        public void setError(String errorText) {
//            this.helperTextLabel.classList.add("invalid-feedback");
//            this.mainPanel.classList.add("error_form");
//            if (errorText == null) {
//                this.helperTextLabel.classList.remove("invalid-feedback");
//                this.mainPanel.classList.remove("error_form");
//            }
//            this.helperTextLabel.style.display = "block";
//            this.helperTextLabel.innerHTML = errorText;
//        }
//
//        @Override
//        public void setErrorStyle(boolean b) {
//
//        }
//
//        @Override
//        public void setHelperText(String helperText) {
//            this.helperTextLabel.innerHTML = helperText;
//        }
//
//        @Override
//        public void setContent(HTMLElement element) {
//            this.content = element;
//            mainPanel.appendChild(element);
//        }
//
//        @Override
//        public <M> HasValue<M> getHasValue() {
//            return null;
//        }
//
//        @Override
//        public void setValid(String validText) {
//            if (validText != null) {
//                this.helperTextLabel.innerHTML = validText;
//                this.helperTextLabel.classList.add("valid-feedback");
//            } else
//                this.helperTextLabel.classList.remove("valid-feedback");
//        }
//    }
}


