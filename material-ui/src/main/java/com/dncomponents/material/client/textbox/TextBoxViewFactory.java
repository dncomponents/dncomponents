package com.dncomponents.material.client.textbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.material.client.textbox.TextBoxViewImpl.TextBoxViewBuilder;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLTemplateElement;

import java.util.Collections;
import java.util.Map;

public class TextBoxViewFactory extends AbstractPluginHelper implements ViewFactory<TextBoxViewImpl> {


    private static TextBoxViewFactory instance;

    private TextBoxViewFactory() {
        arguments.put(TextBoxViewBuilder.typeId, TextBoxType.lookUp.toStringList());
        arguments.put(TextBoxViewBuilder.labelId, Collections.emptyList());
        arguments.put(TextBoxViewBuilder.leadingIconId, MaterialIcons.lookUp.toStringList());
        arguments.put(TextBoxViewBuilder.trailingIconId, MaterialIcons.lookUp.toStringList());
        arguments.put(TextBoxViewBuilder.helperTextId, Collections.emptyList());
        arguments.put(TextBoxViewBuilder.characterCounterId, Collections.emptyList());
    }

    public static TextBoxViewFactory getInstance() {
        if (instance == null)
            instance = new TextBoxViewFactory();
        return instance;
    }

    @Override
    public TextBoxViewImpl getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
        TextBoxType type = TextBoxType.lookUp.getValue(attributes.get(TextBoxViewBuilder.typeId));
        String label = attributes.get(TextBoxViewBuilder.labelId);
        MaterialIcons leadingIcon = MaterialIcons.lookUp.getValue(attributes.get(TextBoxViewBuilder.leadingIconId));
        MaterialIcons trailingIcon = MaterialIcons.lookUp.getValue(attributes.get(TextBoxViewBuilder.trailingIconId));
        String helperText = attributes.get(TextBoxViewBuilder.helperTextId);
        String characterCounterIdString = attributes.get(TextBoxViewBuilder.characterCounterId);
        int characterCounter = 0;
        if (characterCounterIdString != null) {
            try {
                characterCounter = Integer.parseInt(attributes.get(TextBoxViewBuilder.characterCounterId));
            } catch (Exception ex) {
                DomGlobal.console.log("Error parsing characterCounterId");
            }
        }
        return TextBoxViewBuilder.get()
                .setType(type)
                .setLabel(label)
                .setLeadingIcon(leadingIcon)
                .setTrailingIcon(trailingIcon)
                .setHelperText(helperText)
                .setCharacterCounter(characterCounter)
                .build();
    }

    @Override
    public String getId() {
        return TextBoxViewImpl.VIEW_ID;
    }

    @Override
    public Class getClazz() {
        return TextBoxViewImpl.class;
    }

}