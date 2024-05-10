/*
 * Copyright 2024 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dncomponents.client.main.components.appviews.forms;

import com.dncomponents.UiField;
import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.Table;
import com.dncomponents.client.components.autocomplete.Autocomplete;
import com.dncomponents.client.components.core.DefaultCellEditor;
import com.dncomponents.client.components.core.FieldConfig;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.components.core.validation.Validators;
import com.dncomponents.client.components.core.validation.validators.EmailValidator;
import com.dncomponents.client.components.core.validation.validators.EmptyValidator;
import com.dncomponents.client.components.form.Form;
import com.dncomponents.client.components.table.columnclasses.checkboxcolumn.ColumnCheckBox;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.components.textbox.TextBox;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.main.testing.TestingHelper;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.List;


public class FormsAppView implements IsElement {
    private static FormsAppView instance;

    @UiField
    HTMLElement root;
    @UiField
    TextArea logTa;
    @UiField
    Form<FormDto> form;

    public FormsAppView() {
        HtmlBinder.create(FormsAppView.class, this).bind();
        init2();
    }

    private void init2() {
        FieldConfig<FormDto, String> firstNameField =
                new FieldConfig.Builder<FormDto, String>()
                        .setFieldGetter(formDto -> formDto.firstName)
                        .setFieldSetter((formDto, s) -> formDto.firstName = s)
                        .setClazz(String.class)
                        .setName("First name")
                        .setHelperText("Please enter your first name (Can't be empty, must starts with capital letter)")
                        .setSuccessText("Looks good")
                        .setValidator(new Validators<String>()
                                .add(new EmptyValidator<>(), true)
                                .add(value -> {
                                    if (!Character.isUpperCase(value.charAt(0)))
                                        throw new ValidationException("The name should start in uppercase");
                                })
                        )
                        .build();

        FieldConfig<FormDto, String> lastNameField =
                new FieldConfig.Builder<FormDto, String>()
                        .setFieldGetter(formDto -> formDto.lastName)
                        .setFieldSetter((formDto, s) -> formDto.lastName = s)
                        .setClazz(String.class)
                        .setName("Last name")
                        .setHelperText("Please enter your lastName name (Can't be empty, must starts with capital letter)")
                        .setSuccessText("Looks good")
                        .setValidator(new Validators<String>()
                                .add(new EmptyValidator<>(), true)
                                .add(value -> {
                                    if (!Character.isUpperCase(value.charAt(0)))
                                        throw new ValidationException("The last name should start in uppercase");
                                })
                        )
                        .build();

        FieldConfig<FormDto, String> emailField = new FieldConfig.Builder<FormDto, String>()
                .setFieldGetter(formDto -> formDto.email)
                .setFieldSetter((formDto, s) -> formDto.email = s)
                .setClazz(String.class)
                .setName("Email")
                .setHelperText("Please enter valid email (Can't be empty, must be valid email)")
                .setSuccessText("Looks good")
                .setValidator(new Validators<String>()
                        .add(new EmptyValidator<>(), true)
                        .add(new EmailValidator())
                ).setCellEditorFactory(m -> new DefaultCellEditor<>(new TextBox()))
                .build();

        Autocomplete<String> ac = new Autocomplete<>();
        ac.setRowsData(TestingHelper.getColors());
        FieldConfig<FormDto, String> colorField = new FieldConfig.Builder<FormDto, String>()
                .setFieldGetter(formDto -> formDto.color)
                .setFieldSetter((formDto, s) -> formDto.color = s)
                .setHelperText("Enter color")
                .setSuccessText("Looks good!")
                .setName("Color")
                .setClazz(String.class)
                .setCellEditorFactory(m -> new DefaultCellEditor<>(ac))
                .build();

        Table<Fruit> fruitTable = new Table<>();
        fruitTable.setScrollHeight("250px");
        fruitTable.addColumn(new ColumnCheckBox<>(),
                new ColumnConfig<>(Fruit::getName, "Name"),
                new ColumnConfig<>(Fruit::getDescription, "Description"));
        fruitTable.setRowsData(Fruit.fruits);
        fruitTable.drawData();

        FieldConfig<FormDto, List<Fruit>> fruitsField = new FieldConfig.Builder<FormDto, List<Fruit>>()
                .setFieldGetter(formDto -> formDto.fruits)
                .setFieldSetter((formDto, fruits) -> formDto.fruits = fruits)
                .setHelperText("Choose fruits (Can't be empty, can't choose more than three items)")
                .setSuccessText("Looks good!")
                .setName("Fruits")
                .setCellEditorFactory(m -> new DefaultCellEditor(fruitTable))
                .setValidator(new Validators<List<Fruit>>()
                        .add(new EmptyValidator<>(), true)
                        .add(value -> {
                            if (value.size() > 3)
                                throw new ValidationException("Can't choose more than three fruits!");
                        }))
                .setClazz(List.class)
                .build();

        form.addFormConfigs(firstNameField, lastNameField, emailField, colorField, fruitsField);
        form.setFormData(new FormDto());
        form.drawData();
        form.addModelChangedHandler(event ->
                logTa.setValue(event.getModel().toString()));
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    public static FormsAppView getInstance() {
        if (instance == null)
            instance = new FormsAppView();
        return instance;
    }

}
