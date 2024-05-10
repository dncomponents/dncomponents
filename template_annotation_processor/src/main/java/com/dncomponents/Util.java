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
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Util {
    public static boolean checkIfItsMethod(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (!Character.isLetter(ch)) {
                if (ch == '(') {
                    return true;
                } else if (ch == '.') {
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean containsVariable(String code, String variableName) {
        int index = code.indexOf(variableName);
        while (index >= 0) {
            // Check if the variable name is a standalone identifier
            if ((index == 0 || !Character.isJavaIdentifierPart(code.charAt(index - 1)))
                && (index + variableName.length() == code.length() ||
                    !Character.isJavaIdentifierPart(code.charAt(index + variableName.length())))) {
                return true;
            }
            index = code.indexOf(variableName, index + 1);
        }
        return false;
    }


    public static String replaceFunctionArguments(String code, String avoid, String avoid2) {
        int startIndex = code.indexOf("(");
        int endIndex = code.lastIndexOf(")");

        if (startIndex >= 0 && endIndex >= 0 && (endIndex - startIndex > 1)) {
            String[] argArray = code.substring(startIndex + 1, endIndex).split(",");
            String result = "(";
            for (int i = 0; i < argArray.length; i++) {
                String arg = argArray[i];
                arg = arg.trim();
                if (!arg.equals("true") &&
                    !arg.equals("false") &&
                    !(arg.startsWith("\"")) &&
                    !(arg.startsWith("'")) &&
                    !isNumeric(arg) &&
                    !(avoid != null && (arg.equals(avoid) || arg.startsWith(avoid + "."))) &&
                    !(avoid2 != null && (arg.equals(avoid2) || arg.startsWith(avoid2 + ".")))
                )
                    arg = arg.replace(arg, "d." + arg);
                result += arg + ((i == (argArray.length - 1)) ? ")" : ",");
            }

            code = replaceStringBetweenIndexes(code, startIndex, endIndex, result);
        }
        return "d." + code;
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static String replaceStringBetweenIndexes(String str, int startIndex, int endIndex, String replacement) {
        if (str == null || replacement == null || startIndex < 0 || endIndex >= str.length() || startIndex >= endIndex) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, startIndex));
        sb.append(replacement);
        sb.append(str.substring(endIndex + 1));
        return sb.toString();
    }

    public static String createJavaCode(String value, String avoid, boolean events) {
        //todo handle case (something.getString()+ something.getAnotherString()) for the Supplier
        String result = "";
        List<String> expressions = new ArrayList<>();
        if (value != null && !value.isEmpty()) {
            expressions.addAll(Arrays.stream(value.split(";")).collect(Collectors.toList()));
        }

        for (String expression : expressions) {
            if (Util.checkIfItsMethod(expression)) {
                result += Util.replaceFunctionArguments(expression, avoid, (events ? "e" : null));
            } else if (avoid != null && expression.equals(avoid) || expression.startsWith((avoid + "."))) {
                result += expression;
            } else {
                result += "d." + expression;
            }
            if (events) {
                result += ";";
            }
        }
        return result;
    }

    static String getBetween(String text) {
        String c1 = "{{", c2 = "}}";
        try {
            return text.substring(text.indexOf(c1) + c1.length(), text.indexOf(c2));
        } catch (Exception ex) {
            return null;
        }
    }


    public static boolean hasPropsConstructor(Element element) {
        // Check if the element is a TypeElement
        if (!(element instanceof TypeElement)) {
            return false;
        }

        TypeElement typeElement = (TypeElement) element;
        List<ExecutableElement> constructors = ElementFilter.constructorsIn(typeElement.getEnclosedElements());

        // Iterate over constructors
        for (ExecutableElement constructor : constructors) {
            List<? extends VariableElement> parameters = constructor.getParameters();
            // Check if the constructor has one parameter
            if (parameters.size() == 1) {
                VariableElement parameter = parameters.get(0);
                String parameterTypeName = parameter.asType().toString();
                if (parameterTypeName.equals("com.dncomponents.client.components.core.Props")) {
                    return true;
                }
            }
        }
        return false;
    }
}