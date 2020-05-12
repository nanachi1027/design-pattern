package com.nanachi.proxy.processors;

import com.google.auto.service.AutoService;
import com.nanachi.proxy.annotations.Delegator;
import com.nanachi.proxy.annotations.IsDelegator;
import com.squareup.javapoet.*;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.List;

@AutoService(Process.class)
public class DelegatorAnnotationProcessor extends BasicAnnotationProcessor<Delegator> {
    @Override
    public Class<Delegator> responsibleFor() {
        return Delegator.class;
    }

    @Override
    protected void addClassLevelSpec(TypeElement typeElement, RoundEnvironment roundEnv) {
        final TypeName interfaceToImplement = TypeName.get(typeElement.asType());
        final TypeSpec.Builder specBuilderClass = createTypeSpecBuilder(typeElement, interfaceToImplement);

        // add annotation
        specBuilderClass.addAnnotation(IsDelegator.class);

        // add FieldSpec
        final FieldSpec fieldSpec = defineSpecField(typeElement);
        specBuilderClass.addField(fieldSpec);
    }

    private FieldSpec defineSpecField(TypeElement typeElement) {
        final ClassName clsName = ClassName.get(getPkgName(typeElement),
                getClassSimpleName(typeElement));
        return FieldSpec
                .builder(clsName, "clsInstance")
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

    @Override
    protected CodeBlock defineMethodImplementation(ExecutableElement methodElement, String methodName2Delegate) {
        String statementInString = getReturnStatementInString(methodElement, methodName2Delegate);
        return CodeBlock
                .builder()
                .addStatement(statementInString)
                .build();
    }
}
