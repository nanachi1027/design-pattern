package com.nanachi.proxy.processors;

import com.google.auto.service.AutoService;
import com.nanachi.proxy.annotations.IsVirtualProxy;
import com.nanachi.proxy.annotations.VirtualProxyNotThreadSave;
import com.squareup.javapoet.*;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class VirtualProxyNotThreadSafeAnnotationProcessor extends BasicAnnotationProcessor<VirtualProxyNotThreadSave> {
    @Override
    protected CodeBlock defineMethodImplementation(ExecutableElement methodElement, String methodName2Delegate) {
        final String delegateStatement = getReturnStatementInString(methodElement, methodName2Delegate);

        ClassName implClassName = ClassName.get(
                getPkgName((TypeElement) methodElement.getEnclosedElements()),
                getClassSimpleName((TypeElement) methodElement.getEnclosingElement()) + "Impl");
        return CodeBlock
                .builder()
                .beginControlFlow("if (delegator == null)")
                    .addStatement("delegator = new $T()", implClassName)
                .endControlFlow()
                .addStatement(delegateStatement)
                .build();
    }

    @Override
    protected Class<VirtualProxyNotThreadSave> responsibleFor() {
        return VirtualProxyNotThreadSave.class;
    }

    @Override
    protected void addClassLevelSpec(TypeElement typeElement, RoundEnvironment roundEnv) {
        final TypeName interfaceToImpl = TypeName.get(typeElement.asType());
        final TypeSpec.Builder typeSpecBuilder = createTypeSpecBuilder(typeElement, interfaceToImpl);

        typeSpecBuilder.addAnnotation(IsVirtualProxy.class);

        final FieldSpec delegatorFieldSpec = defineFieldSpec(typeElement);
        typeSpecBuilder.addField(delegatorFieldSpec);
    }
}
