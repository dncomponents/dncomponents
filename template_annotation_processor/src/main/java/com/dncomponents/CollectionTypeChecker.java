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

package com.dncomponents;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Optional;

public class CollectionTypeChecker {
    private final Element classEl;

    public CollectionTypeChecker(Element classEl) {
         this.classEl = classEl;
    }

    public String checkCollectionType(String collectionName) {
        String baseName = extractBaseName(collectionName);

        // Check for fields first
        Optional<? extends Element> optionalField = classEl.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ElementKind.FIELD && e.getSimpleName().toString().equals(baseName))
                .findAny();

        if (optionalField.isPresent()) {
            Element field = optionalField.get();
            return extractFirstTypeParameter(field.asType());
        }

        // Check for methods next
        Optional<? extends Element> optionalMethod = classEl.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ElementKind.METHOD && e.getSimpleName().toString().equals(baseName))
                .findAny();

        if (optionalMethod.isPresent()) {
            ExecutableElement method = (ExecutableElement) optionalMethod.get();
            return extractFirstTypeParameter(method.getReturnType());
        }

        return "";
    }

    private String extractBaseName(String name) {
        int parenthesisIndex = name.indexOf('(');
        if (parenthesisIndex != -1) {
            return name.substring(0, parenthesisIndex);
        }
        return name;
    }

    private String extractFirstTypeParameter(TypeMirror typeMirror) {
        if (typeMirror.getKind() == TypeKind.DECLARED) {
            DeclaredType declaredType = (DeclaredType) typeMirror;
            List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
            if (!typeArguments.isEmpty()) {
                return typeArguments.get(0).toString();
            }
        }
        return "";
    }
}
