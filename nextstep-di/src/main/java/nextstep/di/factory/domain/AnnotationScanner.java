package nextstep.di.factory.domain;

import com.google.common.collect.Sets;
import nextstep.di.factory.util.BeanFactoryUtils;
import nextstep.di.factory.util.ReflectionUtils;
import nextstep.stereotype.Controller;
import nextstep.stereotype.Repository;
import nextstep.stereotype.Service;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnotationScanner {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationScanner.class);
    private static final Collection<Class<? extends Annotation>> ANNOTATIONS =
            Arrays.asList(Controller.class, Service.class, Repository.class);

    private Reflections reflections;
    private Set<Class<?>> preInstantiateBeans;
    private BeanFactory beanFactory;

    public AnnotationScanner(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void scan(Object... basePackage) {
        reflections = new Reflections(basePackage);
        preInstantiateBeans = scanAnnotations();

        Set<Class<?>> instantiateBeans = preInstantiateBeans.stream()
                .filter(clazz -> checkAnnotation(clazz))
                .collect(Collectors.toSet());

        for (Class<?> clazz : instantiateBeans) {
            BeanDefinition beanDefinition = makeBean(clazz);
            beanFactory.addBeanDefinition(clazz, beanDefinition);
        }
    }

    private boolean checkAnnotation(Class<?> clazz) {
        return ANNOTATIONS.stream()
                .filter(annotation -> clazz.isAnnotationPresent(annotation))
                .findAny()
                .isPresent();
    }

    private ConstructorBean makeBean(Class<?> clazz) {
        try {
            clazz = BeanFactoryUtils.findConcreteClass(clazz, preInstantiateBeans);
        } catch (IllegalStateException e) {
            return null;
        }
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);

        // inject가 없고 다른 생성자가 없을 때
        if (injectedConstructor == null) {
            return getConstructor(clazz);
        }

        Class<?>[] parameterTypes = injectedConstructor.getParameterTypes();
        if (parameterTypes.length == 0) {
            return new ConstructorBean(clazz, injectedConstructor, Collections.emptyList());
        }

        List<BeanDefinition> parameters = makeParameterBeanDefinition(parameterTypes);

        return new ConstructorBean(clazz, injectedConstructor, parameters);
    }

    private List<BeanDefinition> makeParameterBeanDefinition(Class<?>[] parameterTypes) {
        return Stream.of(parameterTypes)
                .map(classType -> makeBean(classType))
                .filter(beanDefinition -> beanDefinition != null)
                .collect(Collectors.toList());
    }


    private ConstructorBean getConstructor(Class<?> clazz) {
        Constructor<?> constructor = ReflectionUtils.getDefaultConstructor(clazz);
        if (constructor.getParameterTypes().length > 0) {
            return new ConstructorBean(clazz, ReflectionUtils.getDefaultConstructor(clazz), makeParameterBeanDefinition(constructor.getParameterTypes()));
        }
        return new ConstructorBean(clazz, ReflectionUtils.getDefaultConstructor(clazz), Collections.emptyList());
    }

    private Set<Class<?>> scanAnnotations() {
        Set<Class<?>> beans = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : ANNOTATIONS) {
            beans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        logger.debug("Scan Beans Type : {}", beans);
        return beans;
    }
}
