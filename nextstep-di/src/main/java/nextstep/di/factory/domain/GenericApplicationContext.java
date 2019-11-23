package nextstep.di.factory.domain;

import nextstep.annotation.ComponentScan;
import nextstep.annotation.Configuration;
import nextstep.di.factory.domain.scanner.ClassPathScanner;
import nextstep.di.factory.domain.scanner.ConfigurationScanner;

import java.lang.annotation.Annotation;
import java.util.Set;

public class GenericApplicationContext implements ApplicationContext {
    private Class<?> configuration;
    private BeanFactory beanFactory;

    public GenericApplicationContext(Class<?> configuration) {
        this.configuration = configuration;
        this.beanFactory = new GenericBeanFactory();
    }

    public GenericApplicationContext(Class<?> configuration, BeanFactory beanFactory) {
        this.configuration = configuration;
        this.beanFactory = beanFactory;
    }

    @Override
    public void initialize() {
        ClassPathScanner classPathScanner = new ClassPathScanner(beanFactory);
        ConfigurationScanner configurationScanner = new ConfigurationScanner(beanFactory);

        if (configuration.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScan = configuration.getAnnotation(ComponentScan.class);
            classPathScanner.scan(componentScan.value());
        }

        if (configuration.isAnnotationPresent(Configuration.class)) {
            configurationScanner.register(configuration);
        }
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return beanFactory.getBean(requiredType);
    }

    @Override
    public Set<Class<?>> getSupportedClassByAnnotation(Class<? extends Annotation> annotation) {
        return beanFactory.getSupportedClassByAnnotation(annotation);
    }
}
