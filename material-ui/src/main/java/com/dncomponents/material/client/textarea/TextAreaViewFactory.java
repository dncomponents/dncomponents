package com.dncomponents.material.client.textarea;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.material.client.textarea.TextAreaViewImpl.TextAreaViewBuilder;
import com.dncomponents.material.client.textbox.TextBoxViewImpl.TextBoxViewBuilder;
import com.dncomponents.material.client.textbox.TextBoxType;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLTemplateElement;

import java.util.Collections;
import java.util.Map;

public class TextAreaViewFactory extends AbstractPluginHelper implements ViewFactory<TextAreaViewImpl> {


    private static TextAreaViewFactory instance;

    private TextAreaViewFactory() {
        arguments.put(TextAreaViewBuilder.typeId, TextBoxType.lookUp.toStringList());
        arguments.put(TextAreaViewBuilder.labelId, Collections.emptyList());
        arguments.put(TextAreaViewBuilder.helperTextId, Collections.emptyList());
        arguments.put(TextAreaViewBuilder.characterCounterId, Collections.emptyList());
    }

    public static TextAreaViewFactory getInstance() {
        if (instance == null)
            instance = new TextAreaViewFactory();
        return instance;
    }

    @Override
    public TextAreaViewImpl getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
        TextBoxType type = TextBoxType.lookUp.getValue(attributes.get(TextBoxViewBuilder.typeId));
        String label = attributes.get(TextAreaViewBuilder.labelId);
        String helperText = attributes.get(TextAreaViewBuilder.helperTextId);
        String characterCounterIdString = attributes.get(TextAreaViewBuilder.characterCounterId);
        int characterCounter = 0;
        if (characterCounterIdString != null) {
            try {
                characterCounter = Integer.parseInt(attributes.get(TextAreaViewBuilder.characterCounterId));
            } catch (Exception ex) {
                DomGlobal.console.log("Error parsing characterCounterId");
            }
        }
        return TextAreaViewBuilder.get()
                .setType(type)
                .setLabel(label)
                .setHelperText(helperText)
                .setCharacterCounter(characterCounter)
                .build();
    }

    @Override
    public String getId() {
        return TextAreaViewImpl.VIEW_ID;
    }

    @Override
    public Class getClazz() {
        return TextAreaViewImpl.class;
    }

}