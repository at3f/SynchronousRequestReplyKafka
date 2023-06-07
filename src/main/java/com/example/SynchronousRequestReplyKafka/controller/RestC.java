package com.example.SynchronousRequestReplyKafka.controller;

import com.example.SynchronousRequestReplyKafka.model.Model;
import com.example.SynchronousRequestReplyKafka.model.Reply;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class RestC {
    @Autowired
    ReplyingKafkaTemplate<String, Model, Reply> kafkaTemplate;

    @Value("${kafka.topic.request-topic}")
    String requestTopic;

    @Value("${kafka.topic.requestreply-topic}")
    String requestReplyTopic;

    @ResponseBody
    @PostMapping(value="/",produces= MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
    public Reply sum(@RequestBody Model request) throws InterruptedException, ExecutionException {
        // create producer record
        ProducerRecord<String, Model> record = new ProducerRecord<String, Model>(requestTopic, request);
        // set reply topic in header
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
        // post in kafka topic
        RequestReplyFuture<String, Model, Reply> sendAndReceive = kafkaTemplate.sendAndReceive(record);
        // get consumer record
        ConsumerRecord<String, Reply> consumerRecord = sendAndReceive.get();
        return consumerRecord.value();
    }
}
