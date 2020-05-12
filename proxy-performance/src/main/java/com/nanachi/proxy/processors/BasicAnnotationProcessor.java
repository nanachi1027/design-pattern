package com.nanachi.proxy.processors;

import com.google.common.base.Joiner;
import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.*;

import static java.util.stream.Collectors.toList;

public abstract class BasicAnnotationProcessor<T extends Annotation> extends AbstractProcessor {

    protected Filer filer;
    protected Messager messager;
    protected Elements elementUtils;
    protected Types typeUtils;
    private TypeSpec.Builder typeSpecBuilderForTargetClass;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv
                // get sub-class's annotation Elements
                .getElementsAnnotatedWith(responsibleFor())
                .stream()
                // only interface Elements are allowed to continue
                .filter(elem -> elem.getKind() == ElementKind.INTERFACE)
                // Element -> TypeElement
                .map(elem -> (TypeElement) elem)
                .forEach(typeElem -> {
                    // Element -> TypeName
                    final TypeName interface2Impl = TypeName.get(((Element) typeElem).asType());
                    // Element & TypeName -> TypeSpec's Builder instance
                    final TypeSpec.Builder forTargetClass = createTypeSpecBuilder(typeElem, interface2Impl);

                    // entry for sub-class adding extra spec instances to TypeElement by implement this method
                    addClassLevelSpec(typeElem, roundEnv);

                    typeElem
                            // get field, method, constructors directly declared inside this TypeElement
                            .getEnclosedElements()
                            .stream()
                            // only methods are allowed to continue following lambda
                            .filter(e -> e.getKind() == ElementKind.METHOD)
                            // Element -> ExecutableElement(class's method, constructor or static initializer or
                            //                              interface's contains annotation type elements)
                            .map(methodElement -> (ExecutableElement) methodElement)
                            // only public methods are allowed to continue
                            .filter(methodElement -> methodElement.getModifiers().contains(Modifier.PUBLIC))
                            // extract method name to construct
                            .forEach(methodElement -> {
                                final String methodName2Delegate = getMethodSimpleName(methodElement);
                                final CodeBlock codeBlock = defineMethodImplementation(methodElement, methodName2Delegate);
                                final MethodSpec delegateMethodSpec = defineMethod(methodElement,
                                        methodName2Delegate, codeBlock);
                                forTargetClass.addMethod(delegateMethodSpec);
                            });
                    // dump designed class to specific packages .java source file
                    writeDefinedClass(getPkgName(typeElem), forTargetClass);
                });
        return true;
    }

    //
    // --- override methods in AbstractProcessor
    //
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    protected String getMethodSimpleName(ExecutableElement methodElement) {
        return methodElement.getSimpleName().toString();
    }

    protected String getPkgName(final TypeElement typeElement) {
        return elementUtils.getPackageOf(typeElement).getQualifiedName().toString();

    }

    /**
     * This method return a set of annotation types.
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypeSet = new LinkedHashSet<>();
        annotationTypeSet.add(responsibleFor().getCanonicalName());
        return annotationTypeSet;
    }

    /**
     * This method gonna return which JDK version gonna support the annotation you define.
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }
    //
    // --- override methods in AbstractProcessor
    //

    // -- declares abstract methods
    protected abstract CodeBlock defineMethodImplementation(final ExecutableElement methodElement,
                                                            final String methodName2Delegate);

    protected abstract Class<T> responsibleFor();

    protected abstract void addClassLevelSpec(final TypeElement typeElement, final RoundEnvironment roundEnv);
    // -- declares abstract methods


    // -- javapoet util methods
    /**
     * Get an instance of MethodSpec which describe how 'delegator' this method's logic(method body).
     * This MethodSpec should be added to sub-classes.
     */
    protected MethodSpec defineMethod(final ExecutableElement methodElement,
                                      String methodName2Delegate, CodeBlock codeBlock) {
        // abstract modifier should be remove from sub-class
        final Set<Modifier> modifiers = new HashSet<>(methodElement.getModifiers());
        modifiers.remove(Modifier.ABSTRACT);

        return MethodSpec
                // define method's name
                .methodBuilder(methodName2Delegate)
                // add method's modifiers in Modifier Set
                // Modifier (key words like static, public, final, abstract, volatile, transient)
                .addModifiers(modifiers)
                // add method's parameters in ParameterSpec List
                .addParameters(defineParamsForMethod(methodElement))
                // add method's return as TypeName
                .returns(defineReturnTypeForMethod(methodElement))
                // add method's code body
                .addCode(codeBlock)
                .build();
    }

    /**
     * Return method parameters as ParameterSpec instances in order in a List
     */
    public List<ParameterSpec> defineParamsForMethod(final ExecutableElement methodElement) {
        return methodElement
                .getParameters()
                .stream()
                .map(parameter -> {
                    final Name simpleName = parameter.getSimpleName();
                    final TypeMirror typeMirror = parameter.asType();
                    TypeName typeName = TypeName.get(typeMirror);
                    return ParameterSpec.builder(typeName, simpleName.toString(), Modifier.FINAL).build();
                })
                .collect(toList());
    }



    public TypeName defineReturnTypeForMethod(final ExecutableElement methodElement) {
        return TypeName.get(methodElement.getReturnType());
    }

    public FieldSpec defineFieldSpec(final TypeElement typeElement) {
        return null;
    }

    /**
     * Return TypeSpec.Builder instance that helps build instance of TypeSpec.
     *
     * @param typeElement         a class or an interface program element.
     * @param interface2Implement
     * @return
     */
    public TypeSpec.Builder createTypeSpecBuilder(final TypeElement typeElement,
                                                     final TypeName interface2Implement) {
        if (typeSpecBuilderForTargetClass == null) {
            typeSpecBuilderForTargetClass = TypeSpec
                    .classBuilder(getClassSimpleName(typeElement))
                    .addSuperinterface(interface2Implement)
                    .addModifiers(Modifier.PUBLIC);
        }
        return typeSpecBuilderForTargetClass;
    }

    /**
     * Extract class name with package name.
     *
     * @param typeElement
     */
    protected String getClassSimpleName(TypeElement typeElement) {
        String typeElementPkgName = getPkgName(typeElement);
        String typeElementClassName = typeElement.getSimpleName().toString();
        String className = responsibleFor().getSimpleName();
        // ClassName.get( package name, class simple name)
        return ClassName.get(typeElementPkgName, typeElementClassName + className).simpleName();
    }

    public String getReturnStatementInString(final ExecutableElement methodElement,
                                                final String methodName2Delegate) {
        return "return delegator." + getMethodSignatureInString(methodElement, methodName2Delegate);
    }

    /**
     * Return method param signature in String.
     * Like methodName(Type1 arg1, Type2 arg2, ...)
     */
    public String getMethodSignatureInString(final ExecutableElement methodElement, final String methodName2Delegate) {
        return methodName2Delegate + "(" +
                Joiner.on(", ")
                        .skipNulls()
                        .join(defineParamsForMethod(methodElement)
                                .stream()
                                // here we traverse param and get its name
                                .map(param -> param.name)
                                .collect(toList())) +
                ")";
    }

    /**
     * Generate class file to specific package.
     */
    public void writeDefinedClass(String pkgName, TypeSpec.Builder typeSpecBuilder) {
        final TypeSpec typeSpec = typeSpecBuilder.build();
        final JavaFile javaFile = JavaFile.builder(pkgName, typeSpec).skipJavaLangImports(true).build();
        final String className = javaFile.packageName + "." + javaFile.typeSpec.name;
        JavaFileObject jfo;
        Writer writer = null;
        try {
            jfo = filer.createSourceFile(className);
            writer = jfo.openWriter();
            javaFile.writeTo(writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // -- javapoet util methods
}
