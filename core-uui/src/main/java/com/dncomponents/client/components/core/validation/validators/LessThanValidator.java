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

package com.dncomponents.client.components.core.validation.validators;

import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.components.core.validation.Validator;


public class LessThanValidator<T extends Number & Comparable> implements Validator<T> {
    private T numb;
    private String message;

    public LessThanValidator(T numb) {
        this.numb = numb;
    }

    public LessThanValidator(T numb, String message) {
        this.numb = numb;
        this.message = message;
    }

    @Override
    public void validate(T value) throws ValidationException {
        if (value.compareTo(numb) != -1 || value.compareTo(numb) == 0)
            throw new ValidationException(message != null ? message : "The value has to be less than " + numb);
    }

    public static void main(String[] args) {
        LessThanValidator<Integer> lb = new LessThanValidator<>(22, "less than " + 22);
        try {
            lb.validate(2);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}
