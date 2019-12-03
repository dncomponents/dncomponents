package com.dncomponents;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import org.apache.commons.text.StringEscapeUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
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

@SupportedAnnotationTypes({"com.dncomponents.UiField", "com.dncomponents.UiTemplate", "com.dncomponents.UiStyle",
        "com.dncomponents.I18n"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TemplateProcessor extends AbstractProcessor {

    private static String EXTENSION = "Binder";
    private static List<TemplateProp> templateProps = new ArrayList<>();
    private static Map<Element, Element> elementSubElement = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println(" ************************ processing annotations ************************ ");

        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(UiTemplate.class);
        Set<? extends Element> i18nAnnotated = roundEnv.getElementsAnnotatedWith(I18n.class);
        Set<? extends Element> allAnnotatedFields = roundEnv.getElementsAnnotatedWith(UiField.class);
        Set<Element> allClassesContainAnnotatedFields = new HashSet<>();
        allClassesContainAnnotatedFields.addAll(elementsAnnotatedWith);

        allAnnotatedFields.forEach(new Consumer<Element>() {
            @Override
            public void accept(Element element) {
                allClassesContainAnnotatedFields.add(element.getEnclosingElement());
            }
        });

        Set<Element> allClassesContainAnnotatedFieldsCopy = new HashSet<>(allClassesContainAnnotatedFields);

        for (Element clazzEl : allClassesContainAnnotatedFields) {
            TypeMirror superclass = ((TypeElement) clazzEl).getSuperclass();
            for (Element clazzEl2 : allClassesContainAnnotatedFieldsCopy) {
                TypeMirror superclass2 = clazzEl2.asType();
                if (((Type.ClassType) superclass).asElement() == ((Type.ClassType) superclass2).asElement()) {
                    elementSubElement.put(clazzEl, clazzEl2);
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
            Set<? extends Element> fieldsStyleOld = roundEnv.getElementsAnnotatedWith(UiStyle.class);
            Set<Element> fieldsStyle = new HashSet<>();
            for (Element element : fieldsStyleOld) {
                if (element.getEnclosingElement() == classEl) {
                    fieldsStyle.add(element);
                }
            }
            PackageElement pkg = processingEnv.getElementUtils().getPackageOf(classEl);
            try {
                generate(pkg, classEl, fields, fieldsStyle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (roundEnv.processingOver()) {
            try {
                generateTemplateService();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private void generateI18e(PackageElement pe, Element element) throws IOException {
        System.out.println(" @@@@@@@@ processing i18n @@@@@@@@@@ ");

        //create the source file
        String packageName = pe.toString();
        String annotatedClassName = ((Symbol.ClassSymbol) element).getSimpleName() + "";
        String pathAnnotatedClassName = ((Symbol.ClassSymbol) element).fullname + "";
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

    private interface HasValue {
        String getValue(Element element);
    }

    private String fieldString(Set<? extends Element> fields, HasValue hasValue, String getter) {
        ArrayList<String> fieldsList = new ArrayList<>();
        fields.forEach((Consumer<Element>) element -> {
            String fieldName = element.getSimpleName() + "";
            String value = hasValue.getValue(element);
            String fieldValue = value.isEmpty() ? fieldName : value;
            fieldsList.add("        tw." + fieldName + "=" + getter + "(\"" + fieldValue + "\");\n");
        });
        String result = "";
        for (String s : fieldsList) {
            result += s;
        }
        return result;
    }

    private String fieldString2(Set<? extends Element> fields, AtomicBoolean hasProvided) {
        ArrayList<String> fieldsList = new ArrayList<>();
        fields.forEach((Consumer<Element>) element -> {
            String fieldName = element.getSimpleName() + "";
            String value = element.getAnnotation(UiField.class).value();
            String fieldValue = value.isEmpty() ? fieldName : value;
            if (element.getAnnotation(UiField.class).provided()) {
                fieldsList.add("        DomUtil.replace(tw." + fieldName + ", " + "template.getElement" + "(\"" + fieldValue + "\"));\n");
                if (!hasProvided.get())
                    hasProvided.set(true);
            } else {
                fieldsList.add("        tw." + fieldName + "=" + "template.getElement" + "(\"" + fieldValue + "\");\n");
            }
        });
        String result = "";
        for (String s : fieldsList) {
            result += s;
        }
        return result;
    }

    private String readHtmlPair(Element element) {
        String path = ((Symbol.ClassSymbol) element).sourcefile.toUri().getPath();
        String htmlPath = path.substring(0, path.length() - 5) + ".html";
        try {
            String content = new String(Files.readAllBytes(Paths.get(htmlPath)));
            return content;
        } catch (IOException e) {
            return null;
        }
    }

    private Properties readPropertiesPair(Element element) {
        String path = ((Symbol.ClassSymbol) element).sourcefile.toUri().getPath();
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

    private void generate(PackageElement pe, Element element, Set<? extends Element> elementFields, Set<? extends Element> styleFields) throws Exception {

        //create the source file
        String packageName = pe.toString();
        String annotatedClassName = ((Symbol.ClassSymbol) element).getSimpleName() + "";
        String pathAnnotatedClassName = ((Symbol.ClassSymbol) element).fullname + "";
        System.out.println(" ---- " + pathAnnotatedClassName);
        String generatedFullClassName = element.toString() + EXTENSION;
        String generatedClassName = annotatedClassName + EXTENSION;

//        String HtmlBinderName = HtmlBinder.getSimpleName() + "";

        String templateValue = element.getAnnotation(UiTemplate.class) == null ? null : element.getAnnotation(UiTemplate.class).value();
        if (templateValue != null || (templateValue != null && !templateValue.isEmpty())) {
            templateValue = StringEscapeUtils.escapeJava(templateValue);
        }
        //read java html pair
        String htmlContentFromFile = readHtmlPair(element);
        if (htmlContentFromFile != null && (templateValue == null || templateValue.isEmpty())) {
            templateValue = StringEscapeUtils.escapeJava(htmlContentFromFile);
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
                subElementBind = "        if (b)" + binderClz + ".bind(tw,template,b);\n";
            } catch (ClassNotFoundException e) {
            }
        } else {
            importFieldsInitPackage += "import " + subElement.toString() + EXTENSION + ";\n";
            String subElementName = ((Symbol.ClassSymbol) subElement).getSimpleName() + "";
            subElementBind = "        if (b)" + subElementName + EXTENSION + ".bind(tw,template,b);\n";
        }

        templateProps.add(new TemplateProp(pathAnnotatedClassName, generatedFullClassName, generatedClassName));

        String buildWarining = warning + "\n";
        String buildPackageName = "package " + packageName + ";" + "\n";
        String buildClassName = "public class " + generatedClassName + " extends HtmlBinder<" + annotatedClassName + "> {\n\n";
        String buildConstructor = "    public " + generatedClassName + "() {\n" +
//                "       tw." + HtmlBinderName + " = new " + generatedClassName + "();\n" +
                (templateValue != null ? "       setTemplateContent(\"" + templateValue + "\");\n" : "") + "    }\n\n";
        AtomicBoolean hasProvided = new AtomicBoolean(false);
        String fieldsString = fieldString2(elementFields, hasProvided);
        if (hasProvided.get())
            importFieldsInitPackage += "import com.dncomponents.client.dom.DomUtil;\n\n";

        String fieldsStyleString = fieldString(styleFields, element2 -> element2.getAnnotation(UiStyle.class).value(), "template.getStyle");

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
                "        template.init();\n" +
                "        bind(tw, template, true);\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public void bindThis() {\n" +
                "        template.init();\n" +
                "        bind(tw, template, false);\n" +
                "    }\n" +
                "\n" +
                "    public static void bind(" + annotatedClassName + " tw, TemplateParser template, boolean b) {\n" +
                subElementBind +
                fieldsString +
                fieldsStyleString +
                "    }\n" +
                "}");
        writer.close();
    }

    private String getSuperClassPath(Element element) {
        String classPath = ((Symbol.ClassSymbol) element).getSuperclass().toString();
        String classBinderPath = classPath + EXTENSION;
        return classBinderPath;
    }

    private void generateTemplateService() throws Exception {
        final String registerString = "    public static void register() {\n";
        String name = processingEnv.getOptions().get("register");
        String generatedFullClassName = "com.dncomponents.client.components.core." + name;

        FileObject resource = null;
        InputStream inputStream = null;
        try {
            resource = processingEnv.getFiler().getResource(StandardLocation.SOURCE_OUTPUT, "com.dncomponents.client.components.core", name + ".java");
            inputStream = resource.openInputStream();
        } catch (Exception ex) {
        }
        String registers = "";
        ArrayList<String> list = new ArrayList<>();
        for (TemplateProp prop : templateProps) {
            String str = "          TemplateService." + (prop.isI18n ? "i18nBinder" : "binders") + ".put(\"" + prop.fullPathAnnotatedClass + "\", new " + prop.fullPathBinderClass + "());\n";
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
