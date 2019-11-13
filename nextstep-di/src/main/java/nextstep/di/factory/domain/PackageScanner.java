package nextstep.di.factory.domain;

import com.google.common.collect.Sets;
import nextstep.di.factory.util.ReflectionUtils;
import nextstep.stereotype.Controller;
import nextstep.stereotype.Repository;
import nextstep.stereotype.Service;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class PackageScanner {
    private static final Logger logger = LoggerFactory.getLogger(PackageScanner.class);
    private static final Collection<Class<? extends Annotation>> annotations =
            Arrays.asList(Controller.class, Service.class, Repository.class);

    private Reflections reflections;
    private Set<Class<?>> preInstantiateBeans;

    public PackageScanner(Object... basePackage) {
        this.reflections = new Reflections(basePackage);
        preInstantiateBeans = initiatePreInstantiateBeans();
    }

    private Set<Class<?>> initiatePreInstantiateBeans() {
        Set<Class<?>> beans = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            beans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        logger.debug("Scan Beans Type : {}", beans);
        return beans;
    }

    public void scan(BeanFactory2 beanFactory) {
        for (Class<?> clazz : preInstantiateBeans) {
            beanFactory.addBeanDefinition(createBeanDefinition(clazz));
        }
    }

    private BeanDefinition createBeanDefinition(Class<?> clazz) {
        return null;
    }
}
