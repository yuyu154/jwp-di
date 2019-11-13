package nextstep.di.factory.domain;

import nextstep.di.factory.example.JdbcUserRepository;
import nextstep.di.factory.example.MyQnaService;
import nextstep.di.factory.util.BeanFactoryUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;

public class BeanDefinitionTest {

    @Test
    public void test1() {
        Class<?> clazz = MyQnaService.class;
        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        Parameter[] parameters = constructor.getParameters();
        BeanDefinition beanDefinition = new BeanDefinition(MyQnaService.class, constructor, parameters);

        assertThat(beanDefinition.getBeanType())
                .isEqualTo(MyQnaService.class);
        assertThat(beanDefinition.getConstructor())
                .isEqualTo(constructor);
        assertThat(beanDefinition.getParameters())
                .isEqualTo(parameters);
    }
}