package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient  { //implements InitializingBean, DisposableBean

    private String url;

    public NetworkClient() {
        System.out.println("url = " + url);
//        connect();
//        call("초기화 연결 메세지");
    }

    public void setUrl(String url){
        this.url = url;
    }

    //start service
    public void connect(){
        System.out.println("connect : " + url);
    }

    public void call(String msg){
        System.out.println("call : " + url + "msg = " + msg);
    }

    //end service
    public void disconnect(){
        System.out.println("close : " + url);
    }

    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }

    //빈 설정정보에 등록
//    public void init() {
//        System.out.println("NetworkClient.init");
//        connect();
//        call("초기화 연결 메세지");
//    }
//
//    public void close() {
//        System.out.println("NetworkClient.close");
//        disconnect();
//    }
    
    
    
    //인터페이스 방식 등록
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("NetworkClient.afterPropertiesSet");
//        connect();
//        call("초기화 연결 메세지");
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("NetworkClient.destroy");
//        disconnect();
//    }
}
