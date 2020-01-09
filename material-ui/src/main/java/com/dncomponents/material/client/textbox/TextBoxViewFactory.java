package com.dncomponents.material.client.textbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.material.client.textbox.MdcTextBoxViewImpl.MdcTextBoxViewBuilder;
import com.google.gwt.core.client.GWT;
import elemental2.dom.HTMLTemplateElement;

import java.util.Collections;
import java.util.Map;

public class TextBoxViewFactory extends AbstractPluginHelper implements ViewFactory<MdcTextBoxViewImpl> {


    private static TextBoxViewFactory instance;

    private TextBoxViewFactory() {
        arguments.put(MdcTextBoxViewBuilder.typeId, TextBoxType.lookUp.toStringList());
        arguments.put(MdcTextBoxViewBuilder.labelId, Collections.emptyList());
        arguments.put(MdcTextBoxViewBuilder.leadingIconId, MaterialIcons.lookUp.toStringList());
        arguments.put(MdcTextBoxViewBuilder.trailingIconId, MaterialIcons.lookUp.toStringList());
        arguments.put(MdcTextBoxViewBuilder.helperTextId, Collections.emptyList());
        arguments.put(MdcTextBoxViewBuilder.characterCounterId, Collections.emptyList());
    }

    public static TextBoxViewFactory getInstance() {
        if (instance == null)
            instance = new TextBoxViewFactory();
        return instance;
    }

    @Override
    public MdcTextBoxViewImpl getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
        TextBoxType type = TextBoxType.lookUp.getValue(attributes.get(MdcTextBoxViewBuilder.typeId));
        String label = attributes.get(MdcTextBoxViewBuilder.labelId);
        MaterialIcons leadingIcon = MaterialIcons.lookUp.getValue(attributes.get(MdcTextBoxViewBuilder.leadingIconId));
        MaterialIcons trailingIcon = MaterialIcons.lookUp.getValue(attributes.get(MdcTextBoxViewBuilder.trailingIconId));
        String helperText = attributes.get(MdcTextBoxViewBuilder.helperTextId);
        String characterCounterIdString = attributes.get(MdcTextBoxViewBuilder.characterCounterId);
        int characterCounter = 0;
        if (characterCounterIdString != null) {
            try {
                characterCounter = Integer.parseInt(attributes.get(MdcTextBoxViewBuilder.characterCounterId));
            } catch (Exception ex) {
                GWT.log("Error parsing characterCounterId");
            }
        }
        return MdcTextBoxViewBuilder.get()
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
        return MdcTextBoxViewImpl.VIEW_ID;
    }

    @Override
    public Class getClazz() {
        return MdcTextBoxViewImpl.class;
    }

}