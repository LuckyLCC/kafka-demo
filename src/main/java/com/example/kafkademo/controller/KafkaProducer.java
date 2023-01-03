package com.example.kafkademo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    /**
     * 发送消息
     */
    public void sendMessage() {
        try{
            System.out.println("kaishi");
            String msg="1111111111";
            ListenableFuture future = kafkaTemplate.send("hello", msg);
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onFailure(Throwable throwable) {
//                    log.error("数据发送失败，失败数据为{}", msg);
                    System.out.println("失败");
                }

                @Override
                public void onSuccess(SendResult<String, String> stringStringSendResult) {
//                    log.info("数据发送成功,成功数据为{}",msg);
                    System.out.println("成功");
                }
            });
        }catch (Exception e){
            System.out.println("sendMessage exception");
        }

    }
}
