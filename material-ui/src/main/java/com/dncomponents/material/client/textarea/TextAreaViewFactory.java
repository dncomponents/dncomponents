package com.dncomponents.material.client.textarea;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.material.client.textarea.MdcTextAreaViewImpl.MdcTextAreaViewBuilder;
import com.dncomponents.material.client.textbox.MdcTextBoxViewImpl.MdcTextBoxViewBuilder;
import com.dncomponents.material.client.textbox.TextBoxType;
import com.google.gwt.core.client.GWT;
import elemental2.dom.HTMLTemplateElement;

import java.util.Collections;
import java.util.Map;

public class TextAreaViewFactory extends AbstractPluginHelper implements ViewFactory<MdcTextAreaViewImpl> {


    private static TextAreaViewFactory instance;

    private TextAreaViewFactory() {
        arguments.put(MdcTextAreaViewBuilder.typeId, TextBoxType.lookUp.toStringList());
        arguments.put(MdcTextAreaViewBuilder.labelId, Collections.emptyList());
        arguments.put(MdcTextAreaViewBuilder.helperTextId, Collections.emptyList());
        arguments.put(MdcTextAreaViewBuilder.characterCounterId, Collections.emptyList());
    }

    public static TextAreaViewFactory getInstance() {
        if (instance == null)
            instance = new TextAreaViewFactory();
        return instance;
    }

    @Override
    public MdcTextAreaViewImpl getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
        TextBoxType type = TextBoxType.lookUp.getValue(attributes.get(MdcTextBoxViewBuilder.typeId));
        String label = attributes.get(MdcTextAreaViewBuilder.labelId);
        String helperText = attributes.get(MdcTextAreaViewBuilder.helperTextId);
        String characterCounterIdString = attributes.get(MdcTextAreaViewBuilder.characterCounterId);
        int characterCounter = 0;
        if (characterCounterIdString != null) {
            try {
                characterCounter = Integer.parseInt(attributes.get(MdcTextAreaViewBuilder.characterCounterId));
            } catch (Exception ex) {
                GWT.log("Error parsing characterCounterId");
            }
        }
        return MdcTextAreaViewBuilder.get()
                .setType(type)
                .setLabel(label)
                .setHelperText(helperText)
                .setCharacterCounter(characterCounter)
                .build();
    }

    @Override
    public String getId() {
        return MdcTextAreaViewImpl.VIEW_ID;
    }

    @Override
    public Class getClazz() {
        return MdcTextAreaViewImpl.class;
    }

}