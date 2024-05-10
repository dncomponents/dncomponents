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

import com.google.auto.service.AutoService;
import org.apache.commons.text.StringEscapeUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SupportedAnnotationTypes({"com.dncomponents.UiField", "com.dncomponents.Component",
        "com.dncomponents.Template", "com.dncomponents.I18n"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor.class)
public class TemplateProcessor extends AbstractProcessor {

    private static String EXTENSION = "Binder";
    private static List<TemplateProp> templateProps = new ArrayList<>();
    private static List<String> parsersClassNames = new ArrayList<>();
    private static List<String> allCss = new ArrayList<>();
    private static Map<Element, Element> elementSubElement = new HashMap<>();
    private static boolean firstRound = true;
    private static String APP_TEMPLATE_NAME = "AppTemplates";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println(" ************************ processing annotations ************************ ");

        Set<? extends Element> uiComponents = roundEnv.getElementsAnnotatedWith(Component.class);
        Set<? extends Element> templateComponents = roundEnv.getElementsAnnotatedWith(Template.class);
        Set<? extends Element> i18nAnnotated = roundEnv.getElementsAnnotatedWith(I18n.class);
        Set<? extends Element> allAnnotatedFields = roundEnv.getElementsAnnotatedWith(UiField.class);


        Set<Element> allClassesContainAnnotatedFields = new HashSet<>();
        allClassesContainAnnotatedFields.addAll(uiComponents);
        allClassesContainAnnotatedFields.addAll(templateComponents);

        allAnnotatedFields.forEach((Consumer<Element>) element ->
                allClassesContainAnnotatedFields.add(element.getEnclosingElement()));

        Set<Element> allClassesContainAnnotatedFieldsCopy = new HashSet<>(allClassesContainAnnotatedFields);

        for (Element clazzEl : allClassesContainAnnotatedFields) {
            TypeMirror superclass = ((TypeElement) clazzEl).getSuperclass();
            for (Element clazzEl2 : allClassesContainAnnotatedFieldsCopy) {
                TypeMirror superclass2 = clazzEl2.asType();

                if (superclass instanceof DeclaredType && superclass2 instanceof DeclaredType) {
                    Element superClassElement1 = ((DeclaredType) superclass).asElement();
                    Element superClassElement2 = ((DeclaredType) superclass2).asElement();

                    if (superClassElement1.equals(superClassElement2)) {
                        elementSubElement.put(clazzEl, clazzEl2);
                    }
                }
            }
        }

        for (Element classEl : i18nAnnotated) {
            PackageElement pkg = processingEnv.getElementUtils().getPackageOf(classEl);
            try {
                generateI18e(pkg, classEl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Element classEl : allClassesContainAnnotatedFields) {

            Set<? extends Element> fieldsOld = roundEnv.getElementsAnnotatedWith(UiField.class);
            Set<Element> fields = new HashSet<>();
            for (Element element : fieldsOld) {
                if (element.getEnclosingElement() == classEl) {
                    fields.add(element);
                }
            }

            PackageElement pkg = processingEnv.getElementUtils().getPackageOf(classEl);
            try {

                generate(pkg, classEl, fields, classEl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (firstRound) {
            try {
                generateTemplateService();
                if (!allCss.isEmpty()) {
                    generateCssFile(allCss.stream().collect(Collectors.joining("\n")),
                            processingEnv.getOptions().get("cssDir") + "/app.css");
                }
                System.out.println("done");
                firstRound = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private void generateParserClass(Element element, Element classEl, String tag) {
        String tagName = tag.isEmpty() ? classEl.getSimpleName() + "" : tag;
        if (Util.hasPropsConstructor(element)) {
            parsersClassNames.add("new GeneralComponentParser(\"" + tagName + "\", f -> new " + classEl + "((Props) f))");
        } else {
            parsersClassNames.add("new GeneralComponentParser(\"" + tagName + "\", () -> new " + classEl + "())");
        }
    }

    private void generateI18e(PackageElement pe, Element element) throws IOException {
        System.out.println(" @@@@@@@@ processing i18n @@@@@@@@@@ ");

        //create the source file
        String packageName = pe.toString();
        String annotatedClassName = element.getSimpleName() + "";
        String pathAnnotatedClassName = element.toString();
        System.out.println(" ---- " + pathAnnotatedClassName);
        String generatedFullClassName = element.toString() + EXTENSION;
        String generatedClassName = annotatedClassName + EXTENSION;

        Properties properties;
        //read java property pair
        final StringBuffer sb = new StringBuffer();
        properties = readPropertiesPair(element);
        if (properties != null) {
            properties.forEach(new BiConsumer<Object, Object>() {
                @Override
                public void accept(Object o, Object o2) {
                    sb.append("\t\ti18n.putValue(\"" + o + "\",\"" + o2 + "\");\n");

                    System.out.println(o);
                    System.out.println(o2);
                }
            });
        }


        ///%%%
        String warning = "//Generated code do not edit!";
        String importFieldsInitPackage =
                "import com.dncomponents.client.components.core.I18nBinder;\n\n";

        String buildWarining = warning + "\n";
        String buildPackageName = "package " + packageName + ";" + "\n";
        String buildClassName = "public class " + generatedClassName + " extends I18nBinder<" + annotatedClassName + "> {\n\n";
        String buildConstructor = "    public " + generatedClassName + "() {\n\t}\n\n";
        String content = sb.toString();
        JavaFileObject jfo = processingEnv.getFiler().createSourceFile(generatedFullClassName, pe);
        Writer writer = jfo.openWriter();
        writer.write("" +
                     buildWarining +
                     buildPackageName +
                     importFieldsInitPackage +
                     buildClassName +
                     buildConstructor +
                     "    @Override\n" +
                     "    public void bind() {\n" +
                     content +
                     "    }\n\n" +
                     "}");
        writer.close();
        ///%%%
        templateProps.add(new TemplateProp(pathAnnotatedClassName, generatedFullClassName, generatedClassName, true));

    }

    private String fieldString(Set<? extends Element> fields, AtomicBoolean hasProvided) {
        ArrayList<String> fieldsList = new ArrayList<>();
        fields.forEach((Consumer<Element>) element -> {
            String fieldName = element.getSimpleName() + "";
            String value = element.getAnnotation(UiField.class).value();
            String fieldValue = value.isEmpty() ? fieldName : value;
            if (element.getAnnotation(UiField.class).provided()) {
                fieldsList.add("    DomUtil.replace(d." + fieldName + ", " + "template.getElement" + "(\"" + fieldValue + "\"));\n");
                if (!hasProvided.get())
                    hasProvided.set(true);
            } else {
                fieldsList.add("    d." + fieldName + "=" + "template.getElement" + "(\"" + fieldValue + "\");\n");
            }
        });
        String result = "";
        for (String s : fieldsList) {
            result += s;
        }
        return result;
    }

    private String getFilePath(Element element) {
        return processingEnv.getOptions().get("src") + "/" + element.toString().replace('.', '/') + ".java";
    }


    private String readHtmlPair(Element element) {
        String path = getFilePath(element);

        String htmlPath = path.substring(0, path.length() - 5) + ".html";
        try {
            String content = new String(Files.readAllBytes(Paths.get(htmlPath)));
            return content;
        } catch (IOException e) {
            return null;
        }
    }

    private Properties readPropertiesPair(Element element) {
        String path = getFilePath(element);
        String propPath = path.substring(0, path.length() - 5) + ".properties";
        try {
            Properties prop = new Properties();
            InputStream input = new FileInputStream(propPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(input, "UTF-8"));

            prop.load(br);
            return prop;
        } catch (IOException e) {
            return null;
        }
    }

    private String getStatesCall(Set<String> updateSet) {
        String result = updateSet.stream().collect(Collectors.joining());
        if (!result.isEmpty()) {
            result = "\n      void setStates(){\n" +
                     result +
                     "      }\n";
        }
        return result;
    }

    private void generate(PackageElement pe, Element element,
                          Set<? extends Element> elementFields, Element classEl) throws Exception {

        //create the source file
        String packageName = pe.toString();
        String annotatedClassName = element.getSimpleName() + "";
        String pathAnnotatedClassName = element.toString();
        System.out.println(" ---- " + pathAnnotatedClassName);
        String generatedFullClassName = element.toString() + EXTENSION;
        String generatedClassName = annotatedClassName + EXTENSION;

        String templateValue = readHtmlPair(element);
        String cssValue = null;
        Component annotationComponent = element.getAnnotation(Component.class);
        if (annotationComponent != null) {
            if (templateValue == null || templateValue.isEmpty())
                templateValue = annotationComponent.template();
            generateParserClass(element, classEl, annotationComponent.tag());
            if (!annotationComponent.css().isEmpty()) {
                allCss.add("/* " + element.getSimpleName() + " */\n");
                allCss.add(annotationComponent.css());
                cssValue = annotationComponent.css();
            }
        }
        if (element.getAnnotation(Template.class) != null) {
            if (templateValue == null || templateValue.isEmpty())
                templateValue = element.getAnnotation(Template.class).value();
        }
        Map<String, String> map = allFields(element);

        Set<String> updateSet = new HashSet<>();

        final LoopProcessing loopProcessing = new LoopProcessing(templateValue, classEl, map, updateSet);
        final EventsProcessing eventsProcessing = new EventsProcessing(map, updateSet);
        final IfsProcessing ifsProcessing = new IfsProcessing(templateValue, updateSet);
        final String generatedEventsCode = eventsProcessing.parse(templateValue);
        final ValuesProcessing valuesProcessing = new ValuesProcessing(templateValue, updateSet);

        final String generatedStatesCode = getStatesCall(updateSet);

        if (templateValue != null || (templateValue != null && !templateValue.isEmpty())) {
            templateValue = StringEscapeUtils.escapeJava(templateValue);
        }

        String warning = "//Generated code do not edit!";
        String importFieldsInitPackage =
                "import com.dncomponents.client.components.core.HtmlBinder;\n" +
                "import com.dncomponents.client.components.core.TemplateParser;\n";

        Element subElement = elementSubElement.get(element);
        String subElementBind = "";
        if (subElement == null) {
            String binderClz = "";
            try {
                binderClz = getSuperClassPath(element);
                Class.forName(binderClz);
                subElementBind = "        if (b)" + binderClz + ".bind(d,template,b);\n";
            } catch (ClassNotFoundException e) {
            }
        } else {
            importFieldsInitPackage += "import " + subElement + EXTENSION + ";\n";
            String subElementName = subElement.getSimpleName() + "";
            subElementBind = "        if (b)" + subElementName + EXTENSION + ".bind(d,template,b);\n";
        }

        templateProps.add(new TemplateProp(pathAnnotatedClassName, generatedFullClassName, generatedClassName));

        String buildWarining = warning + "\n";
        String buildPackageName = "package " + packageName + ";" + "\n";
        String buildClassName = "public class " + generatedClassName + " extends HtmlBinder<" + annotatedClassName + "> {\n\n";
        String cssStatic = (cssValue != null ? "static { \n allCss.add(\"/* " + annotatedClassName + " */\"); \n allCss.add(\"" + StringEscapeUtils.escapeJava(cssValue) + "\");\n}\n\n" : "");
        String buildConstructor = "    public " + generatedClassName + "() {\n" +
                                  (templateValue != null ? "       setTemplateContent(\"" + templateValue + "\");\n" : "") +
                                  "    }\n\n";
        AtomicBoolean hasProvided = new AtomicBoolean(false);
        String fieldsString = fieldString(elementFields, hasProvided);
        if (hasProvided.get())
            importFieldsInitPackage += "import com.dncomponents.client.dom.DomUtil;\n\n";

        importFieldsInitPackage += valuesProcessing.getImports();
        importFieldsInitPackage += eventsProcessing.getImports();
        importFieldsInitPackage += loopProcessing.getImports();

        JavaFileObject jfo = processingEnv.getFiler().createSourceFile(generatedFullClassName, pe);
        Writer writer = jfo.openWriter();
        writer.write("" +
                     buildWarining +
                     buildPackageName +
                     importFieldsInitPackage +
                     buildClassName +
                     cssStatic +
                     buildConstructor +
                     "    @Override\n" +
                     "    public void bind() {\n" +
                     (!generatedStatesCode.isEmpty() ? "        setStates();\n" : "") +
                     "        template.init();\n" +
                     "        bind(d, template, true);\n" +
                     (!generatedEventsCode.isEmpty() ? "        bindEvents();\n" : "") +
                     "        " +
                     "}\n" +
                     "\n" +
                     "    @Override\n" +
                     "    public void bindThis() {\n" +
                     (!generatedStatesCode.isEmpty() ? "        setStates();\n" : "") +
                     "        template.init();\n" +
                     "        bind(d, template, false);\n" +
                     (!generatedEventsCode.isEmpty() ? "        bindEvents();\n" : "") +
                     "        " +
                     "}\n" +
                     "    \n" +
                     generatedEventsCode +
                     generatedStatesCode +
                     "\n" +
                     "    public static void bind(" + annotatedClassName + " d, TemplateParser template, boolean b) {\n" +
                     "    " +
                     subElementBind +
                     fieldsString +
                     "    \n    }\n" +
                     "}");
        writer.close();
    }

    private Map<String, String> allFields(Element element) {
        Map<String, String> map = new HashMap<>();
        TypeElement annotatedClass = (TypeElement) element;
        for (Element enclosedElement : annotatedClass.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                VariableElement field = (VariableElement) enclosedElement;
                String fieldName = field.getSimpleName().toString();
                TypeMirror fieldType = field.asType();
                if (isCollectionType(fieldType)) {
                    map.put(fieldName, "collection");
                }
                if (isBooleanType(fieldType)) {
                    map.put(fieldName, "boolean");
                }

            }
        }
        return map;
    }

    private boolean isCollectionType(TypeMirror type) {
        Types types = processingEnv.getTypeUtils();
        TypeMirror collectionType = types.erasure(processingEnv.getElementUtils().getTypeElement(Collection.class.getName()).asType());
        return types.isAssignable(type, collectionType);
    }

    private boolean isBooleanType(TypeMirror type) {
        return type.toString().equals(Boolean.class.getCanonicalName()) ||
               type.toString().equals(boolean.class.getCanonicalName());
    }

    private String getSuperClassPath(Element element) {
        String classBinderPath = "";
        if (element instanceof TypeElement) {
            TypeElement typeElement = (TypeElement) element;
            TypeMirror superClass = typeElement.getSuperclass();
            if (superClass instanceof DeclaredType) {
                classBinderPath = superClass.toString() + EXTENSION;
            }
        }
        return classBinderPath;
    }

    private void generateCssFile(String cssContent, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the CSS content to the file
            cssContent = "/* Generated css file */\n\n\n" + cssContent;
            writer.write(cssContent);
            System.out.println("CSS file generated successfully at: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing CSS file: " + e.getMessage());
        }
    }

    private void generateTemplateService() throws Exception {
        final String registerString = "    public static void register() {\n";
        String name = processingEnv.getOptions().get("register");
        if (name == null)
            name = APP_TEMPLATE_NAME;
        String generatedFullClassName = "com.dncomponents.client.components.core." + name;

        FileObject resource = null;
        InputStream inputStream = null;
        try {
            resource = processingEnv.getFiler().getResource(StandardLocation.SOURCE_OUTPUT, "com.dncomponents.client.components.core", name + ".java");
            inputStream = resource.openInputStream();
        } catch (Exception ex) {
        }

        String allParsersClassName = "";
        for (String parsersClassName : parsersClassNames) {
            allParsersClassName += "          HtmlParserService.registerComponent(" + parsersClassName + ");\n";
        }

        String registers = "";
        ArrayList<String> list = new ArrayList<>();
        for (TemplateProp prop : templateProps) {
            String str = "          TemplateService." + (prop.isI18n ? "i18nBinder" : "binders") +
                         ".put(\"" + prop.fullPathAnnotatedClass + "\", ()->new " + prop.fullPathBinderClass + "());\n";
            list.add(str);
            registers += str;
        }

        if (resource != null && inputStream != null) {
            String fileContent = readStream(inputStream);
            List<String> distinctList = new ArrayList<>();
            for (String s : list) {
                if (!fileContent.contains(s)) {
                    distinctList.add(s);
                }
            }
            if (!distinctList.isEmpty()) {
                StringBuffer bufferFileContent = new StringBuffer(fileContent);
                int index = bufferFileContent.lastIndexOf(registerString);
                StringBuffer insertedContent = bufferFileContent.insert(index + registerString.length(), distinctList.stream().collect(Collectors.joining("\n")));
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(generatedFullClassName);
                Writer writer = jfo.openWriter();
                writer.write(insertedContent.toString());
                writer.close();
            }
            return;
        }

        JavaFileObject jfo = processingEnv.getFiler().createSourceFile(generatedFullClassName);
        Writer writer = jfo.openWriter();
        writer.write("" +
                     "package com.dncomponents.client.components.core;\n" +
                     "import com.dncomponents.client.components.core.HtmlBinder;\n" +
                     "import java.util.HashMap;\n" +
                     "import java.util.Map;\n" +
                     "\npublic class " + name + " {\n" +
                     "\n" +
                     "    public static void register() {\n" +
                     registers +
                     allParsersClassName + "\n" +
                     "    }\n" +
                     "}\n");
        writer.close();
    }

    private static String readStream(InputStream inputStream) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while (result != -1) {
            buf.write((byte) result);
            result = bis.read();
        }
        return buf.toString("UTF-8");
    }

    private class TemplateProp {
        public TemplateProp(String fullPathAnnotatedClass, String fullPathBinderClass, String simpleNameBinderClass) {
            this.fullPathAnnotatedClass = fullPathAnnotatedClass;
            this.fullPathBinderClass = fullPathBinderClass;
            this.simpleNameBinderClass = simpleNameBinderClass;
        }

        boolean isI18n;
        String fullPathAnnotatedClass;
        String fullPathBinderClass;
        String simpleNameBinderClass;

        public TemplateProp(String fullPathAnnotatedClass, String fullPathBinderClass, String simpleNameBinderClass, boolean b) {
            this(fullPathAnnotatedClass, fullPathBinderClass, simpleNameBinderClass);
            isI18n = b;
        }
    }

}
