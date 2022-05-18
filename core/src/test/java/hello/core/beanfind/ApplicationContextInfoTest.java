package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 bean 출력")
    void findAllBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for(String s : beanDefinitionNames){
            Object bean = ac.getBean(s);
            System.out.println("name = " + s + " Object = " + bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 bean 출력")
    void findAppApplicationBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for(String s : beanDefinitionNames){
            BeanDefinition beanDefinition = ac.getBeanDefinition(s);

            //BeanDefinition.ROLE_APPLICATION : 직접 등록한 bean
            //BeanDefinition.ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 bean
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){

                Object bean = ac.getBean(s);
                System.out.println("name = " + s + " Object = " + bean);
            }

        }
    }
}
