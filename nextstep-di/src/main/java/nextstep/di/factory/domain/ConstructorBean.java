package nextstep.di.factory.domain;

import nextstep.di.factory.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.List;

//@TODO AnnotationBean
public class ConstructorBean implements BeanDefinition {
    private static final Logger logger = LoggerFactory.getLogger(ConstructorBean.class);

    private final Constructor<?> constructor;
    private final List<BeanDefinition> parameters;
    private Class<?> clazz;

    public ConstructorBean(Class<?> clazz, Constructor<?> constructor, List<BeanDefinition> parameters) {
        this.clazz = clazz;
        this.constructor = constructor;
        this.parameters = parameters;
    }

    public boolean hasParameter() {
        return parameters.size() > 0;
    }

    public Object makeInstance(Object... parameters) {
        logger.debug("BeanDefinition {} 에서 생성한다. parameters : {}",clazz, parameters);
        return ReflectionUtils.newInstance(constructor, parameters);
    }

    public List<BeanDefinition> getParameters() {
        return parameters;
    }

    @Override
    public Class<?> getBeanType() {
        return clazz;
    }
}
