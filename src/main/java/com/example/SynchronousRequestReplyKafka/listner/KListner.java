package com.example.SynchronousRequestReplyKafka.listner;

import com.example.SynchronousRequestReplyKafka.model.Model;
import com.example.SynchronousRequestReplyKafka.model.Reply;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class KListner {
    @KafkaListener(topics = "${kafka.topic.request-topic}")
    @SendTo
    public Reply listen(Model x) throws InterruptedException {
        return new Reply("Hello "+x.getName());
    }
}
