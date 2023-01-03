package com.example.kafkademo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: TODO
 * @Author: liuchang
 * @CreateTime: 2022-10-17  14:44
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private KafkaProducer kafkaProducer;
    @RequestMapping("/hello")
    public String hello(){
        System.out.println("------->测试生产者发送消息");
        kafkaProducer.sendMessage();
        return "kafka消息已发送.";
    }


    @GetMapping("/kafka/test")
    public void sendMessage(@RequestBody String msg) {
        System.out.println("kaishi");
        ListenableFuture future = (ListenableFuture) kafkaTemplate.send("orders-flume-new", msg);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("数据发送失败，失败数据为{}", msg);
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                log.info("数据发送成功,成功数据为{}",msg);
            }
        });
//        kafkaTemplate.send("callmeappgps", msg);
    }

}
