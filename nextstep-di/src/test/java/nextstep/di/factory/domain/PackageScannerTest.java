package nextstep.di.factory.domain;

import nextstep.di.factory.example.MyQnaService;
import nextstep.di.factory.example.QnaController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PackageScannerTest {

    @Test
    void scanBeanFactory1() {
        PackageScanner packageScanner = new PackageScanner("nextstep.di.factory.example");
        BeanFactory2 beanFactory2 = new BeanFactory2();
        packageScanner.scan(beanFactory2);
        beanFactory2.initialize();

        QnaController qnaController = beanFactory2.getBean(QnaController.class);

        assertNotNull(qnaController);
        assertNotNull(qnaController.getQnaService());

        MyQnaService qnaService = qnaController.getQnaService();
        assertNotNull(qnaService);
        assertNotNull(qnaService.getUserRepository());
        assertNotNull(qnaService.getQuestionRepository());
    }
}