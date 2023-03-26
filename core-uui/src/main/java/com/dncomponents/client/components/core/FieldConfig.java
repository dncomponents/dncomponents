/*
 * Copyright 2023 dncomponents
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

package com.dncomponents.client.components.core;

import com.dncomponents.client.components.core.validation.Validator;
import com.dncomponents.client.components.table.header.filter.FilterUtil;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author nikolasavic
 */
public class FieldConfig<T, M> {

    protected BiConsumer<T, M> fieldSetter;
    protected Function<T, M> fieldGetter;
    private Validator<M> validator;
    private String name = "";
    private String helperText;
    private String successText;
    private Class clazz = String.class;
    private ValueRenderer<M> renderer = (value, valuePanel) -> valuePanel.textContent = value + "";
    private CellEditorFactory<T, M> cellEditorFactory = m -> FilterUtil.getComponent(getClazz());

    public FieldConfig(Function<T, M> fieldGetter) {
        this.fieldGetter = fieldGetter;
    }

    public FieldConfig(BiConsumer<T, M> fieldSetter, Function<T, M> fieldGetter) {
        this.fieldSetter = fieldSetter;
        this.fieldGetter = fieldGetter;
    }

    public FieldConfig(BiConsumer<T, M> fieldSetter) {
        this.fieldSetter = fieldSetter;
    }

    public FieldConfig(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter) {
        this.fieldGetter = fieldGetter;
        this.fieldSetter = fieldSetter;
    }

    public void setFieldGetter(Function<T, M> fieldGetter) {
        this.fieldGetter = fieldGetter;
    }

    public void setFieldSetter(BiConsumer<T, M> fieldSetter) {
        this.fieldSetter = fieldSetter;
    }

    public BiConsumer<T, M> getFieldSetter() {
        return fieldSetter;
    }

    public Function<T, M> getFieldGetter() {
        return fieldGetter;
    }

    public void setValidator(Validator<M> validator) {
        this.validator = validator;
    }

    public Validator<M> getValidator() {
        return validator;
    }

    public void setCellEditorFactory(CellEditorFactory<T, M> cellEditorFactory) {
        this.cellEditorFactory = cellEditorFactory;
    }

    public CellEditorFactory<T, M> getCellEditorFactory() {
        return cellEditorFactory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public String getHelperText() {
        return helperText;
    }

    public void setHelperText(String helperText) {
        this.helperText = helperText;
    }

    public void setSuccessText(String successText) {
        this.successText = successText;
    }

    public String getSuccessText() {
        return successText;
    }

    public void setRenderer(ValueRenderer<M> renderer) {
        this.renderer = renderer;
    }

    public ValueRenderer<M> getRenderer() {
        return renderer;
    }

    public static class Builder<T, M> {
        protected BiConsumer<T, M> fieldSetter;
        protected Function<T, M> fieldGetter;
        private Validator<M> validator;
        private String name = "";
        private String helperText;
        private String successText;
        private Class clazz;
        private ValueRenderer<M> renderer;
        private CellEditorFactory<T, M> cellEditorFactory;

        public Builder(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter) {
            this.fieldSetter = fieldSetter;
            this.fieldGetter = fieldGetter;
        }

        public Builder(Function<T, M> fieldGetter) {
            this.fieldGetter = fieldGetter;
        }

        public Builder() {
        }

        public Builder<T, M> setFieldSetter(BiConsumer<T, M> fieldSetter) {
            this.fieldSetter = fieldSetter;
            return this;
        }

        public Builder<T, M> setFieldGetter(Function<T, M> fieldGetter) {
            this.fieldGetter = fieldGetter;
            return this;
        }

        public Builder<T, M> setValidator(Validator<M> validator) {
            this.validator = validator;
            return this;
        }

        public Builder<T, M> setCellEditorFactory(CellEditorFactory<T, M> cellEditorFactory) {
            this.cellEditorFactory = cellEditorFactory;
            return this;
        }

        public Builder<T, M> setClazz(Class clazz) {
            this.clazz = clazz;
            return this;
        }


        public Builder<T, M> setName(String columnName) {
            this.name = columnName;
            return this;
        }

        public Builder<T, M> setHelperText(String helperText) {
            this.helperText = helperText;
            return this;
        }

        public Builder<T, M> setSuccessText(String successText) {
            this.successText = successText;
            return this;
        }

        public Builder<T, M> setRenderer(ValueRenderer<M> renderer) {
            this.renderer = renderer;
            return this;
        }

        public FieldConfig<T, M> build() {
            final FieldConfig<T, M> fieldConfig = new FieldConfig<>(this.fieldGetter, this.fieldSetter);
            if (this.cellEditorFactory != null)
                fieldConfig.setCellEditorFactory(this.cellEditorFactory);
            if (this.name != null)
                fieldConfig.setName(this.name);
            if (this.helperText != null)
                fieldConfig.setHelperText(this.helperText);
            if (this.successText != null)
                fieldConfig.setSuccessText(this.successText);
            if (this.validator != null)
                fieldConfig.setValidator(this.validator);
            if (this.clazz != null)
                fieldConfig.setClazz(this.clazz);
            if (this.renderer != null)
                fieldConfig.setRenderer(this.renderer);
            return fieldConfig;
        }
    }

}
