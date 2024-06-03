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
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Util {

    public static String createJavaCode(String value, String avoid, boolean events) {
        String result = "";
        List<String> expressions = new ArrayList<>();
        if (value != null && !value.isEmpty()) {
            expressions.addAll(Arrays.stream(value.split(";")).collect(Collectors.toList()));
        }
        for (String expression : expressions) {
            result += processJavaCode(expression, avoid, (events ? "e" : null));
            if (events) {
                result += ";";
            }
        }
        return result;
    }

    public static String processJavaCode(String code, String avoid1, String avoid2) {
        // Patterns to identify identifiers and method calls
        Pattern identifierPattern = Pattern.compile("\\b\\w+\\b");
        Pattern methodPattern = Pattern.compile("\\b\\w+\\s*\\(");

        // Tokenize the code to process each part separately
        StringTokenizer tokenizer = new StringTokenizer(code, " \t\n\r\f(){}[];,.+-/*&|<>=!?", true);

        StringBuilder result = new StringBuilder();
        boolean shouldPrefixNextToken = true;

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            // Check if the token is an identifier
            Matcher identifierMatcher = identifierPattern.matcher(token);
            if (identifierMatcher.matches() && !isNumber(token) && !isQuotedString(token)) {
                // Check if the identifier should be avoided
                if ((avoid1 == null || !token.equals(avoid1)) && (avoid2 == null || !token.equals(avoid2))) {
                    if (shouldPrefixNextToken) {
                        result.append("d.").append(token);
                    } else {
                        result.append(token);
                    }
                } else {
                    // If it's an avoid token, don't prefix and reset the flag
                    result.append(token);
                    shouldPrefixNextToken = false;
                }
            } else if (token.equals(".")) {
                // Dot indicates part of a chain, don't prefix the next identifier
                result.append(token);
                shouldPrefixNextToken = false;
            } else {
                // Append non-identifier tokens as is
                result.append(token);
                shouldPrefixNextToken = true;
            }
        }

        return result.toString();
    }

    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isQuotedString(String token) {
        return token.startsWith("\"") && token.endsWith("\"");
    }

    public static boolean isFunctionCall(String expression) {
        String functionPattern = "^[a-zA-Z_][a-zA-Z0-9_]*\\s*\\(.*\\)$";
        return expression.matches(functionPattern);
    }

    public static boolean hasPropsConstructor(Element element) {
        if (!(element instanceof TypeElement)) {
            return false;
        }

        TypeElement typeElement = (TypeElement) element;
        List<ExecutableElement> constructors = ElementFilter.constructorsIn(typeElement.getEnclosedElements());

        for (ExecutableElement constructor : constructors) {
            List<? extends VariableElement> parameters = constructor.getParameters();
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