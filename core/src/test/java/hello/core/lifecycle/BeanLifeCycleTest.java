package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
//        System.out.println(System.identityHashCode(client));
        ac.close();
    }

    @Configuration
    @ComponentScan
    static class LifeCycleConfig{
//        @Bean(initMethod = "init", destroyMethod = "close") //== @Bean(initMethod = "init") destroyMethod 추론
        @Bean
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-kyw");
//            System.out.println(System.identityHashCode(networkClient));
            return networkClient;
        }
    }
}
