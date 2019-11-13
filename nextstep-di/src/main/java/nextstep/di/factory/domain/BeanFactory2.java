package nextstep.di.factory.domain;

import nextstep.di.factory.support.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BeanFactory2 {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Map<Class<?>, BeanDefinition> beanDefinitions = new HashMap<>();
    private Beans beans;

    public BeanFactory2() {
        this.beans = new Beans();
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        for (Class<?> clazz : beanDefinitions.keySet()) {

        }
    }

    public void addBeanDefinition(BeanDefinition beanDefinition) {
        beanDefinitions.put(beanDefinition.getBeanType(), beanDefinition);
    }
}
