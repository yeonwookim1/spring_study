package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Provider;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    private final Provider<MyLogger> myLogger;

    public void logic(String id){
        MyLogger logger = this.myLogger.get();
        logger.log("service id = " + id);
    }
}
