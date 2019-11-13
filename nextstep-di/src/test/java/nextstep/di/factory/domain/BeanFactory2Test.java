package nextstep.di.factory.domain;

import nextstep.di.factory.example.JdbcUserRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BeanFactory2Test {

    @Test
    public void test1() {
        BeanFactory2 beanFactory2 = new BeanFactory2();
        beanFactory2.initialize();

        assertThat(beanFactory2.getBean(JdbcUserRepository.class))
                .isInstanceOf(JdbcUserRepository.class);
    }
}