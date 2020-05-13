package com.nanachi.proxy.cglib;

import com.google.common.base.Joiner;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.ImmutableBean;
import net.sf.cglib.proxy.*;
import org.junit.Assert;
import org.junit.Test;

import java.awt.image.SampleModel;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Cglib provides stronger dynamic proxy ability than native java proxy it is on base of ASM which is a
 * smart framework provides bytecode(java byte code) operation.
 */
public class CglibProxyTutorialTest {

    @Test
    public void enchanerUnitTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DemoClass.class);
        // here, no matter which method is called, a fixed value will be returned.
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                System.out.println("Aspect here in front of the DemoClass's helloList.");
                // fixed value to return not matter which method is invoked.
                return "Aspect here in front of the DemoClass's helloList.";
            }
        });

        Map<String, String> map = new HashMap<>();
        map.put("kokia", "basic info");
        DemoClass demoClassProxy = (DemoClass) enhancer.create();
        String ret = demoClassProxy.helloList(map);
       String retString = demoClassProxy.sayHello();
        System.out.println(ret);
        System.out.println(retString);
    }

    @Test
    public void testInvocationHandler() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DemoClass.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                // here we can pick up a method which we like to intercept its return value
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return "hello cglib";
                } else {
                    System.out.println("Method Return Type not String is invoked, do nothing here");
                    throw new RuntimeException("Method not match!");
                }
            }
        });

        DemoClass demoClassProxy = (DemoClass) enhancer.create();
        String ret1 = demoClassProxy.helloList(null);
        String ret2 = demoClassProxy.sayHello();
        System.out.println(ret1);
        System.out.println(ret2);
    }

    /**
     * In this unit test, we passing the class + class[0] as parameters to CallbackHelper
     * then the getCallBack's parameter becomes the Method
     * in this way we can check the method directly.
     */
    @Test
    public void specificalMethodIntercept() throws Exception {
        Enhancer enhancer = new Enhancer();
        CallbackHelper callbackHelper = new CallbackHelper(DemoClass.class, new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                // write specifical method filter logic only intercept the method statisfy the condition
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return new FixedValue() {
                        @Override
                        public Object loadObject() throws Exception {
                            return "FixValue: Hello cglib";
                        }
                    };
                } else {
                    return NoOp.INSTANCE;
                }
            }
        };
        enhancer.setSuperclass(DemoClass.class);
        enhancer.setCallbackFilter(callbackHelper);
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        DemoClass proxy = (DemoClass) enhancer.create();
        String ret1 = proxy.sayHello();
        String ret2 = proxy.helloList(null);
        String ret3 = proxy.toString();

        System.out.println("ret1=" + ret1 + "; ret2=" + ret2 + "; ret3=" + ret3);
    }

    @Test
    public void immutableBeanUnitTest() throws Exception {
        DemoBean mutableBean = new DemoBean();
        mutableBean.setValue("BeanNameValue");

        // create an immutable bean instance
        DemoBean immutableBean = (DemoBean) ImmutableBean.create(mutableBean);
        System.out.println(immutableBean.getValue());

        // value can be set cause it is mutable
        mutableBean.setValue("UpdateName");
        System.out.println(mutableBean.getValue());

        // value can not be set cause it is immutable an exception will be thrown here
        // immutableBean.setValue("UpdateNewValue");
    }

    // BeanGenerator is adopt to generate instance of java bean
    @Test
    public void testBeanGenerator() throws Exception {
        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.addProperty("value", Integer.class);
        Object generatedBeanObj = beanGenerator.create();
        Assert.assertNotNull(generatedBeanObj);
        Method setter = generatedBeanObj.getClass().getMethod("setValue", Integer.class);
        setter.invoke(generatedBeanObj, 1000);

        Method getter = generatedBeanObj.getClass().getMethod("getValue");
        Integer ret = (Integer) getter.invoke(generatedBeanObj);
        System.out.println(ret);
    }
}


class DemoBean {
    private String value;

    public DemoBean() {}

    public DemoBean(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

class DemoClass {
    public String sayHello() {
        return "Hello!";
    }

    public String helloList(Map<String, String> userInfoMap) {
        return   Joiner.on(", ")
                .skipNulls()
                .join(
                        userInfoMap
                .keySet()
                .stream()
                .map(name -> "Say hello to " + name)
                .collect(toList())
                );
    }
}