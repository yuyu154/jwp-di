package nextstep.di.factory.domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.List;

public class BeanDefinition {
    private Class<?> beanType;
    private Constructor<?> constructor;
    private Parameter[] parameters;

    public BeanDefinition(Class<?> beanType, Constructor<?> constructor, Parameter[] parameters) {
        this.beanType = beanType;
        this.constructor = constructor;
        this.parameters = parameters;
    }

    public Class<?> getBeanType() {
        return this.beanType;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    public Parameter[] getParameters() {
        return parameters;
    }
}
