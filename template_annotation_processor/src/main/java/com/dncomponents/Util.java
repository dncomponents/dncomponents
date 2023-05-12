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

package com.dncomponents;

/**
 * @author nikolasavic
 */
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

    public static String replaceFunctionArguments(String code) {
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
                        !isNumeric(arg))
                    arg = arg.replace(arg, "d." + arg);
                result += arg + ((i == (argArray.length - 1)) ? ")" : ",");
            }

            code = replaceStringBetweenIndexes(code, startIndex, endIndex, result);
        }
        return code;
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
}

