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

package com.dncomponents.client.components.core.validation;

import java.util.ArrayList;
import java.util.List;


public class ValidationException extends Exception {

    List<String> validationMessages = new ArrayList<String>();

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(List<String> validationMessages) {
        this(!validationMessages.isEmpty() ? validationMessages.get(0) : "");
        this.validationMessages = validationMessages;
    }

    public List<String> getValidationMessages() {
        return validationMessages;
    }
}
