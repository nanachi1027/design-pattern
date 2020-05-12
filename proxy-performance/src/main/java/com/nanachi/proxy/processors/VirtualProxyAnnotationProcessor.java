package com.nanachi.proxy.processors;

import com.google.auto.service.AutoService;
import com.nanachi.proxy.annotations.IsDelegator;
import com.nanachi.proxy.annotations.VirtualProxy;
import com.squareup.javapoet.*;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class VirtualProxyAnnotationProcessor extends BasicAnnotationProcessor<VirtualProxy> {

    public static final String FACTORY = "Factory";
    public static final String FACTORY_CREATE_INSTANCE = "createInstance";
    public static final String STRATEGY_FACTORY = "StrategyFactory";
    public static final String STRATEGY_REAL_SUBJECT = "realSubject";

    /**
     * Add statements in method body;
     * In JavaPoet:
     * CodeBlock = method body;
     * Statement = code in a line .
     */
    @Override
    protected CodeBlock defineMethodImplementation(ExecutableElement methodElement, String methodName2Delegate) {
        return null;
    }

    /**
     * Add which annotation class gonna to implement.
     */
    @Override
    protected Class<VirtualProxy> responsibleFor() {
        return VirtualProxy.class;
    }

    /**
     * Add extra FieldSpec(s) && Annotation(s) to specifical TypeSpec
     */
    @Override
    protected void addClassLevelSpec(TypeElement typeElement, RoundEnvironment roundEnv) {
        final TypeName interface2Implement = TypeName.get(typeElement.asType());
        final TypeSpec.Builder typeSpecBuilder = createTypeSpecBuilder(typeElement, interface2Implement);

        // add annotation
        typeSpecBuilder.addAnnotation(IsDelegator.class);

        // add field spec
        final FieldSpec fieldSpec = defineFieldSpec(typeElement);
        typeSpecBuilder.addField(fieldSpec);
    }

    /**
     * Create an instance of class described in TypeElement.
     * It gonna be like this:
     *
     * class xxxAnnotationProcessor {
     *
     *     private (ClassName) (delegator);
     *
     *     ...
     * }
     */
     public FieldSpec defineFieldSpec(TypeElement typeElement) {
        final ClassName className = ClassName.get(getPkgName(typeElement), getClassSimpleName(typeElement));
        return FieldSpec
                .builder(className, "delegator")
                .addModifiers(Modifier.PRIVATE)
                .build();
    }
}
